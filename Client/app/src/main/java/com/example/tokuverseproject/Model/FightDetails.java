package com.example.tokuverseproject.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FightDetails implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("fight_id")
    String fight_id;
    @SerializedName("turn")
    String turn;
    @SerializedName("damage")
    String damage;
    @SerializedName("user_currentHP")
    String user_currentHP;
    @SerializedName("fight_user_currentHP")
    String fight_user_currentHP;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFight_id() {
        return fight_id;
    }

    public void setFight_id(String fight_id) {
        this.fight_id = fight_id;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
    }

    public String getUser_currentHP() {
        return user_currentHP;
    }

    public void setUser_currentHP(String user_currentHP) {
        this.user_currentHP = user_currentHP;
    }

    public String getFight_user_currentHP() {
        return fight_user_currentHP;
    }

    public void setFight_user_currentHP(String fight_user_currentHP) {
        this.fight_user_currentHP = fight_user_currentHP;
    }
}
