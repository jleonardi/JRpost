package edu.bluejack16_2.jrpost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
    Button btnSave;
    public NewStoryFragment() {
        //Required empty public constructor
        //ArrayList<String> genres = new ArrayList<>();
        //genres.add("Horror");
        //genres.add("Comedy");
        //StoryController.getInstance().addStory("Story of Anjay", "Once upon a time", genres);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_story, container, false);
        try {
            txtStory = (EditText) view.findViewById(R.id.txtStory);
            txtTitle = (EditText) view.findViewById(R.id.txtTitle);
            btnSave = (Button) view.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String story = txtStory.getText().toString();
                    String title = txtTitle.getText().toString();
                    if (story.length() == 0) {
                        Toast.makeText(view.getContext(), "Story must be filled", Toast.LENGTH_SHORT).show();
                    } else if (title.length() == 0) {
                        Toast.makeText(view.getContext(), "Title of the Story must be filled", Toast.LENGTH_SHORT).show();
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
