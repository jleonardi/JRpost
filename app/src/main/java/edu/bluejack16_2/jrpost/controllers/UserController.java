package edu.bluejack16_2.jrpost.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private UserController() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public static UserController getInstance() {
        return instance;
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
                    String password = ds.child("password").getValue().toString();
                    String userId = ds.child("userId").getValue().toString();

                    if(!password.equals(login_password))
                        break;

                    Session.name=name;
                    Session.username=username;
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", username);
                    editor.putString("name", name);
                    editor.putString("userId", userId);
                    editor.putString("password", userId);
                    editor.commit();
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
