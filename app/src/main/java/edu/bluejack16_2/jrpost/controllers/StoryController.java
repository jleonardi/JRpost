package edu.bluejack16_2.jrpost.controllers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.Console;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

import edu.bluejack16_2.jrpost.CommentDetailStoryFragment;
import edu.bluejack16_2.jrpost.DetailStoryActivity;
import edu.bluejack16_2.jrpost.DetailStoryFragment;
import edu.bluejack16_2.jrpost.NewStoryFragment;
import edu.bluejack16_2.jrpost.SearchStoryFragment;
import edu.bluejack16_2.jrpost.TimelineFragment;
import edu.bluejack16_2.jrpost.UpdateStoryActivity;
import edu.bluejack16_2.jrpost.adapters.CurrentProfileAdapter;
import edu.bluejack16_2.jrpost.adapters.SearchResultAdapter;
import edu.bluejack16_2.jrpost.adapters.StoryViewAdapter;
import edu.bluejack16_2.jrpost.adapters.UserStoryListAdapter;
import edu.bluejack16_2.jrpost.models.Comment;
import edu.bluejack16_2.jrpost.models.Follow;
import edu.bluejack16_2.jrpost.models.Notification;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;

/**
 * Created by User on 6/20/2017.
 */

public class StoryController {
    private static StoryController instance = new StoryController();
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private StoryController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("stories");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images").child("stories");
    }

    public void uploadImage(Uri filePath) {
        UploadTask uploadTask = storageRef.putFile(filePath);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.v("Tag", "Imba");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("Tag", e.getMessage());
            }
        });
    }

    public static StoryController getInstance() {
        return instance;
    }

    public void addStory(String storyTitle, String storyContent, String storyGenre, Uri image, final NewStoryFragment fragment) {
        final String storyId = mDatabase.push().getKey();
        final Story newStory = new Story(storyId, storyTitle, storyContent, storyGenre);


        if(image != null) {
            storageRef.child(storyId).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    newStory.setImage(true);
                    mDatabase.child(storyId).setValue(newStory);
                    fragment.progressDialog.dismiss();
                }
            });
        } else {
            mDatabase.child(storyId).setValue(newStory);
            fragment.progressDialog.dismiss();
        }

    }

    public void getStoryOnUserId(String userId, final CurrentProfileAdapter adapter) {
        Query storyRef = mDatabase.orderByChild("currentUser").equalTo(userId);
        Log.d("11 anjay",userId);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Story story = ds.getValue(Story.class);
                    if(story.getImage())
                    {
                        story.setImageRef(storageRef.child(story.getStoryId()));
                    }
                    else
                    {
                        story.setImageRef(storageRef.child("noimage.png"));
                    }
                    adapter.add(story);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getStoryOnUserId(String userId, final UserStoryListAdapter adapter) {
        Query storyRef = mDatabase.orderByChild("currentUser").equalTo(userId);
        Log.d("anjay", "getStoryOnUserId: " + userId);

        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("anjay", "getStoryOnUserId: ");
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Story story = ds.getValue(Story.class);
                    if(story.getImage())
                    {
                        story.setImageRef(storageRef.child(story.getStoryId()));
                    }
                    else
                    {
                        story.setImageRef(storageRef.child("noimage.png"));
                    }
                    adapter.addStory(story);
                    adapter.notifyDataSetChanged();
                    Log.d("anjay", "getStoryOnUserId: " + story.getStoryTitle());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getStoryOnSearchTitle(final SearchResultAdapter adapter, final String searchPattern, final String genre, final SearchStoryFragment fragment){
        Query storyRef = FirebaseDatabase.getInstance().getReference().child("stories");
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    final Story story = ds.getValue(Story.class);
                    if(
                            (story.getStoryTitle().contains(searchPattern) && story.getStoryGenre().equals(genre))
                          ||
                            (story.getStoryTitle().contains(searchPattern) && genre.equals("All Genre"))
                            )
                    {
                        if(story.getImage())
                        {
                            story.setImageRef(storageRef.child(story.getStoryId()));
                        }
                        else
                        {
                            story.setImageRef(storageRef.child("noimage.png"));
                        }
                        Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(story.getCurrentUser());
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                    User usr = ds.getValue(User.class);
                                    story.setUser(usr);
                                    adapter.addStory(story);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
                fragment.progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void likeStory(final String userId, final String storyId, final CommentDetailStoryFragment fragment) {
        Query storyRef = mDatabase.orderByChild("storyId").equalTo(storyId);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Story story = ds.getValue(Story.class);
                    if(!story.isLike(userId)){
                        story.like(userId);
                        mDatabase.child(storyId).setValue(story);
                        fragment.setNewLikeCount(story.getLikersCount());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void dislikeStory(final String userId, final String storyId, final CommentDetailStoryFragment fragment) {
        Query storyRef = mDatabase.orderByChild("storyId").equalTo(storyId);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    try {
                        Story story = ds.getValue(Story.class);
                        story.dislike(userId);
                        mDatabase.child(storyId).setValue(story);
                        fragment.setNewLikeCount(story.getLikersCount());
                    } catch(Exception e) {
                        Log.d("Error Komen", e.getMessage());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getStoryOnUsername(final SearchResultAdapter adapter, final String searchPattern, final String genre){
        Query storyRef = FirebaseDatabase.getInstance().getReference().child("stories");
        storyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    final Story story = ds.getValue(Story.class);
                    if(story.getImage())
                    {
                        story.setImageRef(storageRef.child(story.getStoryId()));
                    }
                    else
                    {
                        story.setImageRef(storageRef.child("noimage.png"));
                    }
                    Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(story.getCurrentUser());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                User usr = ds.getValue(User.class);
                                if(usr.getUsername().contains(searchPattern)) {
                                    story.setUser(usr);
                                    adapter.addStory(story);
                                    adapter.notifyDataSetChanged();
                                }
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

    public void getStoryOnFollowedUser(final StoryViewAdapter adapter, final TimelineFragment fragment) {
        Query followedUserRef = FirebaseDatabase.getInstance().getReference().child("followUsers").orderByChild("userId").equalTo(Session.currentUser.getUserId());
        followedUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Follow currentFollowStatus = ds.getValue(Follow.class);
                    Query storyRef = FirebaseDatabase.getInstance().getReference().child("stories").orderByChild("currentUser").equalTo(currentFollowStatus.getFollowedUserId());
                    storyRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            adapter.clearStory();
                            adapter.notifyDataSetChanged();
                            Log.d("Story Updated", "Waw");
                            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                                final Story story = ds.getValue(Story.class);
                                if(story.getImage())
                                {
                                    story.setImageRef(storageRef.child(story.getStoryId()));
                                }
                                else
                                {
                                    story.setImageRef(storageRef.child("noimage.png"));
                                }
                                Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(story.getCurrentUser());
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                            User usr = ds.getValue(User.class);
                                            story.setUser(usr);
                                            adapter.addStory(story);
                                            adapter.sortByDate();
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
                fragment.progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllStory(final StoryViewAdapter adapter, final TimelineFragment fragment) {

        Query storyRef = FirebaseDatabase.getInstance().getReference().child("stories");
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    final Story story = ds.getValue(Story.class);
                    Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(story.getCurrentUser());
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                User usr = ds.getValue(User.class);
                                story.setUser(usr);
                                adapter.addStory(story);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

                //loading selesai
                fragment.progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getStoryOnId(final String storyId, final DetailStoryActivity activity) {
        Query storyRef = mDatabase.orderByChild("storyId").equalTo(storyId);
        storyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    final Story story = ds.getValue(Story.class);
                    if(story.getImage())
                    {
                        story.setImageRef(storageRef.child(story.getStoryId()));
                    }
                    else
                    {
                        story.setImageRef(storageRef.child("noimage.png"));
                    }
                    Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("userId").equalTo(story.getCurrentUser());
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                User usr = ds.getValue(User.class);
                                story.setUser(usr);
                                activity.addStory(story);
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

    public void getStoryToUpdate(String storyId, final UpdateStoryActivity activity) {
        Query userRef = mDatabase.orderByChild("storyId").equalTo(storyId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Story story = ds.getValue(Story.class);
                    if(story.getImage())
                    {
                        story.setImageRef(storageRef.child(story.getStoryId()));
                    }
                    else
                    {
                        story.setImageRef(storageRef.child("noimage.png"));
                    }
                    activity.fillStory(story);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updateStory(final String storyId, final String storyTitle, final String storyContent, final String storyGenre, final Uri image, final UpdateStoryActivity activity) {
        try {
            Query userRef = mDatabase.orderByChild("storyId").equalTo(storyId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final Story story = ds.getValue(Story.class);
                        story.setStoryTitle(storyTitle);
                        story.setStoryContent(storyContent);
                        story.setStoryGenre(storyGenre);
                        if (image != null) {
                            storageRef.child(storyId).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    story.setImage(true);
                                    mDatabase.child(storyId).setValue(story);
                                    Log.d("UpdateGambar", "Sukses");
                                    activity.progressDialog.dismiss();
                                    activity.finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("UpdateGambar", "gagal");
                                }
                            });
                        } else {
                            mDatabase.child(storyId).setValue(story);
                            activity.progressDialog.dismiss();
                            activity.finish();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            Log.d("UpdateTag", e.getMessage());
        }
    }

    public void deleteStory(String storyId, DetailStoryActivity activity) {
        mDatabase.child(storyId).setValue(null);
        Query commentQuery = FirebaseDatabase.getInstance().getReference("comments").orderByChild("storyId").equalTo(storyId);
        commentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Comment comment = ds.getValue(Comment.class);
                    FirebaseDatabase.getInstance().getReference("comments").child(comment.getCommentId()).setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Query notifQuery = FirebaseDatabase.getInstance().getReference("notification").orderByChild("storyId").equalTo(storyId);
        notifQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    Notification notif= ds.getValue(Notification.class);
                    FirebaseDatabase.getInstance().getReference("notification").child(notif.getNotifId()).setValue(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
