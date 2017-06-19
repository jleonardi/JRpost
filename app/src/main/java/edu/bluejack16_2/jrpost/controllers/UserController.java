package edu.bluejack16_2.jrpost.controllers;

import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.bluejack16_2.jrpost.RegisterActivity;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    public void getUser(String username, String password, final RegisterActivity activity) {
        DatabaseReference userRef = mDatabase.child("users").orderByChild("username").equalTo(username).getRef();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    Toast.makeText(activity, ds.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(activity, "Gagal", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
