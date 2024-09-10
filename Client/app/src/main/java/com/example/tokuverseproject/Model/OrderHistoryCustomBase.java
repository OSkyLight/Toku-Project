package com.example.tokuverseproject.Model;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.tokuverseproject.Activity.OrderDetailsHistoryActivity;
import com.example.tokuverseproject.Activity.OrderHistoryActivity;
import com.example.tokuverseproject.Activity.UserPageActivity;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class OrderHistoryCustomBase extends BaseAdapter
{
    LayoutInflater inflater;
    List<Order> orderList;
    ServerHandler serverHandler = new ServerHandler();
    OrderHistoryActivity orderHistoryActivity;

    public OrderHistoryCustomBase(Context ctx, List<Order> orderList, OrderHistoryActivity orderHistoryActivity)
    {
        inflater = LayoutInflater.from(ctx);
        this.orderList = orderList;
        this.orderHistoryActivity = orderHistoryActivity;
    }
    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.order_history_item_list, null);
        TextView lbl_OrderHistory_TotalPrice = view.findViewById(R.id.lbl_OrderHistory_TotalPrice);
        TextView lbl_OrderHistory_Address = view.findViewById(R.id.lbl_OrderHistory_Address);
        ImageView img = view.findViewById(R.id.img);
        lbl_OrderHistory_TotalPrice.setText(orderList.get(i).total_price);
        lbl_OrderHistory_Address.setText(orderList.get(i).address);
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), R.drawable.icon_store);
        img.setImageDrawable(drawable);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderHistoryActivity.showLoading();
                serverHandler.getOrderDetails_ByOrderId(orderList.get(i).id, new ServerHandler.getOrderDetails_ByOrderId_CallBack() {
                    @Override
                    public void onSuccess(List<OrderDetails> orderDetailsList) {
                        orderList.get(i).setOrderDetailsList(orderDetailsList);
                        Intent intent = new Intent(inflater.getContext(), OrderDetailsHistoryActivity.class);
                        intent.putExtra("order_history", orderList.get(i));
                        try
                        {
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            inflater.getContext().startActivity(intent);
                        }
                        catch (Exception e)
                        {
                            Log.d("ERRROR", e.getMessage());
                        }
                        orderHistoryActivity.dismissLoading();
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }
        });
        return view;
    }
}
