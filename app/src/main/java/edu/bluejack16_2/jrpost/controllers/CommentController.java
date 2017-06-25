package edu.bluejack16_2.jrpost.controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.jrpost.adapters.CommentListAdapter;
import edu.bluejack16_2.jrpost.models.Comment;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.User;

/**
 * Created by User on 6/22/2017.
 */

public class CommentController {
    private static CommentController instance = new CommentController();
    DatabaseReference mDatabase;

    private CommentController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("comments");
    }

    public static CommentController getInstance() {
        return instance;
    }

    public void addComment(String storyId, String commentContent, CommentListAdapter adapter) {
        String commentId = mDatabase.push().getKey();
        Comment newComment = new Comment(commentId, storyId, commentContent);
        mDatabase.child(commentId).setValue(newComment);
        newComment.setUser(Session.currentUser);
        adapter.addComment(newComment);
        adapter.notifyDataSetChanged();
    }

    public void populateComments(final CommentListAdapter adapter, String storyId) {
        Query commentRef = FirebaseDatabase.getInstance().getReference().child("comments").orderByChild("storyId").equalTo(storyId);
        commentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Log.d("Comment Section", "ada Komen");
                    final Comment comment = ds.getValue(Comment.class);
                    Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(comment.getUserId());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                comment.setUser(user);
                                adapter.addComment(comment);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
