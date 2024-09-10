package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.tokuverseproject.Model.NewFeeds;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public User user;
    Button btn_GoToSignUp;
    Button btn_Login;
    EditText txt_Username, txt_Password;
    boolean showPassword;
    private ProgressBar progressBar;


    ServerHandler serverHandler = new ServerHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.loadingBar_Main);
        btn_GoToSignUp = findViewById(R.id.btn_SignUp);
        btn_Login = findViewById(R.id.btn_Login);
        txt_Username = findViewById(R.id.txt_Username);
        txt_Password = findViewById(R.id.txt_Password);



        btn_GoToSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                gotoSignUp();
            }
        });
        btn_Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                logIn();
            }
        });
    }

    void Test()
    {
        serverHandler.getNewFeedAction(new ServerHandler.GetNewFeeds_CallBack() {
            @Override
            public void onSuccess(List<NewFeeds> newFeedsList) {

            }

            @Override
            public void onFailed(String message) {

            }
        });
    }
    void gotoSignUp()
    {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    void logIn()
    {
        String userName = txt_Username.getText().toString();
        String passWord = txt_Password.getText().toString();

        String testUN = "Test";
        String testPW = "123465";
        showLoading();
        serverHandler.LogIn(userName, passWord, new ServerHandler.LoginCallback()
        {
            @Override
            public void onSuccess(User user)
            {
                dismissLoading();
                Toast.makeText(MainActivity.this, "Log in sucessful",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("scene", "1");
                startActivity(intent);
            }
            @Override
            public void onFail(String message)
            {
                dismissLoading();
                Toast.makeText(MainActivity.this, message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    // Method to show loading screen
    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    private void dismissLoading() {
        progressBar.setVisibility(View.GONE);
    }
}