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
import android.widget.TextView;

import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

public class HistoryFragment extends Fragment {
    Bundle bundle = new Bundle();
    User user;
    ImageView img_History_UserAvatar;
    TextView lbl_History_Username;
    Button btn_OrderHistory, btn_FightHistory;
    ServerHandler serverHandler = new ServerHandler();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        img_History_UserAvatar = view.findViewById(R.id.img_History_UserAvatar);
        lbl_History_Username = view.findViewById(R.id.lbl_History_Username);
        btn_OrderHistory = view.findViewById(R.id.btn_OrderHistory);
        btn_FightHistory = view.findViewById(R.id.btn_FightHistory);
        serverHandler.LoadImageFromURL(user.getAvatar(), img_History_UserAvatar);
        lbl_History_Username.setText(user.getUsername());
        btn_OrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                intent.putExtra("user", user);
                getActivity().startActivity(intent);
            }
        });
        btn_FightHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FightHistoryActivity.class);
                intent.putExtra("user", user);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}