package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tokuverseproject.Model.FightDetails;
import com.example.tokuverseproject.Model.FightDetailsCustomBase;
import com.example.tokuverseproject.Model.FightHistory;
import com.example.tokuverseproject.Model.FightRecord;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.LinkedList;
import java.util.List;

public class FightDetailsActivity extends AppCompatActivity {

    ImageView btn_FightDetailsBack;
    TextView lbl_FightDetails_ID;
    ImageView img_FightDetails_UserHero, img_FightDetails_ClickedUserHero;
    TextView lbl_FightDetails_Username, lbl_FightDetails_UserAttack, lbl_FightDetails_UserDefense, lbl_FightDetails_UserHealth, lbl_FightDetails_UserLevel;
    TextView lbl_FightDetails_ClickedUserName, lbl_FightDetails_ClickedUserAttack, lbl_FightDetails_ClickedUserDefense, lbl_FightDetails_ClickedUserHealth, lbl_FightDetails_ClickedUserLevel;
    TextView lbl_FightDetails_Reward;
    ListView listVIew_FightRecord;
    ProgressBar loadingBar_FightDetailsHistory;
    FightHistory fightHistory;
    User user, fight_user;

    ServerHandler serverHandler = new ServerHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_details);
        btn_FightDetailsBack = findViewById(R.id.btn_FightDetailsBack);
        btn_FightDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lbl_FightDetails_ID = findViewById(R.id.lbl_FightDetails_ID);

        img_FightDetails_UserHero = findViewById(R.id.img_FightDetails_UserHero);
        lbl_FightDetails_Username = findViewById(R.id.lbl_FightDetails_Username);
        lbl_FightDetails_UserAttack = findViewById(R.id.lbl_FightDetails_UserAttack);
        lbl_FightDetails_UserDefense = findViewById(R.id.lbl_FightDetails_UserDefense);
        lbl_FightDetails_UserHealth = findViewById(R.id.lbl_FightDetails_UserHealth);
        lbl_FightDetails_UserLevel = findViewById(R.id.lbl_FightDetails_UserLevel);

        img_FightDetails_ClickedUserHero = findViewById(R.id.img_FightDetails_ClickedUserHero);
        lbl_FightDetails_ClickedUserName = findViewById(R.id.lbl_FightDetails_ClickedUserName);
        lbl_FightDetails_ClickedUserAttack = findViewById(R.id.lbl_FightDetails_ClickedUserAttack);
        lbl_FightDetails_ClickedUserDefense = findViewById(R.id.lbl_FightDetails_ClickedUserDefense);
        lbl_FightDetails_ClickedUserHealth = findViewById(R.id.lbl_FightDetails_ClickedUserHealth);
        lbl_FightDetails_ClickedUserLevel = findViewById(R.id.lbl_FightDetails_ClickedUserLevel);

        lbl_FightDetails_Reward = findViewById(R.id.lbl_FightDetails_Reward);
        listVIew_FightRecord = findViewById(R.id.listVIew_FightRecord);
        loadingBar_FightDetailsHistory = findViewById(R.id.loadingBar_FightDetails);
        fightHistory = (FightHistory) getIntent().getSerializableExtra("fight_history");
        user = (User) getIntent().getSerializableExtra("user");
        fight_user = (User) getIntent().getSerializableExtra("clicked_user");

        lbl_FightDetails_ID.setText("FIGHT ID: " + fightHistory.getId());
        lbl_FightDetails_Reward.setText("Reward: " + fightHistory.getRewards());
        showInfo(user, img_FightDetails_UserHero, lbl_FightDetails_Username, lbl_FightDetails_UserAttack, lbl_FightDetails_UserDefense, lbl_FightDetails_UserHealth, lbl_FightDetails_UserLevel);
        showInfo(fight_user, img_FightDetails_ClickedUserHero, lbl_FightDetails_ClickedUserName, lbl_FightDetails_ClickedUserAttack, lbl_FightDetails_ClickedUserDefense, lbl_FightDetails_ClickedUserHealth,lbl_FightDetails_ClickedUserLevel);
        showLoading();
        serverHandler.getFightDetails_ByFightID(fightHistory.getId(), new ServerHandler.getFightDetails_ByFightID_CallBack() {
            @Override
            public void onSuccess(List<FightDetails> fightDetailsList) {
                fightDetailsList.get(0).getFight_id();
                List<FightRecord> fightRecordList = new LinkedList<>();
                for(int i = 0; i < fightDetailsList.size(); i++)
                {
                    FightRecord fightRecord = new FightRecord();
                    Integer damage = Integer.parseInt(fightDetailsList.get(i).getDamage());
                    Integer turn = Integer.parseInt(fightDetailsList.get(i).getTurn());
                    Integer user_currentHP = Integer.parseInt(fightDetailsList.get(i).getUser_currentHP());
                    Integer fightUser_currentHP = Integer.parseInt(fightDetailsList.get(i).getFight_user_currentHP());
                    String user_HeroPic = user.getClass_HeroDetails().getClass_Hero().getHero_pic();
                    String fightUser_HeroPic = fight_user.getClass_HeroDetails().getClass_Hero().getHero_pic();
                    fightRecord.setTurn(turn);
                    fightRecord.setDamage(damage);
                    fightRecord.setUser_currentHP(user_currentHP);
                    fightRecord.setClickedUser_currentHP(fightUser_currentHP);
                    fightRecord.setUser_heroPic(user_HeroPic);
                    fightRecord.setClickedUser_heroPIc(fightUser_HeroPic);
                    fightRecordList.add(fightRecord);
                    FightDetailsCustomBase fightDetailsCustomBase = new FightDetailsCustomBase(getBaseContext(), fightRecordList);
                    listVIew_FightRecord.setAdapter(fightDetailsCustomBase);
                }
                dismissLoading();
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }
    public void showLoading() {
        loadingBar_FightDetailsHistory.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_FightDetailsHistory.setVisibility(View.GONE);
    }

    private void showInfo(User user, ImageView img_HeroPic, TextView lbl_Username, TextView lbl_Attack, TextView lbl_Defense, TextView lbl_Health, TextView lbl_Level)
    {
        serverHandler.LoadImageFromURL(user.getAvatar(), img_HeroPic);
        lbl_Username.setText(user.getUsername());
        lbl_Attack.setText("  " + user.getClass_HeroDetails().getAttach_point());
        lbl_Defense.setText("  " + user.getClass_HeroDetails().getDefense_point());
        lbl_Health.setText("  " + user.getClass_HeroDetails().getHealth_point());
        lbl_Level.setText("Level: " + user.getClass_HeroDetails().getLevel());
    }
}