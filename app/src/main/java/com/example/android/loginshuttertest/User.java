package com.example.android.loginshuttertest;

/**
 * Created by bocist-8 on 07/10/15.
 */
public class User {
    private String id;
    private String username;
    private String picture_thumb_url;
    private boolean verified;
    private boolean public_shuttersong;
    private int following_status;

    public User(String id, String username, String picture_thumb_url, boolean verified, boolean public_shuttersong, int following_status) {
        this.id = id;
        this.username = username;
        this.picture_thumb_url = picture_thumb_url;
        this.verified = verified;
        this.public_shuttersong = public_shuttersong;
        this.following_status = following_status;
    }

    public int getFollowing_status() {
        return following_status;
    }

    public void setFollowing_status(int following_status) {
        this.following_status = following_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPicture_thumb_url() {
        return picture_thumb_url;
    }

    public void setPicture_thumb_url(String picture_thumb_url) {
        this.picture_thumb_url = picture_thumb_url;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isPublic_shuttersong() {
        return public_shuttersong;
    }

    public void setPublic_shuttersong(boolean public_shuttersong) {
        this.public_shuttersong = public_shuttersong;
    }
}

//[{"id":"e2641286-a290-44b2-89c5-9eb38c2c1026",
//        "username":"sur.sur","picture_thumb_url":"default-mermaid.png",
//        "verified":false,
//        "public_shuttersong":false,
//        "following_status":2},
//
//        {"id":"31e09340-0723-4c1b-99b7-9bf3ec967fd9",
//        "username":"sur2",
//        "picture_thumb_url":"default-mermaid.png",
//        "verified":false,"public_shuttersong":true,
//        "following_status":3},
//
//        {"id":"9aca9b71-4f76-4b9a-998e-6f323f3b8332",
//        "username":"surkasur",
//        "picture_thumb_url":"default-mermaid.png",
//        "verified":false,
//        "public_shuttersong":true,
//        "following_status":3},

