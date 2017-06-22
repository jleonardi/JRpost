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
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.bluejack16_2.jrpost.controllers.StoryController;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewStoryFragment extends Fragment {


    EditText txtStory;
    EditText txtTitle;
    ArrayAdapter adapter;
    Spinner spinGenre;
    private static final int SELECTED_PICTURE = 1;
    ImageView imgView;
    Button btnChoose;
    String filePath;
    public ProgressDialog progressDialog;
    public NewStoryFragment() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SELECTED_PICTURE:
                if(resultCode==Activity.RESULT_OK)
                {
                    Uri uri = data.getData();
                    String[]projection={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    filePath=cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Drawable d = new BitmapDrawable(yourSelectedImage);
                    imgView.setBackground(d);
                }
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_story, container, false);

        try {
            txtStory = (EditText) view.findViewById(R.id.txtStory);
            txtTitle = (EditText) view.findViewById(R.id.txtTitle);
            spinGenre = (Spinner) view.findViewById(R.id.spinGenre);
            imgView = (ImageView) view.findViewById(R.id.imgView);
            btnChoose = (Button) view.findViewById(R.id.btnChoose);

            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,SELECTED_PICTURE);
                }
            });

            ArrayList<String> genres = new ArrayList<>();
            genres.add("Drama");
            genres.add("Comedy");
            genres.add("Fantasy");
            genres.add("Mystery");
            genres.add("Horror");
            genres.add("Thriller");
            adapter=new ArrayAdapter(view.getContext(),android.R.layout.simple_list_item_1,genres);
            spinGenre.setAdapter(adapter);
            final NewStoryFragment fragment = this;

            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.setImageResource(R.drawable.ic_menu_share);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String story = txtStory.getText().toString();
                    String title = txtTitle.getText().toString();
                    String genre = spinGenre.getSelectedItem().toString();
                    if (story.length() == 0) {
                        Toast.makeText(view.getContext(), "Story must be filled", Toast.LENGTH_SHORT).show();
                    } else if (title.length() == 0) {
                        Toast.makeText(view.getContext(), "Title of the Story must be filled", Toast.LENGTH_SHORT).show();
                    }
                    else if(filePath.length()==0)
                    {
                        Toast.makeText(view.getContext(), "Please Choose Image for the Cover", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog = new ProgressDialog(view.getContext());
                        progressDialog.setMessage("Please wait");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        StoryController.getInstance().addStory(title,story,genre,fragment);


                        Toast.makeText(view.getContext(), "Your Story has been published", Toast.LENGTH_SHORT).show();

                        //selesai add story balik ke timeline
                        getActivity().setTitle("Timeline");
                        TimelineFragment timelineFragment= new TimelineFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout,timelineFragment);
                        fragmentTransaction.commit();
                    }
                }
            });
        }catch(Exception e)
        {
            Toast.makeText(view.getContext(), e+"", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
