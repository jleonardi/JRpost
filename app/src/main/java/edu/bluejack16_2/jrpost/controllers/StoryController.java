package edu.bluejack16_2.jrpost.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.bluejack16_2.jrpost.models.Story;

/**
 * Created by User on 6/20/2017.
 */

public class StoryController {
    private static StoryController instance = new StoryController();
    private DatabaseReference mDatabase;

    private StoryController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("stories");
    }

    public static StoryController getInstance() {
        return instance;
    }

    public void addStory(String storyTitle, String storyContent, ArrayList<String> storyGenre) {
        String storyId = mDatabase.push().getKey();
        Story newStory = new Story(storyId, storyTitle, storyContent, storyGenre);
        mDatabase.child(storyId).setValue(newStory);
    }

}
