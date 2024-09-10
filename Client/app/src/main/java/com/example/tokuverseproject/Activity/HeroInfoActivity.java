package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokuverseproject.Model.Hero;
import com.example.tokuverseproject.Model.HeroDetails;
import com.example.tokuverseproject.Model.JSON_MESSAGE;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

public class HeroInfoActivity extends AppCompatActivity {

    TextView lbl_HeroName, lbl_Attack, lbl_Defend, lbl_Heath, lbl_Level, lbl_exp, lbl_Attribute;
    ImageView btnBack, img_HeroInfoFullPic;
    String userID;
    ProgressBar exp_bar;
    ServerHandler serverHandler = new ServerHandler();
    ImageButton btn_AttackPlus, btn_AttackMinus, btn_DefendPlus, btn_DefendMinus, btn_HealthPlus, btn_HealthMinus;
    Button btn_Features;
    ProgressBar loadingBar_HeroInfo;

    private static final int REQUEST_CODE_HERO_INFO = 001;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_info);
        lbl_HeroName = findViewById(R.id.lbl_HeroInfoName);
        lbl_Attack = findViewById(R.id.lbl_attackPoint);
        lbl_Defend = findViewById(R.id.lbl_defendPoint);
        lbl_Heath = findViewById(R.id.lbl_healthPoint);
        btnBack = findViewById(R.id.imgView_BackBtn);
        btn_Features = findViewById(R.id.btn_HeroInfoFeatures);
        img_HeroInfoFullPic = findViewById(R.id.imgView_HeroInfoFullPic);
        lbl_Level = findViewById(R.id.lbl_level);
        lbl_exp = findViewById(R.id.lbl_exp);
        lbl_Attribute = findViewById(R.id.lbl_Attribute);
        btn_AttackPlus = findViewById(R.id.btn_AttackPlus);
        btn_AttackMinus = findViewById(R.id.btn_AttackMinus);
        btn_DefendPlus = findViewById(R.id.btn_DefendPlus);
        btn_DefendMinus = findViewById(R.id.btn_DefendMinus);
        btn_HealthPlus = findViewById(R.id.btn_HealthPlus);
        btn_HealthMinus = findViewById(R.id.btn_HealthMinus);
        exp_bar = findViewById(R.id.exp_bar);
        loadingBar_HeroInfo = findViewById(R.id.loadingBar_HeroInfo);

        // Get user object from Intent
        user = (User) getIntent().getSerializableExtra("user");
        if (user != null)
        {
            LoadHero(user);
            adjust_Attribute(user);
        }
        else
        {
            Log.e("LoadHero", "User object is null");
        }

        // Back button click listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private boolean isRequestCompleted = false;
    @Override
    public void onBackPressed() {
        if (!isRequestCompleted) {
            showLoading();
            serverHandler.getHeroDetails_ByUserID(user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
                @Override
                public void onSuccess(HeroDetails heroDetails) {
                    dismissLoading();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("user", user);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
            });
            isRequestCompleted = true;
        }
        else
        {
            super.onBackPressed();
        }
    }

    void adjust_Attribute(User user)
    {   HeroDetails heroDetails = user.getClass_HeroDetails();
        final Integer[] current_attack = new Integer[1];
        final Integer[] current_defense = new Integer[1];
        final Integer[] current_health = new Integer[1];
        final Integer[] current_attribute = new Integer[1];
        final Integer[] max_attribute = new Integer[1];
        final Integer[] default_attack = new Integer[1];
        final Integer[] default_defense = new Integer[1];
        final Integer[] default_health = new Integer[1];

        current_attack[0] = Integer.parseInt(heroDetails.getAttach_point());
        current_defense[0] = Integer.parseInt(heroDetails.getDefense_point());
        current_health[0] = Integer.parseInt(heroDetails.getHealth_point());
        current_attribute[0] = Integer.parseInt(heroDetails.getAttribute_point());
        max_attribute[0] = Integer.parseInt(heroDetails.getAttribute_point());
        default_attack[0] = Integer.parseInt(heroDetails.getAttach_point());
        default_defense[0] = Integer.parseInt(heroDetails.getDefense_point());
        default_health[0] = Integer.parseInt(heroDetails.getHealth_point());

        btn_AttackPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (current_attribute[0] > 0 ) {
                        current_attribute[0] -= 1;
                        current_attack[0] += 1;
                        lbl_Attack.setText("  " + current_attack[0]); // Update lbl_Attack with current_attack
                        lbl_Attribute.setText("Attribute: " + current_attribute[0] + " points"); // Update lbl_Attribute with current_attribute
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
        btn_DefendPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (current_attribute[0] > 0) {
                        current_attribute[0] -= 1;
                        current_defense[0] += 1;
                        lbl_Defend.setText("  " + current_defense[0]);
                        lbl_Attribute.setText("Attribute: " + current_attribute[0] + " points"); // Update lbl_Attribute with current_attribute
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
        btn_HealthPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (current_attribute[0] > 0) {
                        current_attribute[0] -= 1;
                        current_health[0] += 1;
                        lbl_Heath.setText("  " + current_health[0]);
                        lbl_Attribute.setText("Attribute: " + current_attribute[0] + " points"); // Update lbl_Attribute with current_attribute
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
        btn_AttackMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(current_attribute[0] < max_attribute[0] && current_attack[0] > default_attack[0])
                    {
                        current_attribute[0] += 1;
                        current_attack[0] -= 1;
                        lbl_Attack.setText("  " + current_attack[0]);
                        lbl_Attribute.setText("Attribute: " + current_attribute[0] + " points");
                    }
                }catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
        btn_DefendMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(current_attribute[0] < max_attribute[0] && current_defense[0] > default_defense[0])
                    {
                        current_attribute[0] += 1;
                        current_defense[0] -= 1;
                        lbl_Defend.setText("  " + current_defense[0]);
                        lbl_Attribute.setText("Attribute: " + current_attribute[0] + " points");
                    }
                }catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });
        btn_HealthMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(current_attribute[0] < max_attribute[0] && current_health[0] > default_health[0])
                    {
                        current_attribute[0] += 1;
                        current_health[0] -= 1;
                        lbl_Heath.setText("  " + current_health[0]);
                        lbl_Attribute.setText("Attribute: " + current_attribute[0] + " points");
                    }
                }catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }
        });

        btn_Features.setText("Change Stat");
        btn_Features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                String attack = String.valueOf(current_attack[0]);
                String defense = String.valueOf(current_defense[0]);
                String health = String.valueOf(current_health[0]);
                String attribute = String.valueOf(current_attribute[0]);
                serverHandler.changeAttribute_Action(heroDetails.getId(), attack, defense, health, attribute, new ServerHandler.ChangeAttribute_CallBack() {
                    @Override
                    public void onSuccess(JSON_MESSAGE jsonMessage) {
                        user.getClass_HeroDetails().setAttach_point(attack);
                        user.getClass_HeroDetails().setDefense_point(defense);
                        user.getClass_HeroDetails().setHealth_point(health);
                        user.getClass_HeroDetails().setAttribute_point(attribute);
                        Toast.makeText(HeroInfoActivity.this, jsonMessage.getMessage(), Toast.LENGTH_LONG).show();
                        dismissLoading();
                    }

                    @Override
                    public void onFailed(String message) {
                        dismissLoading();
                    }
                });
            }
        });
    }

    void LoadHero(User user)
    {
        showLoading();
        if (user.getClass_HeroDetails() != null)
        {
            HeroDetails heroDetails = user.getClass_HeroDetails();
            lbl_Attack.setText("  " + heroDetails.getAttach_point());
            lbl_Defend.setText("  " + heroDetails.getDefense_point());
            lbl_Heath.setText("  " + heroDetails.getHealth_point());
            lbl_exp.setText(heroDetails.getExp() + "/" + heroDetails.getMax_exp());
            lbl_Level.setText("Level " + heroDetails.getLevel());
            lbl_Attribute.setText("Attribute: " + heroDetails.getAttribute_point() + " points");

            Hero hero = user.getClass_HeroDetails().getClass_Hero();
            lbl_HeroName.setText(hero.getHero_name());
            serverHandler.LoadImageFromURL(hero.getFull_pic(), img_HeroInfoFullPic);

            int current_exp = Integer.parseInt(heroDetails.getExp().toString());
            int max_exp = Integer.parseInt(heroDetails.getMax_exp().toString());
            exp_bar.setMax(max_exp);
            exp_bar.setProgress(current_exp, true);
            dismissLoading();
        }
        else
        {
            dismissLoading();
            Log.e("LoadHero", "HeroDetails object is null");
        }
    }

    // Method to show loading screen
    private void showLoading() {
        loadingBar_HeroInfo.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    private void dismissLoading() {
        loadingBar_HeroInfo.setVisibility(View.GONE);
    }
}

