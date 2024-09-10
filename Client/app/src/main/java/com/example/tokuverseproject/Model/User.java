package com.example.tokuverseproject.Model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

import okhttp3.Response;

public class User implements Serializable{
    @SerializedName("id")
    private  String id;
    @SerializedName("username")
    private  String username;

    @SerializedName("pass")
    private String password;
    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String phone_number;
    @SerializedName("profile_pic")
    private String profile_pic;
    @SerializedName("hero_details_id")
    private String hero_details_id;
    @SerializedName("coins")
    private String coins;
    @SerializedName("avatar")
    private String avatar;

    private HeroDetails class_HeroDetails;


    public HeroDetails getClass_HeroDetails() {
        return class_HeroDetails;
    }

    public void setClass_HeroDetails(HeroDetails class_HeroDetails) {
        this.class_HeroDetails = class_HeroDetails;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getHero_details_id() {
        return hero_details_id;
    }

    public void setHero_details_id(String hero_details_id) {
        this.hero_details_id = hero_details_id;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User(String username, String password, String email, String phone_number) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_number = phone_number;
    }
    public User()
    {

    }

}
