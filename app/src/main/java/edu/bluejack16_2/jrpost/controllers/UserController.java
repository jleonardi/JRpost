package edu.bluejack16_2.jrpost.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import edu.bluejack16_2.jrpost.CurrentUserProfileActivity;
import edu.bluejack16_2.jrpost.LoginActivity;
import edu.bluejack16_2.jrpost.MainActivity;
import edu.bluejack16_2.jrpost.ProfileActivity;
import edu.bluejack16_2.jrpost.RegisterActivity;
import edu.bluejack16_2.jrpost.SearchUserFragment;
import edu.bluejack16_2.jrpost.adapters.UserListAdapter;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.Story;
import edu.bluejack16_2.jrpost.models.User;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by User on 6/20/2017.
 */

public class UserController {
    /*
    * Singleton UserController
    * DatabaseReference untuk akses databasenya
    * */
    private DatabaseReference mDatabase;
    private static UserController instance = new UserController();
    private String login_password;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private UserController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images").child("users");
    }

    public static UserController getInstance() {
        return instance;
    }

    public void doLoginWithFbGmail(final String username,final String name,final String password,final LoginActivity activity)
    {
        Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()==0) //klo usernamenya blom ada
                {
                    addNewUser(username, name, password);
                }
                getUser(username,password,activity);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void changeProfilePicture(final Uri image) {
        final String userId = Session.currentUser.getUserId();
        Query userRef = mDatabase.orderByChild("userId").equalTo(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final User user = ds.getValue(User.class);
                    if (image != null) {
                        storageRef.child(userId).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                user.setImage(true);
                                Session.currentUser.setImage(true);
                                mDatabase.child(userId).setValue(user);
                                Log.d("UpdateGambar", "Sukses   ");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("UpdateGambar", "Gagal");
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getProfilePicture()
    {
        if(Session.currentUser.getImage())
        {
            Session.currentUser.setImageRef(storageRef.child(Session.currentUser.getUserId()));
            Log.d("ProfImg", "Ketemu");
        }
        else
        {
            Session.currentUser.setImageRef(storageRef.child("noimage.png"));
            Log.d("ProfImg", "Tidak Ketemu");
        }
    }


    public void doRegister(final String username,final String name,final String password,final Activity activity)
    {
        Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0) //klo usernamenya udah ada
                {
                    Toast.makeText(activity, "Username is already Exists", Toast.LENGTH_SHORT).show();
                    RegisterActivity.progressDialog.dismiss();
                    return;
                }
                else
                {
                    SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());;
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("name",name);
                    editor.putString("username",username);
                    editor.commit();
                    Session.currentUser=new User(username,name,password);
                    addNewUser(username,name,password);


                    Toast.makeText(activity, "Succesfully Register", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(activity,MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void searchUser(final UserListAdapter adapter, final String searchPattern, final SearchUserFragment fragment) {

        Query userRef = mDatabase;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if(user.getUsername().toLowerCase().contains(searchPattern.toLowerCase())) {
                        adapter.addUser(user);
                        adapter.notifyDataSetChanged();
                    }
                }
                fragment.progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Boolean addNewUser(String username, String name, String password) {

        String newUserId = mDatabase.push().getKey();
        User newUser = new User(newUserId, username, name, password);
        /*
        * .child itu kayak add folder di databasenya
        * push itu untuk kasih unique id
        * kalau .child("users").push.setValue
        * nanti ngasilin dari root -> users -> unique id -> data data usernya
        * */
        //mDatabase.child("users").push().setValue(newUser);
        mDatabase.child(newUserId).setValue(newUser);
        Session.currentUser.setUserId(newUserId);
        return true;
    }

    public void getUser(String username, String password, final LoginActivity activity) {
        Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);
        login_password=password;

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue().toString();
                    String username = ds.child("username").getValue().toString();
                    String password;
                    try
                    {
                        //klo login pake gmail, di firebase a dakde password
                        //jadi ni error harus a
                        password= ds.child("password").getValue().toString();
                    }catch(Exception e)
                    {
                        //berarti login pakai gmail
                        password=null;
                    }
                    String userId = ds.child("userId").getValue().toString();
                    //User getUser = ds.getValue(User.class);
                    Session.currentUser = new User(userId, username, name, password);
                    //Session.currentUser = getUser;
                    getProfilePicture();

                    if(password!=null) { //kalo je password a dak kosong (login biasa)
                        if (!password.equals(login_password)) //kalo password a dak same
                            break;
                    }

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", username);
                    editor.putString("name", name);
                    editor.putString("userId", userId);
                    editor.putString("password", password);
                    editor.commit();
                    Intent intent = new Intent(activity.getApplicationContext(),MainActivity.class);
                    activity.startActivity(intent);
                    Toast.makeText(activity, "Welcome, "+name, Toast.LENGTH_SHORT).show();
                    activity.finish();
                    return;
                }
                Toast.makeText(activity, "You are not registered as member, Please Register!", Toast.LENGTH_SHORT).show();
                LoginActivity.progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getUserProfile(String userId, final ProfileActivity activity){
        Query userQuery = mDatabase.orderByChild("userId").equalTo(userId);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if(user.getImage()){
                        user.setImageRef(storageRef.child(user.getUserId()));
                    } else {
                        user.setImageRef(storageRef.child("noimage.png"));
                    }
                    activity.doneReadUser(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentUser(String userId, final CurrentUserProfileActivity activity) {
        Query userQuery = mDatabase.orderByChild("userId").equalTo(userId);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if(user.getImage()){
                        user.setImageRef(storageRef.child(user.getUserId()));
                    } else {
                        user.setImageRef(storageRef.child("noimage.png"));
                    }
                    activity.doneReadUser(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setActivityPicture(String userId, final MainActivity activity) {
        Query userQuery = mDatabase.orderByChild("userId").equalTo(userId);
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if(user.getImage()){
                        user.setImageRef(storageRef.child(user.getUserId()));
                    } else {
                        user.setImageRef(storageRef.child("noimage.png"));
                    }
                    activity.setPicture(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
