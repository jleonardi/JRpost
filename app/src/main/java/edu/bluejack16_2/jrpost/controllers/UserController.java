package edu.bluejack16_2.jrpost.controllers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.bluejack16_2.jrpost.models.User;

/**
 * Created by User on 6/20/2017.
 */

public class UserController {
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

        mDatabase.child("users").push().setValue(newUser);

        return true;
    }

}
