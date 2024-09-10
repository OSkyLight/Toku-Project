package com.example.tokuverseproject.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.tokuverseproject.Activity.FightActivity;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class FightRecordCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    List<FightRecord> fightRecordList;
    ServerHandler serverHandler = new ServerHandler();
    FightActivity fightActivity;

    public FightRecordCustomBase(Context ctx, List<FightRecord> fightRecordList, FightActivity fightActivity)
    {
        this.fightRecordList = fightRecordList;
        this.fightActivity = fightActivity;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return fightRecordList.size();
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
        view = inflater.inflate(R.layout.fight_record_item, null);
        TextView lbl_FightRecord_Turn = view.findViewById(R.id.lbl_FightRecord_Turn);
        TextView lbl_FightRecord_Damage = view.findViewById(R.id.lbl_FightRecord_Damge);
        TextView lbl_FightRecord_UserHP = view.findViewById(R.id.lbl_FightRecord_UserHP);
        TextView lbl_FightRecord_ClickedUserHP = view.findViewById(R.id.lbl_FightRecord_ClickedUserHP);
        ImageView img_FightRecord_User = view.findViewById(R.id.img_FightRecord_User);
        ImageView img_FightRecord_ClickedUser = view.findViewById(R.id.img_FightRecord_ClickedUser);
        ImageView img_AttackDirection = view.findViewById(R.id.img_AttackDirection);
        Integer turn_fight = i + 1;
        lbl_FightRecord_Turn.setText("Turn: " + turn_fight.toString());
        lbl_FightRecord_Damage.setText(fightRecordList.get(i).getDamage().toString() +" damage");
        serverHandler.LoadImageFromURL(fightRecordList.get(i).getUser_heroPic(), img_FightRecord_User);
        serverHandler.LoadImageFromURL(fightRecordList.get(i).getClickedUser_heroPIc(), img_FightRecord_ClickedUser);
        lbl_FightRecord_UserHP.setText(fightRecordList.get(i).getUser_currentHP().toString());
        lbl_FightRecord_ClickedUserHP.setText(fightRecordList.get(i).getClickedUser_currentHP().toString());
        if(fightRecordList.get(i).getUser_currentHP() <= 0)
        {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            Drawable drawable = img_FightRecord_User.getDrawable();
            drawable.setColorFilter(filter);
            img_FightRecord_User.setImageDrawable(drawable);
            lbl_FightRecord_ClickedUserHP.setText("WIN");
            lbl_FightRecord_UserHP.setText("LOSE");
            lbl_FightRecord_UserHP.setTextColor(Color.RED);
            lbl_FightRecord_ClickedUserHP.setTextColor(Color.GREEN);
        }
        if(fightRecordList.get(i).getClickedUser_currentHP() <= 0)
        {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            Drawable drawable = img_FightRecord_ClickedUser.getDrawable();
            drawable.setColorFilter(filter);
            img_FightRecord_ClickedUser.setImageDrawable(drawable);
            lbl_FightRecord_ClickedUserHP.setText("LOSE");
            lbl_FightRecord_UserHP.setText("WIN");
            lbl_FightRecord_UserHP.setTextColor(Color.GREEN);
            lbl_FightRecord_ClickedUserHP.setTextColor(Color.RED);
        }
        if(fightRecordList.get(i).getTurn() == 2)
        {
            int blueColor = Color.BLUE;
            img_AttackDirection.setImageResource(R.drawable.attack_direction);
            img_AttackDirection.setColorFilter(blueColor, PorterDuff.Mode.SRC_ATOP);
        }
        else if(fightRecordList.get(i).getTurn() == 1)
        {
            int redColor = Color.RED;
            img_AttackDirection.setScaleX(-1);
            img_AttackDirection.setImageResource(R.drawable.attack_direction);
            img_AttackDirection.setColorFilter(redColor, PorterDuff.Mode.SRC_ATOP);
        }
        return view;
    }
}
