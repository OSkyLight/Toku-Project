package com.example.tokuverseproject.Model;

import com.google.gson.annotations.SerializedName;

public class Like {
    @SerializedName("id")
    String id;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("news_feed_id")
    String news_feeed_is;
    @SerializedName("is_reward")
    String is_reward;
    @SerializedName("is_liked")
    String is_liked;

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

    public String getNews_feeed_is() {
        return news_feeed_is;
    }

    public void setNews_feeed_is(String news_feeed_is) {
        this.news_feeed_is = news_feeed_is;
    }

    public String getIs_reward() {
        return is_reward;
    }

    public void setIs_reward(String is_reward) {
        this.is_reward = is_reward;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }
}
