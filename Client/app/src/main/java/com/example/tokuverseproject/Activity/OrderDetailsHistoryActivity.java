package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tokuverseproject.Model.Order;
import com.example.tokuverseproject.Model.OrderDetailsHistoryCustomBase;
import com.example.tokuverseproject.R;

import org.w3c.dom.Text;

public class OrderDetailsHistoryActivity extends AppCompatActivity {

    Order order;
    TextView lbl_OrderID, lbl_OrderDetailsHistory_TotalPrice;
    ListView listView_OrderDetailsHistory;
    ImageView btn_OrderHistoryDetailsBack;
    ProgressBar loadingBar_OrderDetailHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_history);
        order = (Order) getIntent().getSerializableExtra("order_history");
        lbl_OrderID = findViewById(R.id.lbl_OrderID);
        lbl_OrderDetailsHistory_TotalPrice = findViewById(R.id.lbl_OrderDetailsHistory_TotalPrice);
        listView_OrderDetailsHistory = findViewById(R.id.listView_OrderDetailsHistory);
        btn_OrderHistoryDetailsBack = findViewById(R.id.btn_OrderHistoryDetailsBack);
        loadingBar_OrderDetailHistory = findViewById(R.id.loadingBar_OrderDetailHistory);
        btn_OrderHistoryDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        lbl_OrderID.setText("Order ID: " + order.getId());
        lbl_OrderDetailsHistory_TotalPrice.setText(" " + order.getTotal_price());

        OrderDetailsHistoryCustomBase orderDetailsHistoryCustomBase = new OrderDetailsHistoryCustomBase(getBaseContext(), order.getOrderDetailsList(), this);
        listView_OrderDetailsHistory.setAdapter(orderDetailsHistoryCustomBase);
    }
    public void showLoading() {
        loadingBar_OrderDetailHistory.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_OrderDetailHistory.setVisibility(View.GONE);
    }
}