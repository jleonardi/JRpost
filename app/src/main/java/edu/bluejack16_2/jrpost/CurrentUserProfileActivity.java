package edu.bluejack16_2.jrpost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

import edu.bluejack16_2.jrpost.adapters.CurrentProfileAdapter;
import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.controllers.UserController;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;

public class CurrentUserProfileActivity extends AppCompatActivity {

    TextView lblName;
    TextView lblUsername;
    ImageView imgProfile;
    ListView listViewProf;
    CurrentProfileAdapter adapter;
    Button btnChangeProfile;
    private static final int SELECTED_PICTURE = 1;
    String filePath;
    Uri fileURI;
    User currentUser;
    ProgressDialog progressDialog;

    public void doneReadUser(User user) {
        currentUser = user;
        lblName= (TextView) findViewById(R.id.lblName);
        lblName.setText(currentUser.getName());
        lblUsername= (TextView) findViewById(R.id.lblUsername);
        lblUsername.setText(currentUser.getUsername());
        imgProfile= (ImageView) findViewById(R.id.imgProfile);
        UserController.getInstance().getProfilePicture();
        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(currentUser.getImageRef()).into(imgProfile);

        listViewProf= (ListView) findViewById(R.id.listViewProf);

        btnChangeProfile= (Button) findViewById(R.id.btnChangePhoto);
        btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,SELECTED_PICTURE);
            }
        });


        final CurrentProfileAdapter adapter= new CurrentProfileAdapter(getApplicationContext());
        StoryController.getInstance().getStoryOnUserId(currentUser.getUserId(),adapter);

        listViewProf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), DetailStoryActivity.class);
                Log.d("Anjay 22",((Story)adapter.getItem(i)).getStoryId());
                intent.putExtra("story", ((Story)adapter.getItem(i)).getStoryId());
                startActivity(intent);
                finish();
            }
        });

        listViewProf.setAdapter(adapter);
        progressDialog.dismiss();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SELECTED_PICTURE:
                if(resultCode== Activity.RESULT_OK)
                {
                    imgProfile.setImageResource(android.R.color.transparent);
                    Uri uri = data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};
                    Cursor cursor = getApplicationContext().getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filePath=cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);
                    imgProfile.setBackground(d);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    //StoryController.getInstance().uploadImage(data.getData());
                    fileURI = data.getData();
                    UserController.getInstance().changeProfilePicture(uri);
                }
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_profile);
        progressDialog = new ProgressDialog(CurrentUserProfileActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        UserController.getInstance().getCurrentUser(Session.currentUser.getUserId(), this);
    }
}
