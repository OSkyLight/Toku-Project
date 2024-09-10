package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.tokuverseproject.Model.SearchCustomBase;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.LinkedList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btn_Back;

    User user;
    EditText txt_Search;
    ListView listView_Search_User;
    ProgressBar loadingBar_Search;
    List<User> searchList = new LinkedList<>();

    ServerHandler serverHandler = new ServerHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btn_Back = findViewById(R.id.btn_Back);
        txt_Search = findViewById(R.id.txt_Search);
        listView_Search_User = findViewById(R.id.listView_Search_User);
        loadingBar_Search = findViewById(R.id.loadingBar_Search);
        user = (User) getIntent().getSerializableExtra("user");

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        showLoading();
        serverHandler.getUser(new ServerHandler.getUser_CallBack() {
            @Override
            public void onSuccess(List<User> userList) {
                for(int i = 0; i < userList.size(); i++)
                {
                    if(userList.get(i).getId().equals(user.getId()) == false)
                    {
                        searchList.add(userList.get(i));
                    }
                }
                SearchCustomBase searchCustomBase = new SearchCustomBase(getBaseContext(), searchList, user, SearchActivity.this);
                listView_Search_User.setAdapter(searchCustomBase);
                txt_Search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String searchText = s.toString();
                        List<User> search_user_list = new LinkedList<>();
                        for(int i = 0; i < searchList.size(); i++)
                        {
                            if(searchList.get(i).getUsername().toLowerCase().contains(searchText.toLowerCase()))
                            {
                                search_user_list.add(searchList.get(i));
                            }
                        }
                        SearchCustomBase searchCustomBase = new SearchCustomBase(getBaseContext(), search_user_list, user, SearchActivity.this);
                        listView_Search_User.setAdapter(searchCustomBase);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {                    }
                });
                dismissLoading();
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    public void showLoading() {
        loadingBar_Search.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_Search.setVisibility(View.GONE);
    }
}