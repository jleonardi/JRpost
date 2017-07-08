package edu.bluejack16_2.jrpost.models;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 6/20/2017.
 */

public class Story implements Serializable {
    private String storyId;
    private String storyTitle;
    private String storyContent;
    private String storyGenre;
    private String currentUser;
    private Long createdAt;
    private User user;
    private ArrayList<String> likers;
    private Integer totalLikes;
    private Boolean image;
    private StorageReference imageRef;

    public Story(String storyId, String storyTitle, String storyContent, String storyGenre, String currentUser, Long createdAt) {
        image = false;
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = currentUser;
        this.createdAt = createdAt;
        likers = new ArrayList<>();
        totalLikes = 0;
    }

    public Story(String storyId, String storyTitle, String storyContent, String storyGenre, String currentUser) {
        image = false;
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = currentUser;
        this.createdAt = System.currentTimeMillis();
        likers = new ArrayList<>();
        totalLikes = 0;
    }

    public Story(String storyId, String storyTitle, String storyContent, String storyGenre) {
        image = false;
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = Session.currentUser.getUserId();
        this.createdAt = System.currentTimeMillis();
        likers = new ArrayList<>();
        totalLikes = 0;
    }

    public Story()   {
        likers = new ArrayList<>();
        image = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    public String getStoryGenre() {
        return storyGenre;
    }

    public void setStoryGenre(String storyGenre) {
        this.storyGenre = storyGenre;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public ArrayList<String> getLikers() {
        return likers;
    }

    public void setLikers(ArrayList<String> likers) {
        this.likers = likers;
    }

    public void like(String userId){
        likers.add(userId);
    }

    public void dislike(String userId){
        likers.remove(userId);
    }

    public boolean isLike(String userId) {
        return likers.contains(userId);
    }

    public Integer getLikersCount() {
        return likers.size();
    }

    public Integer getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

    public StorageReference getImageRef() {
        return imageRef;
    }

    public void setImageRef(StorageReference imageRef) {
        this.imageRef = imageRef;
    }
}
