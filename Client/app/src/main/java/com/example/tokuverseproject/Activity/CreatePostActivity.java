package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreatePostActivity extends AppCompatActivity {

    ImageView backButton, img_Avatar;
    TextView lbl_UserName;
    EditText txt_CreatePostContent;
    Button btn_PostAction;
    ServerHandler serverHandler = new ServerHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        backButton = findViewById(R.id.btn_CreatePostBack);
        img_Avatar = findViewById(R.id.img_UserPageAvatar);
        lbl_UserName = findViewById(R.id.lbl_UserPageName);
        txt_CreatePostContent = findViewById(R.id.txt_CreatePostContent);
        btn_PostAction = findViewById(R.id.btn_PostAction);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String userId = getIntent().getStringExtra("userID");
        serverHandler.GetUserByID(userId, new ServerHandler.GetUserByID_CallBack() {
            @Override
            public void onSuccess(User user) {
                lbl_UserName.setText(user.getUsername());
                serverHandler.LoadImageFromURL(user.getAvatar(), img_Avatar);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentTime = dateFormat.format(calendar.getTime());
                Log.d("Current Date time", currentTime);

                String content = txt_CreatePostContent.getText().toString();
                btn_PostAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Content", txt_CreatePostContent.getText().toString());
                        createPost(userId, txt_CreatePostContent.getText().toString(), currentTime, user.getUsername(), user.getProfile_pic());
                    }
                });
            }

            @Override
            public void onFail(String message) {

            }
        });


    }

    void createPost(String user_id, String content, String date_time, String user_name, String user_avatar)
    {
        serverHandler.createPost(user_id, content, date_time, new ServerHandler.createPost_CallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(CreatePostActivity.this, "Post sucessfull",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreatePostActivity.this, HomeActivity.class);
                intent.putExtra("userID", user_id);
                startActivity(intent);
            }
            @Override
            public void onFailed(String message)
            {
                Toast.makeText(CreatePostActivity.this, "Post failed",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}