package com.example.tokuverseproject.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comment {
    @SerializedName("id")
    String id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("news_feed_id")
    String news_feed_id;
    @SerializedName("content")
    String content;
    @SerializedName("user_name")
    String user_name;
    @SerializedName("user_avatar")
    String user_avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNews_feed_id() {
        return news_feed_id;
    }

    public void setNews_feed_id(String news_feed_id) {
        this.news_feed_id = news_feed_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }
}
