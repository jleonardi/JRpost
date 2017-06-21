package edu.bluejack16_2.jrpost.controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import edu.bluejack16_2.jrpost.TimelineFragment;
import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
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

    public void addStory(String storyTitle, String storyContent, String storyGenre) {
        String storyId = mDatabase.push().getKey();
        Story newStory = new Story(storyId, storyTitle, storyContent, storyGenre);
        mDatabase.child(storyId).setValue(newStory);

    }

    public void getAllStory(final StoryViewAdapter adapter, final TimelineFragment fragment) {
        Query storyRef = FirebaseDatabase.getInstance().getReference().child("stories");
        storyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String storyTitle = ds.child("storyTitle").getValue().toString();
                    String storyContent = ds.child("storyContent").getValue().toString();
                    String storyGenre = ds.child("storyGenre").getValue().toString();
                    String currentUser = ds.child("currentUser").getValue().toString();
                    //Date createdAt = (Date) ds.child("createdAt").getValue();
                    String storyId = ds.child("storyId").getValue().toString();
                    Story story = new Story(storyId, storyTitle, storyContent, storyGenre, currentUser);
                    adapter.addStory(story);
                }
                adapter.notifyDataSetChanged();
                //loading selesai
                fragment.progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
