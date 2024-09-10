package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.tokuverseproject.Model.FightHistory;
import com.example.tokuverseproject.Model.FightHistoryCustomBase;
import com.example.tokuverseproject.Model.Hero;
import com.example.tokuverseproject.Model.HeroDetails;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class FightHistoryActivity extends AppCompatActivity {
    ServerHandler serverHandler = new ServerHandler();
    ImageView btn_FightHistoryBack;
    ListView listView_FightHistory;
    ProgressBar loadingBar_FightHistory;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight_history);
        btn_FightHistoryBack = findViewById(R.id.btn_FightHistoryBack);
        listView_FightHistory = findViewById(R.id.listView_FightHistory);
        loadingBar_FightHistory = findViewById(R.id.loadingBar_FightHistory);
        user = (User) getIntent().getSerializableExtra("user");
        btn_FightHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        serverHandler.getHeroDetails_ByUserID(user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
            @Override
            public void onSuccess(HeroDetails heroDetails) {
                user.setClass_HeroDetails(heroDetails);
                serverHandler.getHero_ByID(heroDetails.getHero_id(), new ServerHandler.CallBack() {
                    @Override
                    public void getHero_ByID_Success(Hero hero) {
                        user.getClass_HeroDetails().setClass_Hero(hero);
                        serverHandler.getFightHistory_ByUserId(user.getId(), new ServerHandler.getFightHistory_ByUserId_CallBack() {
                            @Override
                            public void onSuccess(List<FightHistory> fightHistoryList) {
                                FightHistoryCustomBase fightHistoryCustomBase = new FightHistoryCustomBase(getBaseContext(), fightHistoryList, FightHistoryActivity.this, user);
                                listView_FightHistory.setAdapter(fightHistoryCustomBase);
                            }

                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }
        });
    }

    public void showLoading() {
        loadingBar_FightHistory.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_FightHistory.setVisibility(View.GONE);
    }
}