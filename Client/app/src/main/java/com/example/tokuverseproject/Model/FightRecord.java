package com.example.tokuverseproject.Model;

public class FightRecord {

    Integer damage;
    Integer turn;
    Integer user_currentHP;
    Integer clickedUser_currentHP;
    String user_heroPic;
    String clickedUser_heroPIc;
    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getTurn() {
        return turn;
    }

    public void setTurn(Integer turn) {
        this.turn = turn;
    }

    public Integer getUser_currentHP() {
        return user_currentHP;
    }

    public void setUser_currentHP(Integer user_currentHP) {
        this.user_currentHP = user_currentHP;
    }

    public Integer getClickedUser_currentHP() {
        return clickedUser_currentHP;
    }

    public void setClickedUser_currentHP(Integer clickedUser_currentHP) {
        this.clickedUser_currentHP = clickedUser_currentHP;
    }

    public String getUser_heroPic() {
        return user_heroPic;
    }

    public void setUser_heroPic(String user_heroPic) {
        this.user_heroPic = user_heroPic;
    }

    public String getClickedUser_heroPIc() {
        return clickedUser_heroPIc;
    }

    public void setClickedUser_heroPIc(String clickedUser_heroPIc) {
        this.clickedUser_heroPIc = clickedUser_heroPIc;
    }
}
