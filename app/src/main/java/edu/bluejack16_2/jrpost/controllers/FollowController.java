package edu.bluejack16_2.jrpost.controllers;

import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.jrpost.DetailStoryFragment;
import edu.bluejack16_2.jrpost.ProfileActivity;
import edu.bluejack16_2.jrpost.models.Follow;
import edu.bluejack16_2.jrpost.models.Session;

/**
 * Created by User on 6/21/2017.
 */

public class FollowController {
    private static FollowController instance = new FollowController();
    DatabaseReference mDatabase;

    private FollowController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("followUsers");
    }

    public static FollowController getInstance() {
        return instance;
    }

    public void followUser(String followedUserId) {
        String followId = mDatabase.push().getKey();
        final Follow newFollow = new Follow(followId, followedUserId);
        Query followRef = FirebaseDatabase.getInstance().getReference().child("followUsers").orderByChild("currentAndFollowed").equalTo(newFollow.getCurrentAndFollowed());
        followRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() <= 0) {
                    mDatabase.child(newFollow.getFollowId()).setValue(newFollow);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void unFollowUser(String followedUserId) {
        Follow newFollow = new Follow(followedUserId);
        Query followRef = FirebaseDatabase.getInstance().getReference().child("followUsers").orderByChild("currentAndFollowed").equalTo(newFollow.getCurrentAndFollowed());
        followRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Follow oldFollow = ds.getValue(Follow.class);
                    //mDatabase.child("followUsers").child(oldFollow.getFollowId()).removeValue();
                    ds.getRef().setValue(null);
                    Log.d("UnfollowTest", "Gils");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkFollowUser(final Button btn, String followedUserId){
        Follow newFollow = new Follow(followedUserId);
        Query followRef = FirebaseDatabase.getInstance().getReference().child("followUsers").orderByChild("currentAndFollowed").equalTo(newFollow.getCurrentAndFollowed());
        followRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0) {
                    btn.setText("Unfollow");
                } else {
                    btn.setText("Follow");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkFollowUser(final DetailStoryFragment fragment, String followedUserId){
        Follow newFollow = new Follow(followedUserId);
        Query followRef = FirebaseDatabase.getInstance().getReference().child("followUsers").orderByChild("currentAndFollowed").equalTo(newFollow.getCurrentAndFollowed());
        followRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0) {
                    fragment.isFollowing(true);
                } else {
                    fragment.isFollowing(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
