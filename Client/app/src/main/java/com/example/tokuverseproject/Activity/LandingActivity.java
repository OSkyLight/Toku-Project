package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tokuverseproject.R;

public class LandingActivity extends AppCompatActivity {

    Button btn_Test;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        btn_Test = findViewById(R.id.btn_Test);
        btn_Test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                try {

                }
                catch(Exception e)
                {
                    Log.d("Tag", e.getMessage());
                }
                Log.d("Tag", " Test Message");
                Intent intent = new Intent(LandingActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                }
                catch(Exception e)
                {
                    Log.d("Tag", e.getMessage());
                }
                Log.d("Tag", " Test Message");
                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}