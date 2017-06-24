package edu.bluejack16_2.jrpost.models;

/**
 * Created by RE on 6/24/2017.
 */

public class Notification {
    private String content;
    private String from;
    private String userId;
    private String date;
    private String notifId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public Notification() {
    }

    public Notification(String content, String from, String userId, String date) {
        this.content = content;
        this.from = from;
        this.userId = userId;
        this.date = date;
    }

    public Notification(String notifId,String content, String from, String userId, String date) {
        this.content = content;
        this.from = from;
        this.userId = userId;
        this.date = date;
        this.notifId = notifId;
    }
}
