package edu.bluejack16_2.jrpost.models;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

/**
 * Created by User on 6/19/2017.
 */

public class User implements Serializable{
    private String userId;
    private String username;
    private String name;
    private String password;
    private boolean image;
    private StorageReference imageRef;

    public StorageReference getImageRef() {
        return imageRef;
    }

    public void setImageRef(StorageReference imageRef) {
        this.imageRef = imageRef;
    }

    public User() {

    }

    public boolean getImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public User(String userId, String username, String name, String password) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public User(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
