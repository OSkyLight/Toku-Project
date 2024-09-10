package com.example.tokuverseproject.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokuverseproject.Activity.SearchActivity;
import com.example.tokuverseproject.Activity.UserPageActivity;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class SearchCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    List<User> searchList;
    ServerHandler serverHandler = new ServerHandler();
    User user;
    SearchActivity searchActivity;
    public SearchCustomBase(Context ctx, List<User> searchList, User user, SearchActivity searchActivity)
    {
        inflater = LayoutInflater.from(ctx);
        this.searchActivity = searchActivity;
        this.user = user;
        this.searchList = searchList;
    }
    @Override
    public int getCount() {
        return searchList.size();
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
        view = inflater.inflate(R.layout.search_item_list, null);
        ImageView img_Search_UserAvatar = view.findViewById(R.id.img_Search_UserAvatar);
        TextView lbl_Search_UserName = view.findViewById(R.id.lbl_Search_UserName);
        ImageView img_Search_UserHeroPic = view.findViewById(R.id.img_Search_UserHeroPic);
        serverHandler.LoadImageFromURL(searchList.get(i).getAvatar(), img_Search_UserAvatar);
        lbl_Search_UserName.setText(searchList.get(i).getUsername());
        User clicked_user = searchList.get(i);
        searchActivity.showLoading();
        serverHandler.getHeroDetails_ByUserID(clicked_user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
            @Override
            public void onSuccess(HeroDetails heroDetails) {
                clicked_user.setClass_HeroDetails(heroDetails);
                serverHandler.getHero_ByID(clicked_user.getClass_HeroDetails().getHero_id(), new ServerHandler.CallBack() {
                    @Override
                    public void getHero_ByID_Success(Hero hero) {
                        clicked_user.getClass_HeroDetails().setClass_Hero(hero);
                        serverHandler.LoadImageFromURL(hero.getHero_pic(), img_Search_UserHeroPic);
                        searchActivity.dismissLoading();
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchActivity.showLoading();
                Intent intent = new Intent(inflater.getContext(), UserPageActivity.class);
                intent.putExtra("user", user);
                User clicked_user = searchList.get(i);
                serverHandler.getHeroDetails_ByUserID(clicked_user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
                    @Override
                    public void onSuccess(HeroDetails heroDetails) {
                        clicked_user.setClass_HeroDetails(heroDetails);
                        serverHandler.getHero_ByID(clicked_user.getClass_HeroDetails().getHero_id(), new ServerHandler.CallBack() {
                            @Override
                            public void getHero_ByID_Success(Hero hero) {
                                clicked_user.getClass_HeroDetails().setClass_Hero(hero);
                                searchActivity.dismissLoading();
                                intent.putExtra("clicked_user", clicked_user);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                inflater.getContext().startActivity(intent);
                            }

                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }
                });
            }
        });
        return view;
    }
}
