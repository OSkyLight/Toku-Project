package com.example.tokuverseproject.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokuverseproject.Activity.StoreFragment;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class StoreCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    List<Product> productList;
    ServerHandler serverHandler = new ServerHandler();
    StoreFragment storeFragment;

    public StoreCustomBase(Context ctx, List<Product> productList, StoreFragment storeFragment)
    {
        this.productList = productList;
        this.storeFragment = storeFragment;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return productList.size();
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
        view = inflater.inflate(R.layout.store_item_list, null);
        ImageView img_Store_ProductBox = view.findViewById(R.id.img_Store_ProductBox);
        TextView lbl_Store_ProductName = view.findViewById(R.id.lbl_Store_ProductName);
        TextView lbl_Store_ProductPrice = view.findViewById(R.id.lbl_Store_ProductPrice);
        Button btn_Store_AddToCart = view.findViewById(R.id.btn_Store_AddToCart);

        String[] picture_url = productList.get(i).getPicture().split("\\s+");
        serverHandler.LoadImageFromURL(picture_url[0], img_Store_ProductBox);
        lbl_Store_ProductName.setText(productList.get(i).getName());
        lbl_Store_ProductPrice.setText("  " + productList.get(i).getPrice());
        btn_Store_AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the corresponding product to the cart
                storeFragment.addToCart(productList.get(i));
            }
        });
        return view;
    }
}
