package edu.bluejack16_2.jrpost.models;

/**
 * Created by User on 6/21/2017.
 */

public class Follow {
    private String followId;
    private String userId;
    private String followedUserId;
    private String currentAndFollowed;

    public Follow(String followId, String userId, String followedUserId, String currentAndFollowed) {
        this.followId = followId;
        this.userId = userId;
        this.followedUserId = followedUserId;
        this.currentAndFollowed = currentAndFollowed;
    }

    public Follow(String followId, String followedUserId) {
        this.followId = followId;
        this.userId = Session.currentUser.getUserId();
        this.followedUserId = followedUserId;
        this.currentAndFollowed = this.userId + " " + this.followedUserId;
    }

    public Follow(String followedUserId) {
        this.userId = Session.currentUser.getUserId();
        this.followedUserId = followedUserId;
        this.currentAndFollowed = this.userId + " " + this.followedUserId;
    }

    public Follow() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowedUserId() {
        return followedUserId;
    }

    public void setFollowedUserId(String followedUserId) {
        this.followedUserId = followedUserId;
    }

    public String getCurrentAndFollowed() {
        return currentAndFollowed;
    }

    public void setCurrentAndFollowed(String currentAndFollowed) {
        this.currentAndFollowed = currentAndFollowed;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }
}
