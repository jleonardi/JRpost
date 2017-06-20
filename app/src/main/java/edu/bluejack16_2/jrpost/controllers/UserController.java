package edu.bluejack16_2.jrpost.controllers;

import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.jrpost.LoginActivity;
import edu.bluejack16_2.jrpost.MainActivity;
import edu.bluejack16_2.jrpost.RegisterActivity;
import edu.bluejack16_2.jrpost.models.Session;
import edu.bluejack16_2.jrpost.models.User;

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

    private UserController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public static UserController getInstance() {
        return instance;
    }

    public Boolean addNewUser(String username, String name, String password) {
        User newUser = new User(username, name, password);
        /*
        * .child itu kayak add folder di databasenya
        * push itu untuk kasih unique id
        * kalau .child("users").push.setValue
        * nanti ngasilin dari root -> users -> unique id -> data data usernya
        * */
        mDatabase.child("users").push().setValue(newUser);
        return true;
    }

    public void getUser(String username, String password, final LoginActivity activity) {
        Query userRef = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("username").equalTo(username);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue().toString();
                    Session.name=name;
                    Intent intent = new Intent(activity.getApplicationContext(),MainActivity.class);
                    activity.startActivity(intent);
                    Toast.makeText(activity, "Welcome, "+name, Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(activity, "You are not registered as member, Please Register!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
