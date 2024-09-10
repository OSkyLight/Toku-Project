package com.example.tokuverseproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokuverseproject.Model.Comment;
import com.example.tokuverseproject.Model.CommentCustomBase;
import com.example.tokuverseproject.Model.JSON_MESSAGE;
import com.example.tokuverseproject.Model.Like;
import com.example.tokuverseproject.Model.NewFeeds;
import com.example.tokuverseproject.Model.User;
import com.example.tokuverseproject.R;
import com.example.tokuverseproject.ServerAPI.ServerHandler;

import java.util.List;

public class PostActivity extends AppCompatActivity {

    ServerHandler serverHandler =  new ServerHandler();
    ImageView img_ClickedUserAvatar, btn_Back;
    TextView lbl_ClickedUserName, lbl_Post_Content, lbl_Post_LikeCount, lbl_Post_CommentCount, txt_Comment;
    Button btn_Post_Like, btn_Post_Comment, btn_CreateComment;

    ListView listView_Comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        img_ClickedUserAvatar = findViewById(R.id.img_ClickedUserAvatar);
        lbl_ClickedUserName = findViewById(R.id.lbl_ClickedUserName);
        lbl_Post_Content = findViewById(R.id.lbl_Post_Content);
        lbl_Post_LikeCount = findViewById(R.id.lbl_Post_LikeCount);
        lbl_Post_CommentCount = findViewById(R.id.lbl_Post_CommentCount);
        btn_Post_Like = findViewById(R.id.btn_Post_Like);
        btn_Post_Comment = findViewById(R.id.btn_Post_Comment);
        btn_Back = findViewById(R.id.btn_PostView_Back);
        btn_CreateComment = findViewById(R.id.btn_CreateComment);
        txt_Comment = findViewById(R.id.txt_Comment);
        listView_Comment = findViewById(R.id.listView_Comment);
        String cliked_userID = getIntent().getStringExtra("cliked_userID");
        String user_ID= getIntent().getStringExtra("userID");
        String post_ID = getIntent().getStringExtra("postID");
        String is_comment = getIntent().getStringExtra("comment");
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this, HomeActivity.class);
                serverHandler.GetUserByID(user_ID, new ServerHandler.GetUserByID_CallBack() {
                    @Override
                    public void onSuccess(User user) {
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }

                    @Override
                    public void onFail(String message) {

                    }
                });
            }
        });
        serverHandler.getComment_ByNFID(post_ID, new ServerHandler.getComment_ByNFID_CallBack() {
            @Override
            public void onSuccess(List<Comment> commentList) {
                CommentCustomBase commentCustomBase = new CommentCustomBase(getApplicationContext(), commentList);
                listView_Comment.setAdapter(commentCustomBase);
            }

            @Override
            public void onFailed(String message) {

            }
        });
        serverHandler.GetUserByID(cliked_userID, new ServerHandler.GetUserByID_CallBack() {
            @Override
            public void onSuccess(User user) {
                serverHandler.LoadImageFromURL(user.getAvatar(), img_ClickedUserAvatar);
                lbl_ClickedUserName.setText(user.getUsername());
            }

            @Override
            public void onFail(String message) {

            }
        });
        serverHandler.getNewFeed_ByID(post_ID, new ServerHandler.getNewFeed_ByID_CallBack() {
            @Override
            public void onSuccess(NewFeeds newFeed) {
                lbl_Post_Content.setText(newFeed.getContent());
                lbl_Post_CommentCount.setText(newFeed.getComment_count() +"  ");
                lbl_Post_LikeCount.setText(" " + newFeed.getLike_count());
                serverHandler.getLike_ByUserId_And_NewsFeedId(post_ID, user_ID, new ServerHandler.getLike_ByUserId_And_NewsFeedId_CallBack() {
                    @Override
                    public void onSuccess(Like like) {
                        btn_Post_Like.setTextColor(Color.RED);
                        btn_Post_Like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_liked_icon_24,0 ,0 ,0);
                        if(Integer.parseInt(newFeed.getLike_count()) == 1)
                        {
                            lbl_Post_LikeCount.setText("  You");
                        }
                        else
                        {
                            lbl_Post_LikeCount.setText("  You and " + newFeed.getLike_count() + " others");
                        }
                    }
                    @Override
                    public void onFailed(String message) {

                    }
                });
                btn_Post_Like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        serverHandler.like_Acion(post_ID, user_ID, new ServerHandler.LikeAction_CallBack() {
                            @Override
                            public void onSuccess(JSON_MESSAGE jsonMessage) {
                                JSON_MESSAGE message = jsonMessage;
                                if(message.getMessage().equals("0") == false)
                                {
                                    btn_Post_Like.setTextColor(Color.RED);
                                    btn_Post_Like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.btn_liked_icon_24,0 ,0 ,0);
                                    Toast.makeText(PostActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                                    if(Integer.parseInt(newFeed.getLike_count()) == 0)
                                    {
                                        lbl_Post_LikeCount.setText("  You");
                                    }
                                    else
                                    {
                                        lbl_Post_LikeCount.setText("  You and " + newFeed.getLike_count() + " others");
                                    }
                                }
                            }

                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onFailed(String message) {

            }
        });



        if(is_comment.equals("true"))
        {
            txt_Comment.requestFocus();
            // Show the soft keyboard
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(txt_Comment, InputMethodManager.SHOW_IMPLICIT);
        }
        btn_CreateComment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String content = txt_Comment.getText().toString();
                serverHandler.createComment(user_ID, post_ID, content, new ServerHandler.createComment_CallBack() {
                    @Override
                    public void onSuccess(JSON_MESSAGE jsonMessage) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        txt_Comment.setText("");
                        Toast.makeText(PostActivity.this, jsonMessage.getMessage(), Toast.LENGTH_LONG).show();
                        serverHandler.getComment_ByNFID(post_ID, new ServerHandler.getComment_ByNFID_CallBack() {
                            @Override
                            public void onSuccess(List<Comment> commentList) {
                                try {
                                    lbl_Post_CommentCount.setText(String.valueOf(commentList.size()) + "  ");
                                }
                                catch (Exception e)
                                {
                                    Log.d("Error", e.getMessage());
                                }

                                CommentCustomBase commentCustomBase = new CommentCustomBase(getApplicationContext(), commentList);
                                listView_Comment.setAdapter(commentCustomBase);
                            }

                            @Override
                            public void onFailed(String message) {

                            }
                        });
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }
        });
        btn_Post_Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_Comment.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(txt_Comment, InputMethodManager.SHOW_IMPLICIT);

            }
        });
    }
}