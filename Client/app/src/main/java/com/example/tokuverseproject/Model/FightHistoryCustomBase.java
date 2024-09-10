package com.example.tokuverseproject.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokuverseproject.Activity.FightDetailsActivity;
import com.example.tokuverseproject.Activity.FightHistoryActivity;
import com.example.tokuverseproject.Activity.OrderDetailsHistoryActivity;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class FightHistoryCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    List<FightHistory> fightHistoryList;
    ServerHandler serverHandler = new ServerHandler();
    FightHistoryActivity fightHistoryActivity;
    User main_user;

    public FightHistoryCustomBase(Context ctx, List<FightHistory> fightHistoryList, FightHistoryActivity fightHistoryActivity, User user)
    {
        inflater = LayoutInflater.from(ctx);
        this.fightHistoryList = fightHistoryList;
        this.fightHistoryActivity = fightHistoryActivity;
        this.main_user = user;
    }
    @Override
    public int getCount() {
        return fightHistoryList.size();
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
        view = inflater.inflate(R.layout.fight_history_item, null);
        ImageView imgView_FightHis_UserHeroPic = view.findViewById(R.id.imgView_FightHis_UserHeroPic);
        TextView lbl_FightHis_UserName = view.findViewById(R.id.lbl_FightHis_UserName);
        TextView lbl_FightHis_Status = view.findViewById(R.id.lbl_FightHis_Status);
        TextView lbl_FightHis_Reward = view.findViewById(R.id.lbl_FightHis_Reward);
        ImageView imgView_FightHis_FightUserHeroPic = view.findViewById(R.id.imgView_FightHis_FightUserHeroPic);
        TextView lbl_FightHis_FightUserName = view.findViewById(R.id.lbl_FightHis_FightUserName);
        serverHandler.LoadImageFromURL(main_user.getClass_HeroDetails().getClass_Hero().getHero_pic(), imgView_FightHis_UserHeroPic);
        lbl_FightHis_UserName.setText(main_user.getUsername());
        if(fightHistoryList.get(i).getStatus().equals("win"))
        {
            lbl_FightHis_Status.setTextColor(Color.GREEN);
            lbl_FightHis_Status.setText("WIN!!!");
        }
        else
        {
            lbl_FightHis_Status.setTextColor(Color.RED);
            lbl_FightHis_Status.setText("LOSE!!!");
        }
        lbl_FightHis_Reward.setText(" " + fightHistoryList.get(i).getRewards());
        fightHistoryActivity.showLoading();
        View finalView = view;
        serverHandler.GetUserByID(fightHistoryList.get(i).getFight_user_id(), new ServerHandler.GetUserByID_CallBack() {
            @Override
            public void onSuccess(User user) {
                serverHandler.getHeroDetails_ByUserID(user.getId(), new ServerHandler.getHeroDetails_ByID_callBack() {
                    @Override
                    public void onSuccess(HeroDetails heroDetails) {
                        user.setClass_HeroDetails(heroDetails);
                        serverHandler.getHero_ByID(heroDetails.getHero_id(), new ServerHandler.CallBack() {
                            @Override
                            public void getHero_ByID_Success(Hero hero) {
                                user.getClass_HeroDetails().setClass_Hero(hero);
                                lbl_FightHis_FightUserName.setText(user.getUsername());
                                serverHandler.LoadImageFromURL(user.getClass_HeroDetails().getClass_Hero().getHero_pic(), imgView_FightHis_FightUserHeroPic);
                                fightHistoryActivity.dismissLoading();
                                finalView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.d("TETSSSTST", fightHistoryList.get(i).getId());
                                        Intent intent = new Intent(inflater.getContext(), FightDetailsActivity.class);
                                        intent.putExtra("fight_history", fightHistoryList.get(i));
                                        intent.putExtra("user", main_user);
                                        intent.putExtra("clicked_user", user);
                                        try
                                        {
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            inflater.getContext().startActivity(intent);
                                        }
                                        catch (Exception e)
                                        {
                                            Log.d("ERRROR", e.getMessage());
                                        }
                                        fightHistoryActivity.dismissLoading();
                                    }
                                });
                            }

                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onFail(String message) {

            }
        });
        return view;
    }
}
