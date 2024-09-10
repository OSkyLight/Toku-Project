package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokuverseproject.Model.Cart;
import com.example.tokuverseproject.Model.CartCustomBase;
import com.example.tokuverseproject.Model.Order;
import com.example.tokuverseproject.Model.Product;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {
    User user;
    ArrayList<Cart> cart;
    ImageView btn_CheckOutBack, img_CheckOut_UserAvatar;
    TextView lbl_CheckOut_UserName, lbl_CheckOut_UserPhoneNumber, lbl_CheckOut_UserCoins, lbl_CheckOut_TotalPrice;
    Button btn_CheckOut;
    Integer totalPrice = 0;
    ProgressBar loadingBar_CheckOut;
    ListView listView_Cart;
    ServerHandler serverHandler = new ServerHandler();
    EditText txt_CheckOut_Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        user = (User) getIntent().getSerializableExtra("user");
        try {
            cart = (ArrayList<Cart>) getIntent().getSerializableExtra("cart");
        }
        catch(Exception e)
        {
            Log.d("ERRRROR", e.getMessage());
        }
        btn_CheckOutBack = findViewById(R.id.btn_CheckOutBack);
        btn_CheckOutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("updatedCart", cart);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        img_CheckOut_UserAvatar = findViewById(R.id.img_CheckOut_UserAvatar);
        lbl_CheckOut_UserName = findViewById(R.id.lbl_CheckOut_UserName);
        lbl_CheckOut_UserCoins = findViewById(R.id.lbl_CheckOut_UserCoins);
        lbl_CheckOut_UserPhoneNumber = findViewById(R.id.lbl_CheckOut_UserPhoneNumber);
        lbl_CheckOut_TotalPrice = findViewById(R.id.lbl_CheckOut_TotalPrice);
        btn_CheckOut = findViewById(R.id.btn_CheckOut);
        loadingBar_CheckOut = findViewById(R.id.loadingBar_CheckOut);
        listView_Cart = findViewById(R.id.listView_Cart);
        txt_CheckOut_Address = findViewById(R.id.txt_CheckOut_UserAddress);

        serverHandler.LoadImageFromURL(user.getAvatar(), img_CheckOut_UserAvatar);
        lbl_CheckOut_UserName.setText(user.getUsername());
        lbl_CheckOut_UserPhoneNumber.setText(user.getPhone_number());
        lbl_CheckOut_UserCoins.setText(user.getCoins() + " ");


        calculatePrice(cart);
        CartCustomBase cartCustomBase = new CartCustomBase(getBaseContext(), cart, this);
        listView_Cart.setAdapter(cartCustomBase);

        btn_CheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer user_coins = Integer.parseInt(user.getCoins());
                if(user_coins >= totalPrice)
                {
                    String address = txt_CheckOut_Address.getText().toString();
                    if (address != null && !address.isEmpty())
                    {
                        showLoading();
                        serverHandler.createOrderAction(user.getId(), txt_CheckOut_Address.getText().toString(), totalPrice.toString(), new ServerHandler.createOrder_CallBack() {
                            @Override
                            public void onSuccess(Order order) {
                                String order_id = order.getId();
                                for(Cart temp_cart : cart)
                                {
                                    String product_id = temp_cart.getProduct().getId();
                                    String quantity = temp_cart.getQuantity().toString();
                                    String price = temp_cart.getProduct().getPrice();
                                    serverHandler.createOrderDetails_Action(order_id, product_id, quantity, price, new ServerHandler.createOrderDetails_CallBack() {
                                        @Override
                                        public void onSuccess() {
                                        }

                                        @Override
                                        public void onFailed(String message) {
                                            Toast.makeText(CheckOutActivity.this, message, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                serverHandler.GetUserByID(user.getId(), new ServerHandler.GetUserByID_CallBack() {
                                    @Override
                                    public void onSuccess(User user) {
                                        Intent intent = new Intent(CheckOutActivity.this, HomeActivity.class);
                                        intent.putExtra("user", user);
                                        intent.putExtra("scene", "1");
                                        Toast.makeText(CheckOutActivity.this, "Order placed thank you", Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFail(String message) {

                                    }
                                });
                                dismissLoading();
                            }

                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(CheckOutActivity.this, "Address cannot be null", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CheckOutActivity.this, "Influential amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public interface change
    {
        void onChange(ArrayList<Cart> cart);
    }

    public void changQuantity(ArrayList<Cart> cart)
    {
        showLoading();
        this.cart = cart;
        calculatePrice(cart);
        dismissLoading();
    }
    void calculatePrice(ArrayList<Cart> cart)
    {
        totalPrice = 0;
        for(Cart temp_cart : cart)
        {
            Integer price = Integer.parseInt(temp_cart.getProduct().getPrice());
            totalPrice += price * temp_cart.getQuantity();
            lbl_CheckOut_TotalPrice.setText(totalPrice.toString());
        }
    }
    public void removeCart(Product product)
    {
        for(Cart temp_cart : cart)
        {
            if(temp_cart.getProduct().equals(product))
            {
                cart.remove(temp_cart);
                break;
            }
        }
        calculatePrice(cart);
        CartCustomBase cartCustomBase = new CartCustomBase(getBaseContext(), cart, this);
        listView_Cart.setAdapter(cartCustomBase);
    }

    private void showLoading() {
        loadingBar_CheckOut.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    private void dismissLoading() {
        loadingBar_CheckOut.setVisibility(View.GONE);
    }
}