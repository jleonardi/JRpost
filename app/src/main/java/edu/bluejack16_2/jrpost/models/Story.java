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
    private ArrayList<String> storyGenre;
    private User currentUser;
    private Date createdAt;

    public Story(String storyId, String storyTitle, String storyContent, ArrayList<String> storyGenre, User currentUser) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = currentUser;
        this.createdAt = new Date();
    }

    public Story(String storyId, String storyTitle, String storyContent, ArrayList<String> storyGenre) {
        this.storyId = storyId;
        this.storyTitle = storyTitle;
        this.storyContent = storyContent;
        this.storyGenre = storyGenre;
        this.currentUser = Session.currentUser;
        this.createdAt = new Date();
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

    public ArrayList<String> getStoryGenre() {
        return storyGenre;
    }

    public void setStoryGenre(ArrayList<String> storyGenre) {
        this.storyGenre = storyGenre;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
