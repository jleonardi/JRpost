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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import edu.bluejack16_2.jrpost.controllers.StoryController;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.utilities.FirebaseImageLoader;

public class UpdateStoryActivity extends AppCompatActivity {

    EditText txtStory;
    EditText txtTitle;
    ArrayAdapter adapter;
    Spinner spinGenre;
    private static final int SELECTED_PICTURE = 1;
    ImageView imgView;
    Button btnChoose;
    String filePath = "";
    public ProgressDialog progressDialog;
    Uri fileURI;
    Story currentStory;
    ArrayList<String> genres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_story);
        fileURI = null;
        Intent intent = getIntent();
        String storyId = intent.getStringExtra("storyId");

        try {
            txtStory = (EditText) findViewById(R.id.txtStory);
            txtTitle = (EditText) findViewById(R.id.txtTitle);
            spinGenre = (Spinner) findViewById(R.id.spinGenre);
            imgView = (ImageView) findViewById(R.id.imgView);
            btnChoose = (Button) findViewById(R.id.btnChoose);

            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("UpdateGambar", "tes");
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,SELECTED_PICTURE);
                    Log.d("UpdateGambar", "tes");
                }
            });


            genres.add("Drama");
            genres.add("Comedy");
            genres.add("Fantasy");
            genres.add("Mystery");
            genres.add("Horror");
            genres.add("Thriller");
            adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,genres);
            spinGenre.setAdapter(adapter);

            StoryController.getInstance().getStoryToUpdate(storyId, this);

            //FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            //fab.setImageResource(R.drawable.ic_menu_share);

        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillStory(Story story){
        currentStory = story;
        txtTitle.setText(currentStory.getStoryTitle());
        txtStory.setText(currentStory.getStoryContent());
        spinGenre.setSelection(genres.indexOf(currentStory.getStoryGenre()));
        Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(currentStory.getImageRef()).into(imgView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.update_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.updateMenu) {

//            Intent intent = new Intent(getApplicationContext(), TranslateChoiceActivity.class);
//            intent.putExtra("content", currentStory.getStoryContent());
//            startActivity(intent);
                String story = txtStory.getText().toString();
                String title = txtTitle.getText().toString();
                String genre = spinGenre.getSelectedItem().toString();
                if (story.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Story must be filled", Toast.LENGTH_SHORT).show();
                } else if (title.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Title of the Story must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    //Update Here
                    StoryController.getInstance().updateStory(currentStory.getStoryId(), title, story, genre, fileURI, this);
                }
            }
        } catch(Exception e) {
            Log.d("UpdateTag", e.getMessage());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SELECTED_PICTURE:
                if(resultCode== Activity.RESULT_OK)
                {
                    Log.d("UpdateGambar", "sukses pilih gambar");
                    try {
                        imgView.setImageResource(android.R.color.transparent);
                        Uri uri = data.getData();
                        String[] projection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(projection[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                        Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                        Drawable d = new BitmapDrawable(yourSelectedImage);
                        imgView.setBackground(d);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        //StoryController.getInstance().uploadImage(data.getData());
                        fileURI = data.getData();
                    } catch (Exception e) {
                        Log.d("UpdateGambar", "Error: " + e.getMessage());
                    }

                }
                break;

        }
    }
}
