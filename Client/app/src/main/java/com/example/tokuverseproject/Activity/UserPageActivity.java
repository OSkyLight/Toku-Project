package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tokuverseproject.Model.Hero;
import com.example.tokuverseproject.Model.HeroDetails;
import com.example.tokuverseproject.Model.NewFeedCustomBase;
import com.example.tokuverseproject.Model.NewFeeds;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.LinkedList;
import java.util.List;

public class UserPageActivity extends AppCompatActivity {

    ImageView btn_UserPageBack, img_UserPage_Avatar, img_UserPage_HeroImage;
    TextView lbl_UserPage_Username, lbl_UserPage_HeroLevel;
    Button btn_UserPage_Fight;
    User user, clicked_user;
    ListView listView_UserPage_NewsFeed;
    ProgressBar loadingBar_UserPage;
    ServerHandler serverHandler = new ServerHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        user = (User) getIntent().getSerializableExtra("user");
        clicked_user = (User) getIntent().getSerializableExtra("clicked_user");

        img_UserPage_Avatar = findViewById(R.id.img_UserPage_Avatar);
        img_UserPage_HeroImage = findViewById(R.id.img_UserPage_HeroImage);
        lbl_UserPage_Username = findViewById(R.id.lbl_UserPage_Username);
        lbl_UserPage_HeroLevel = findViewById(R.id.lbl_UserPage_HeroLevel);
        btn_UserPage_Fight = findViewById(R.id.btn_UserPage_Fight);
        loadingBar_UserPage = findViewById(R.id.loadingBar_Store);
        listView_UserPage_NewsFeed = findViewById(R.id.listView_UserPage_NewsFeed);

        showLoading();
        serverHandler.LoadImageFromURL(clicked_user.getAvatar(), img_UserPage_Avatar);
        serverHandler.LoadImageFromURL(clicked_user.getClass_HeroDetails().getClass_Hero().getFull_pic(), img_UserPage_HeroImage);
        lbl_UserPage_Username.setText(clicked_user.getUsername());
        lbl_UserPage_HeroLevel.setText("Level: " + clicked_user.getClass_HeroDetails().getLevel());
        dismissLoading();
        List<NewFeeds> clickedUser_NewsFeeds = new LinkedList<>();
        if(user.getId().equals(clicked_user.getId()))
        {
            btn_UserPage_Fight.setVisibility(View.GONE);
        }
        showLoading();
        serverHandler.getNewFeedAction(new ServerHandler.GetNewFeeds_CallBack() {
            @Override
            public void onSuccess(List<NewFeeds> newFeedsList) {

                for(int i = 0; i < newFeedsList.size(); i++)
                {
                    if(newFeedsList.get(i).getUser_id().equals(clicked_user.getId()))
                    {
                        clickedUser_NewsFeeds.add(newFeedsList.get(i));
                    }
                }
                NewFeedCustomBase.UserPageLoadingCallback callback = new NewFeedCustomBase.UserPageLoadingCallback() {
                    @Override
                    public void onUserPageLoadingStart() {
                        showLoading();
                    }

                    @Override
                    public void onUserPageLoadingFinish() {
                        dismissLoading();
                    }
                };
                NewFeedCustomBase newFeedCustomBase = new NewFeedCustomBase(getApplicationContext(), clickedUser_NewsFeeds, user, callback);
                listView_UserPage_NewsFeed.setAdapter(newFeedCustomBase);
                dismissLoading();
            }

            @Override
            public void onFailed(String message) {

            }
        });


        btn_UserPageBack = findViewById(R.id.btn_UserPageBack);
        btn_UserPageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                serverHandler.GetUserByID(user.getId(), new ServerHandler.GetUserByID_CallBack() {
                    @Override
                    public void onSuccess(User user) {
                        Intent intent = new Intent(UserPageActivity.this, HomeActivity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("scene", "1");
                        dismissLoading();
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(String message) {

                    }
                });

            }
        });
        btn_UserPage_Fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                serverHandler.getHeroDetails_ByUserID(user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
                    @Override
                    public void onSuccess(HeroDetails heroDetails) {
                        user.setClass_HeroDetails(heroDetails);
                        serverHandler.getHero_ByID(user.getClass_HeroDetails().getHero_id(), new ServerHandler.CallBack() {
                            @Override
                            public void getHero_ByID_Success(Hero hero) {
                                dismissLoading();
                                user.getClass_HeroDetails().setClass_Hero(hero);
                                Intent intent = new Intent(UserPageActivity.this, FightActivity.class);
                                intent.putExtra("user", user);
                                intent.putExtra("clicked_user", clicked_user);
                                startActivity(intent);
                            }
                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }
                });
            }
        });
    }

    public void showLoading() {
        loadingBar_UserPage.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_UserPage.setVisibility(View.GONE);
    }
}