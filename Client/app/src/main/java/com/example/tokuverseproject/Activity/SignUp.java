package com.example.tokuverseproject.Activity;

import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.API;
import com.example.tokuverseproject.ServerAPI.ServerHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
    Button btn_SignUp;
    TextView txt_Username;
    TextView txt_Password;
    TextView txt_Email;
    TextView txt_PhoneNumber;

    ServerHandler serverHandler = new ServerHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn_SignUp = findViewById(R.id.btn_SignUp);


        txt_Username = findViewById(R.id.txt_Username);
        txt_Password = findViewById(R.id.txt_Password);
        txt_Email = findViewById(R.id.txt_Email);
        txt_PhoneNumber = findViewById(R.id.txt_PhoneNumber);
        btn_SignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                SignUp();
            }
        });
    }


    @SuppressLint("NotConstructor")
    void SignUp()
    {
        String userName = txt_Username.getText().toString();
        String password = txt_Password.getText().toString();
        String email = txt_Email.getText().toString();
        String phoneNumber = txt_PhoneNumber.getText().toString();
        User user = new User(userName, password, email, phoneNumber);
        serverHandler.signUp(user, new ServerHandler.signUp_CallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(SignUp.this, "Sign up success",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailed(String message) {

            }
        });

    }
}