package com.example.tokuverseproject.Activity;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tokuverseproject.Model.Cart;
import com.example.tokuverseproject.Model.Product;
import com.example.tokuverseproject.Model.StoreCustomBase;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment {

    Bundle bundle = new Bundle();
    User user;
    ImageView img_StoreUserAvatar;
    TextView lbl_StoreUserName, lbl_StoreUserCoin;
    Button btn_StoreCheckout;
    ListView listView_Store;
    ProgressBar loadingBar_Store;
    ServerHandler serverHandler = new ServerHandler();
    private static final int REQUEST_CODE_CHECKOUT = 1001;

    Integer total_product = 0;
    ArrayList<Cart> cart = new ArrayList<Cart>();
    StoreFragment storeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if(bundle != null)
        {
            try
            {
                user = (User) bundle.getSerializable("user");
            }
            catch (Exception e)
            {
                Log.d("Error", e.getMessage());
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        img_StoreUserAvatar = view.findViewById(R.id.img_StoreUserAvatar);
        lbl_StoreUserName = view.findViewById(R.id.lbl_StoreUserName);
        lbl_StoreUserCoin = view.findViewById(R.id.lbl_StoreUserCoin);
        btn_StoreCheckout = view.findViewById(R.id.btn_StoreCheckout);
        loadingBar_Store = view.findViewById(R.id.loadingBar_Store);
        lbl_StoreUserCoin.setText("  " + user.getCoins());
        lbl_StoreUserName.setText(user.getUsername());
        listView_Store = view.findViewById(R.id.listView_Store);
        Picasso.get().load(user.getAvatar()).into(img_StoreUserAvatar);

        showLoading();
        serverHandler.getProduct_Action(new ServerHandler.getProduct_CallBack() {
            @Override
            public void onSuccess(List<Product> productList) {
                StoreCustomBase storeCustomBase = new StoreCustomBase(inflater.getContext(), productList, StoreFragment.this);
                listView_Store.setAdapter(storeCustomBase);
                dismissLoading();
            }

            @Override
            public void onFailed(String message) {

            }
        });
        btn_StoreCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckOutActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("cart", cart);

                // Debugging: Print the cart contents
                Log.d("StoreFragment", "Cart contents: " + cart.toString());

                startActivityForResult(intent, REQUEST_CODE_CHECKOUT);
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHECKOUT && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<Cart> updatedCart = (ArrayList<Cart>) data.getSerializableExtra("updatedCart");
                if (updatedCart != null) {
                    cart.clear();
                    cart.addAll(updatedCart);
                    calculateProduct_InCart(cart);
                }
            }
        }
    }
    public void addToCart(Product product) {
        if(cart.size() == 0)
        {
            Cart temp_cart = new Cart();
            temp_cart.setProduct(product);
            temp_cart.setQuantity(1);
            cart.add(temp_cart);
        }
        else
        {
            for(int i = 0; i < cart.size(); i++)
            {
                if(product.equals(cart.get(i).getProduct()))
                {
                    Integer currentQuantity = cart.get(i).getQuantity();
                    cart.get(i).setQuantity(currentQuantity += 1);
                    break;
                }
                if(i == cart.size() - 1)
                {
                    Cart temp_cart = new Cart();
                    temp_cart.setProduct(product);
                    temp_cart.setQuantity(1);
                    cart.add(temp_cart);
                    break;
                }
            }
        }
        calculateProduct_InCart(cart);

    }

    public void calculateProduct_InCart(ArrayList<Cart> cart)
    {
        total_product = 0;
        for(Cart temp_cart : cart)
        {
            total_product += temp_cart.getQuantity();
        }
        btn_StoreCheckout.setText("x " + total_product.toString());
    }

    public void removeFromCart(Product product)
    {

    }


    private void showLoading() {
        loadingBar_Store.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    private void dismissLoading() {
        loadingBar_Store.setVisibility(View.GONE);
    }

}