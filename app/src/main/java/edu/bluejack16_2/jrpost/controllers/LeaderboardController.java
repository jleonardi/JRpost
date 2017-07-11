package edu.bluejack16_2.jrpost.controllers;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import edu.bluejack16_2.jrpost.LeaderboardFragment;
import edu.bluejack16_2.jrpost.LoginActivity;
import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;

/**
 * Created by RE on 7/8/2017.
 */

public class LeaderboardController {
    private static LeaderboardController instance = new LeaderboardController();
    DatabaseReference mDatabase;

    private LeaderboardController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("stories");
    }

    public static LeaderboardController getInstance() {
        return instance;
    }

    public void getByGenre(final String genre, final StoryViewAdapter adapter)
    {
        Query storyRef = null;
        if(genre.equals("All Genre")){
            storyRef = mDatabase;
        } else {
            storyRef = mDatabase.orderByChild("storyGenre").equalTo(genre);
        }
        adapter.clearStory();
        adapter.notifyDataSetChanged();
        Log.d("Leaderboard",genre);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final Story story = ds.getValue(Story.class);
                        Log.d("Leaderboard", story.getStoryTitle());
                        Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(story.getCurrentUser());
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                    User usr = ds.getValue(User.class);
                                    story.setUser(usr);
                                    adapter.addStory(story);
                                    adapter.sortByLike();
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    //adapter.sortByLike();
                    //adapter.notifyDataSetChanged();

                } catch(Exception e) {
                    Log.d("Leaderboard Error", e.getMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
