package com.example.tokuverseproject.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CommentCustomBase extends BaseAdapter {
    LayoutInflater inflater;
    List<Comment> commentList;
    ServerHandler serverHandler;
    public CommentCustomBase(Context ctx, List<Comment> commentList)
    {
        this.commentList = commentList;
        inflater = LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return commentList.size();
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
        view = inflater.inflate(R.layout.comment_item_list, null);
        ImageView img_Comment_UserAvatar = view.findViewById(R.id.img_Comment_UserAvatar);
        TextView lbl_Commemt_UserName = view.findViewById(R.id.lbl_Comment_UserName);
        TextView lbl_Comment_Content = view.findViewById(R.id.lbl_Comment_Content);
        lbl_Commemt_UserName.setText(commentList.get(i).getUser_name());
        lbl_Comment_Content.setText(commentList.get(i).getContent());
        try
        {
            Log.d("User avatar", commentList.get(i).getUser_avatar());
            Picasso.get().load(commentList.get(i).getUser_avatar()).into(img_Comment_UserAvatar);
        }
        catch (Exception e)
        {
            img_Comment_UserAvatar.setImageResource(R.drawable.logo_128);
            Log.d("Failed", e.getMessage());
        }
        return view;
    }
}
