package com.example.tokuverseproject.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokuverseproject.Activity.OrderDetailsHistoryActivity;
import com.example.tokuverseproject.Activity.OrderHistoryActivity;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class OrderDetailsHistoryCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    List<OrderDetails> orderDetailsList;
    ServerHandler serverHandler = new ServerHandler();
    OrderDetailsHistoryActivity orderDetailsHistoryActivity;
    public OrderDetailsHistoryCustomBase(Context ctx, List<OrderDetails> orderDetailsList, OrderDetailsHistoryActivity orderDetailsHistoryActivity)
    {
        inflater = LayoutInflater.from(ctx);
        this.orderDetailsList = orderDetailsList;
        this.orderDetailsHistoryActivity = orderDetailsHistoryActivity;
    }
    @Override
    public int getCount() {
        return orderDetailsList.size();
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
        view = inflater.inflate(R.layout.cart_item_list, null);
        ImageView img_Cart_ProductImage = view.findViewById(R.id.img_Cart_ProductImage);
        TextView lbl_Cart_ProductName = view.findViewById(R.id.lbl_Cart_ProductName);
        TextView lbl_Cart_ProductPrice = view.findViewById(R.id.lbl_Cart_ProductPrice);
        TextView lbl_Cart_ProductQuantity = view.findViewById(R.id.lbl_Cart_ProductQuantity);
        Button btn_Cart_ProdcutRemove = view.findViewById(R.id.btn_Cart_ProdcutRemove);
        btn_Cart_ProdcutRemove.setVisibility(View.GONE);
        lbl_Cart_ProductQuantity.setText("Quantity: " + orderDetailsList.get(i).quantity);
        orderDetailsHistoryActivity.showLoading();
        serverHandler.getProduct_ById(orderDetailsList.get(i).product_id, new ServerHandler.getProduct_ById_CallBack() {
            @Override
            public void onSuccess(Product product) {
                String[] picture_url = product.getPicture().split("\\s+");
                serverHandler.LoadImageFromURL(picture_url[0], img_Cart_ProductImage);
                lbl_Cart_ProductName.setText(product.name);
                lbl_Cart_ProductPrice.setText(product.price);
                orderDetailsHistoryActivity.dismissLoading();
            }

            @Override
            public void onFailed(String message) {
                Log.d("ERRRROR", message);
            }
        });
        return view;
    }
}
