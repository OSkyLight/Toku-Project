package com.example.tokuverseproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.tokuverseproject.Model.NewFeedCustomBase;
import com.example.tokuverseproject.Model.NewFeeds;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;


public class HomeFragment extends Fragment implements NewFeedCustomBase.UserPageLoadingCallback {

    Bundle bundle = new Bundle();
    User user;

    ImageView img_Avatar;
    ServerHandler serverHandler = new ServerHandler();
    ListView listView_NewFeeds;

    ImageButton btn_HomeSearch;
    Button btn_CreatePost;
    ProgressBar loadingBar_Home;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if(bundle != null)
        {
            user = (User) bundle.getSerializable("user");
        }
        Log.d("Home UserID",user.getId());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_Avatar = view.findViewById(R.id.image_HomeUserAvatar);
        listView_NewFeeds = view.findViewById(R.id.listView_Post);
        loadingBar_Home = view.findViewById(R.id.loadingBar_Store);

        btn_CreatePost = view.findViewById(R.id.btn_CreatePost);
        btn_CreatePost.setText("What on your mind, " + user.getUsername() + " ?");
        serverHandler.LoadImageFromURL(user.getAvatar(), img_Avatar);
        btn_HomeSearch = view.findViewById(R.id.btn_HomeSearch);


        Context appContext = requireContext().getApplicationContext();
        showLoading();
        serverHandler.getNewFeedAction(new ServerHandler.GetNewFeeds_CallBack() {
            @Override
            public void onSuccess(List<NewFeeds> newFeedsList) {
                NewFeedCustomBase.UserPageLoadingCallback callback = new NewFeedCustomBase.UserPageLoadingCallback() {
                    @Override
                    public void onUserPageLoadingStart() {
                        showLoading();
                    }

                    @Override
                    public void onUserPageLoadingFinish() {
                        dismissLoading();
                    }
                };
                NewFeedCustomBase newFeedCustomBase = new NewFeedCustomBase(appContext, newFeedsList, user, callback);
                listView_NewFeeds.setAdapter(newFeedCustomBase);
                dismissLoading();
            }

            @Override
            public void onFailed(String message) {
                dismissLoading();
            }
        });

        btn_CreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPostAction();
            }
        });
        btn_HomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("user", user);
                getActivity().startActivity(intent);
            }
        });
        return view;

    }

    // Method to show loading screen
    public void showLoading() {
        loadingBar_Home.setVisibility(View.VISIBLE);
    }

    // Method to dismiss loading screen
    public void dismissLoading() {
        loadingBar_Home.setVisibility(View.GONE);
    }
    void createPostAction()
    {
        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
        intent.putExtra("userID", user.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public void onUserPageLoadingStart() {
        showLoading();
    }

    @Override
    public void onUserPageLoadingFinish() {
        dismissLoading();
    }
}