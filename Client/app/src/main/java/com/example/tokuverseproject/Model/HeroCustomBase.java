package com.example.tokuverseproject.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class HeroCustomBase extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Hero> heroList;
    private ServerHandler serverHandler = new ServerHandler();
    private int selectedPosition = -1;

    public HeroCustomBase(Context ctx, List<Hero> heroList)
    {
        this.heroList = heroList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return heroList.size();
    }


    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.hero_item_list, null);
        TextView lbl_HeroName = view.findViewById(R.id.lbl_HeroName);
        TextView lbl_HeroDes = view.findViewById(R.id.lbl_HeroDescription);
        ImageView img_heroPic = view.findViewById(R.id.imgView_HeroPic);
        ImageView img_FullPic = view.findViewById(R.id.img_HeroFullPic);
        lbl_HeroName.setText(heroList.get(i).getHero_name());
        lbl_HeroDes.setText(heroList.get(i).getDescription());
        serverHandler.LoadImageFromURL(heroList.get(i).getHero_pic(), img_heroPic);
        serverHandler.LoadImageFromURL(heroList.get(i).getFull_pic(), img_FullPic);

        if (i == selectedPosition) {
            view.setBackgroundColor(Color.CYAN); // Change to your desired color
        } else {
            view.setBackgroundColor(Color.TRANSPARENT); // Set default color here
        }
        return view;
    }
}
