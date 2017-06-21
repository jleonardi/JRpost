package edu.bluejack16_2.jrpost.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 6/20/2017.
 */

public class Story {
    private String storyId;
    private String storyTitle;
    private String storyContent;
    private String storyGenre;
    private String currentUser;
    private Date createdAt;
    private User user;

    public Story(String storyId, String storyTitle, String storyContent, String storyGenre, String currentUser, Date createdAt) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = currentUser;
        this.createdAt = createdAt;
    }

    public Story(String storyId, String storyTitle, String storyContent, String storyGenre, String currentUser) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = currentUser;
        this.createdAt = new Date();
    }

    public Story(String storyId, String storyTitle, String storyContent, String storyGenre) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = Session.currentUser.getUserId();
        this.createdAt = new Date();
    }

    public Story() {

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
