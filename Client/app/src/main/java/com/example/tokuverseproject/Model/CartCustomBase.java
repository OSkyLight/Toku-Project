package com.example.tokuverseproject.Model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.tokuverseproject.Activity.CheckOutActivity;
import com.example.tokuverseproject.Activity.CreatePostActivity;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.ArrayList;
import java.util.List;

public class CartCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Cart> cart;
    ServerHandler serverHandler = new ServerHandler();
    CheckOutActivity checkOutActivity;
    public CartCustomBase(Context ctx,  ArrayList<Cart> cart, CheckOutActivity checkOutActivity)
    {
        this.cart = cart;
        this.checkOutActivity = checkOutActivity;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {

        return cart.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.cart_item_list, null);
        ImageView img_Cart_ProductImage = view.findViewById(R.id.img_Cart_ProductImage);
        TextView lbl_Cart_ProductName = view.findViewById(R.id.lbl_Cart_ProductName);
        TextView lbl_Cart_ProductPrice = view.findViewById(R.id.lbl_Cart_ProductPrice);
        TextView lbl_Cart_ProductQuantity = view.findViewById(R.id.lbl_Cart_ProductQuantity);
        ImageButton btn_Cart_ProductQuantityMinus = view.findViewById(R.id.btn_Cart_ProductQuantityMinus);
        ImageButton btn_Cart_ProductQuantityPlus = view.findViewById(R.id.btn_Cart_ProductQuantityPlus);
        Button btn_Cart_ProductRemove = view.findViewById(R.id.btn_Cart_ProdcutRemove);
        serverHandler.LoadImageFromURL(cart.get(i).product.picture, img_Cart_ProductImage);
        lbl_Cart_ProductName.setText(cart.get(i).product.name);
        lbl_Cart_ProductPrice.setText(cart.get(i).product.price);
        lbl_Cart_ProductQuantity.setText(cart.get(i).quantity.toString());
        Drawable drawable = ContextCompat.getDrawable(inflater.getContext(), R.drawable.minus);
        btn_Cart_ProductQuantityMinus.setImageDrawable(drawable);
        drawable = ContextCompat.getDrawable(inflater.getContext(), R.drawable.plus);
        btn_Cart_ProductQuantityPlus.setImageDrawable(drawable);
        btn_Cart_ProductQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.get(i).setQuantity(cart.get(i).quantity + 1);
                lbl_Cart_ProductQuantity.setText(cart.get(i).quantity.toString());
                checkOutActivity.changQuantity(cart);
            }
        });
        btn_Cart_ProductQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.get(i).quantity > 1)
                {
                    cart.get(i).setQuantity(cart.get(i).quantity - 1);
                    lbl_Cart_ProductQuantity.setText(cart.get(i).quantity.toString());
                    checkOutActivity.changQuantity(cart);
                }

            }
        });
        btn_Cart_ProductRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOutActivity.removeCart(cart.get(i).product);
            }
        });
        return view;
    }
}
