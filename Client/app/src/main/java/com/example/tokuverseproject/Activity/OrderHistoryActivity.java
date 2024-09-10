package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.tokuverseproject.Model.Order;
import com.example.tokuverseproject.Model.OrderHistoryCustomBase;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    User user;
    ProgressBar loadingBar_OrderHistory;
    ListView listView_OrderHistory;
    ImageView btn_OrderHistoryBack;
    ServerHandler serverHandler = new ServerHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        user = (User) getIntent().getSerializableExtra("user");
        loadingBar_OrderHistory = findViewById(R.id.loadingBar_OrderHistory);
        listView_OrderHistory = findViewById(R.id.listView_OrderHistory);
        btn_OrderHistoryBack = findViewById(R.id.btn_OrderHistoryBack);

        btn_OrderHistoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        showLoading();
        serverHandler.getOrder_ByUserId(user.getId(), new ServerHandler.getOrder_ByUserId_CallBack() {
            @Override
            public void onSuccess(List<Order> orderList) {
                OrderHistoryCustomBase orderHistoryCustomBase = new OrderHistoryCustomBase(getApplicationContext(), orderList, OrderHistoryActivity.this);
                listView_OrderHistory.setAdapter(orderHistoryCustomBase);
                dismissLoading();
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }
    public void showLoading() {
        loadingBar_OrderHistory.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_OrderHistory.setVisibility(View.GONE);
    }
}