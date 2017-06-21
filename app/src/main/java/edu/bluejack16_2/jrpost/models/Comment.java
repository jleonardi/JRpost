package edu.bluejack16_2.jrpost.models;

/**
 * Created by User on 6/22/2017.
 */

public class Comment {
    private String commentId;
    private String storyId;
    private String userId;
    private String commentContent;
    private User user;

    public Comment() {

    }

    public Comment(String commentId, String storyId, String commentContent) {
        this.commentId = commentId;
        this.storyId = storyId;
        this.commentContent = commentContent;
        this.userId = Session.currentUser.getUserId();
    }

    public Comment(String commentId, String storyId, String userId, String commentContent) {
        this.commentId = commentId;
        this.storyId = storyId;
        this.userId = userId;
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
