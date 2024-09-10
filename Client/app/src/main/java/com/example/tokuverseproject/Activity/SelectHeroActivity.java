package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tokuverseproject.Model.Hero;
import com.example.tokuverseproject.Model.HeroCustomBase;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectHeroActivity extends AppCompatActivity {

    ImageView btn_Back;
    ServerHandler serverHandler = new ServerHandler();
    Button btn_Save;
    ListView listView_Hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hero);
        listView_Hero = findViewById(R.id.hero_listView);
        btn_Back = findViewById(R.id.btn_Back);
        btn_Save = findViewById(R.id.btn_Save);
        String userId = getIntent().getStringExtra("userID");
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        serverHandler.getHero(new ServerHandler.getHero_CallBack() {
            @Override
            public void onSuccess(List<Hero> heroList) {
                HeroCustomBase heroCustomBaseAdapter = new HeroCustomBase(getApplicationContext(), heroList);
                listView_Hero.setAdapter(heroCustomBaseAdapter);
                listView_Hero.setOnItemClickListener((parent, view, position, id) -> {
                    heroCustomBaseAdapter.setSelectedPosition(position);
                    String hero_id = heroList.get(position).getId();
                    btn_Save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            serverHandler.selectHero(userId, hero_id, new ServerHandler.selectHero_CallBack() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(SelectHeroActivity.this, "Select Hero Success",
                                            Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }

                                @Override
                                public void onFail(String message) {

                                }
                            });
                        }
                    });
                });
            }

            @Override
            public void onFail(String message)
            {

            }
        });
    }
}