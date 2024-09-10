package com.example.tokuverseproject.Activity;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tokuverseproject.Model.Hero;
import com.example.tokuverseproject.Model.HeroDetails;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;


public class ProfileFragment extends Fragment {

    ServerHandler serverHandler = new ServerHandler();
    Bundle bundle = new Bundle();
    User user;
    ImageView img_ProfileUserAvatar;
    TextView lbl_Username, lbl_Email, lbl_PhoneNumber, lbl_coins;
    Button btn_HeroInfo;
    Button btn_LogOut;
    ProgressBar loadingBar_Profile;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if(bundle != null)
        {
            try {
                user = (User) bundle.getSerializable("user");
            }
            catch (Exception e)
            {
                Log.d("Error", e.getMessage());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        lbl_Username = view.findViewById(R.id.lbl_Username);
        lbl_Email = view.findViewById(R.id.lbl_Email);
        lbl_PhoneNumber = view.findViewById(R.id.lbl_MobilePhone);
        lbl_coins = view.findViewById(R.id.lbl_coins);
        img_ProfileUserAvatar = view.findViewById(R.id.img_ProfileUserAvatar);
        btn_HeroInfo = view.findViewById(R.id.btn_GoToHero);
        btn_LogOut = view.findViewById(R.id.btn_LogOut);

        lbl_Username.setText("  " + user.getUsername());
        lbl_Email.setText("  " + user.getEmail());
        lbl_PhoneNumber.setText("  " + user.getPhone_number());
        lbl_coins.setText("  " + user.getCoins());
        serverHandler.LoadImageFromURL(user.getAvatar(), img_ProfileUserAvatar);

        loadingBar_Profile = view.findViewById(R.id.loadingBar_Store);

        btn_HeroInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                gotoSelectHero();
            }
        });
        btn_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    void gotoSelectHero()
    {
        showLoading();
        serverHandler.GetUserByID(user.getId(), new ServerHandler.GetUserByID_CallBack() {
            @Override
            public void onSuccess(User user) {
                if(user.getHero_details_id().equals("0"))
                {
                    dismissLoading();
                    Intent intent;
                    intent = new Intent(getActivity(), SelectHeroActivity.class);
                    intent.putExtra("userID", user.getId());
                    getActivity().startActivity(intent);

                }
                else
                {
                    dismissLoading();
                    serverHandler.getHeroDetails_ByUserID(user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
                        @Override
                        public void onSuccess(HeroDetails heroDetails) {
                            user.setClass_HeroDetails(heroDetails);
                            serverHandler.getHero_ByID(user.getClass_HeroDetails().getHero_id(), new ServerHandler.CallBack() {
                                @Override
                                public void getHero_ByID_Success(Hero hero) {
                                    user.getClass_HeroDetails().setClass_Hero(hero);
                                    dismissLoading();
                                    Intent intent;
                                    intent = new Intent(getActivity(), HeroInfoActivity.class);
                                    intent.putExtra("user", user);
                                    getActivity().startActivity(intent);
                                }

                                @Override
                                public void onFailed(String message) {
                                    dismissLoading();
                                }
                            });

                        }
                    });
                }
            }

            @Override
            public void onFail(String message) {

            }
        });

    }

    private void showLoading() {
        loadingBar_Profile.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    private void dismissLoading() {
        loadingBar_Profile.setVisibility(View.GONE);
    }
}