package com.example.tokuverseproject.ServerAPI;

import static android.app.ProgressDialog.show;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

import com.example.tokuverseproject.Model.Comment;
import com.example.tokuverseproject.Model.FightDetails;
import com.example.tokuverseproject.Model.FightHistory;
import com.example.tokuverseproject.Model.Hero;
import com.example.tokuverseproject.Model.HeroDetails;
import com.example.tokuverseproject.Model.JSON_MESSAGE;
import com.example.tokuverseproject.Model.Like;
import com.example.tokuverseproject.Model.NewFeeds;
import com.example.tokuverseproject.Model.Order;
import com.example.tokuverseproject.Model.OrderDetails;
import com.example.tokuverseproject.Model.Product;
import com.example.tokuverseproject.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerHandler {

    String Ip = //"10.40.171.72";
            "192.168.1.23";
    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://tokusever491.online/phpFiles/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    API Api = retrofit.create(API.class);

    public class Message {
        @SerializedName("message")
        String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public interface CallBack {
        void getHero_ByID_Success(Hero hero);

        void onFailed(String message);
    }

    public void LoadImageFromURL(String url, ImageView imageView) {
        try {
            Picasso.get().load(url).into(imageView);
        } catch (Exception e) {
            Log.d("Failed To Load Image From URL", e.getMessage());
        }
    }
    public interface getImage_FromURL_CallBack
    {
        void onSuccess();
        void onFailed(String message);
    }
    public void getImage_FromURL(String url, ImageView imageView, getImage_FromURL_CallBack callBack)
    {
        try {
            Picasso.get().load(url).into(imageView);
            callBack.onSuccess();
        } catch (Exception e) {
            Log.d("Failed To Load Image From URL", e.getMessage());
            callBack.onFailed(e.getMessage());
        }
    }

    public interface getUser_CallBack
    {
        void onSuccess(List<User> userList);
        void onFailed(String message);
    }

    public void getUser(getUser_CallBack callBack) {
        Call<List<User>> call = Api.getUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface getHero_CallBack {
        void onSuccess(List<Hero> heroList);

        void onFail(String message);
    }

    public void getHero(getHero_CallBack callBack) {
        Call<List<Hero>> call = Api.getHero();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> heroList = response.body();
                try {
                    callBack.onSuccess(heroList);
                } catch (Exception e) {
                    callBack.onFail(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Log.d("failed", t.getMessage());
            }
        });
    }

    public interface LoginCallback {
        void onSuccess(User user);

        void onFail(String message);
    }

    public void LogIn(String username, String password, LoginCallback callback) {
        Call<List<User>> call = Api.logIn(username, password);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> userList = response.body();
                try {
                    callback.onSuccess(userList.get(0));
                } catch (Exception e) {
                    callback.onFail("Log in Failed");
                    Log.d("failed", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("failed", t.getMessage());
                callback.onFail(t.getMessage());
            }
        });
    }

    public interface GetUserByID_CallBack {
        void onSuccess(User user);

        void onFail(String message);
    }

    public void GetUserByID(String id, GetUserByID_CallBack callBack) {
        Log.d("Curent user id", id);
        Call<List<User>> call = Api.getUser_ByID(id);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> userList = response.body();
                try {
                    callBack.onSuccess(userList.get(0));
                } catch (Exception e) {
                    Log.d("Get User by Id Failed", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("failed", t.getMessage());
            }
        });
    }

    public interface getHeroDetails_ByID_callBack {
        void onSuccess(HeroDetails heroDetails);
    }

    public void getHeroDetails_ByUserID(String id, getHeroDetails_ByID_callBack callBack) {
        Call<List<HeroDetails>> call = Api.getHeroDetails_ByUserID(id);
        call.enqueue(new Callback<List<HeroDetails>>() {
            @Override
            public void onResponse(Call<List<HeroDetails>> call, Response<List<HeroDetails>> response) {
                List<HeroDetails> heroDetails = response.body();
                try {
                    callBack.onSuccess(heroDetails.get(0));
                } catch (Exception e) {
                    Log.d("Failed at getHeroDetails_ByID", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<HeroDetails>> call, Throwable t) {

            }
        });
    }


    public void getHero_ByID(String id, CallBack callBack) {
        Call<List<Hero>> call = Api.getHero_ByID(id);
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(Call<List<Hero>> call, Response<List<Hero>> response) {
                List<Hero> hero = response.body();
                try {
                    callBack.getHero_ByID_Success(hero.get(0));
                } catch (Exception e) {
                    Log.d("Failed at getHero_ByID", e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<List<Hero>> call, Throwable t) {
                Log.d("Failed at getHero_ByID", t.getMessage());
            }
        });
    }

    public void getHeroDetails_ByUserID(String user_id) {
        Call<List<HeroDetails>> call = Api.getHero_ByUserID(user_id);
        call.enqueue(new Callback<List<HeroDetails>>() {
            @Override
            public void onResponse(Call<List<HeroDetails>> call, Response<List<HeroDetails>> response) {
                List<HeroDetails> heroDetails = response.body();
                String hero_details_id = heroDetails.get(0).getId();
                Log.d("user_ID", hero_details_id);
                addHeroToUser(user_id, hero_details_id);
            }

            @Override
            public void onFailure(Call<List<HeroDetails>> call, Throwable t) {

            }
        });
    }

    public void addHeroToUser(String id, String hero_details_id) {
        Call<Void> call = Api.addHeroToUser(id, hero_details_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("failed at addHeroToUser", t.getMessage());
            }
        });
    }

    public interface signUp_CallBack {
        void onSuccess();

        void onFailed(String message);
    }

    public void signUp(User user, signUp_CallBack callBack) {
        Call<Void> call = Api.signUpAction(user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone_number());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("failed", t.getMessage());
            }
        });
    }

    public interface selectHero_CallBack {
        void onSuccess();

        void onFail(String message);
    }

    public void selectHero(String user_id, String hero_id, selectHero_CallBack callBack) {
        Call<Void> call = Api.selecHero(user_id, hero_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getHeroDetails_ByUserID(user_id);
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFail(t.getMessage());
            }
        });
    }

    public interface createPost_CallBack {
        void onSuccess();

        void onFailed(String message);
    }

    public void createPost(String user_id, String content, String current_time, createPost_CallBack callBack) {
        Call<Void> call = Api.createPostAction(user_id, content, current_time);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }


    public interface GetNewFeeds_CallBack {
        void onSuccess(List<NewFeeds> newFeedsList);

        void onFailed(String message);
    }

    public void getNewFeedAction(GetNewFeeds_CallBack callBack) {
        Call<List<NewFeeds>> call = Api.getNewFeeds();
        call.enqueue(new Callback<List<NewFeeds>>() {
            @Override
            public void onResponse(Call<List<NewFeeds>> call, Response<List<NewFeeds>> response) {
                List<NewFeeds> newFeedsList = response.body();
                try {
                    for (int i = 0; i < newFeedsList.size(); i++) {
                        Log.d("Get New Feed Success", newFeedsList.get(i).getUser_name());
                    }
                    callBack.onSuccess(newFeedsList);
                } catch (Exception e) {
                    Log.d("Get New Feed Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<NewFeeds>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface ChangeAttribute_CallBack {
        void onSuccess(JSON_MESSAGE jsonMessage);

        void onFailed(String message);
    }

    public void changeAttribute_Action(String id, String attack_point, String defense_point, String health_point, String attribute_point, ChangeAttribute_CallBack callBack) {
        Call<JSON_MESSAGE> call = Api.changeAttrribute(id, attack_point, defense_point, health_point, attribute_point);
        call.enqueue(new Callback<JSON_MESSAGE>() {
            @Override
            public void onResponse(Call<JSON_MESSAGE> call, Response<JSON_MESSAGE> response) {
                JSON_MESSAGE jsonMessage = response.body();
                try {
                    callBack.onSuccess(jsonMessage);
                } catch (Exception e) {
                    Log.d("Change Attribute Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JSON_MESSAGE> call, Throwable t) {

            }
        });
    }

    public interface LikeAction_CallBack {
        void onSuccess(JSON_MESSAGE jsonMessage);

        void onFailed(String message);
    }

    public void like_Acion(String news_feed_id, String user_id, LikeAction_CallBack callBack) {
        Call<JSON_MESSAGE> call = Api.likeAction(news_feed_id, user_id);
        call.enqueue(new Callback<JSON_MESSAGE>() {
            @Override
            public void onResponse(Call<JSON_MESSAGE> call, Response<JSON_MESSAGE> response) {
                JSON_MESSAGE jsonMessage = response.body();
                try {
                    callBack.onSuccess(jsonMessage);
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<JSON_MESSAGE> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface getLike_ByUserId_And_NewsFeedId_CallBack {
        void onSuccess(Like like);

        void onFailed(String message);
    }

    public void getLike_ByUserId_And_NewsFeedId(String news_feed_id, String user_id, getLike_ByUserId_And_NewsFeedId_CallBack callBack) {
        Call<List<Like>> call = Api.getLike_ByUserID_and_NewsFeedID(news_feed_id, user_id);
        call.enqueue(new Callback<List<Like>>() {
            @Override
            public void onResponse(Call<List<Like>> call, Response<List<Like>> response) {
                Like like = new Like();
                if (response.body().size() > 0) {
                    like = response.body().get(0);
                    callBack.onSuccess(like);
                }

            }

            @Override
            public void onFailure(Call<List<Like>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface getNewFeed_ByID_CallBack {
        void onSuccess(NewFeeds newFeed);

        void onFailed(String message);
    }

    public void getNewFeed_ByID(String id, getNewFeed_ByID_CallBack callBack) {
        Call<List<NewFeeds>> call = Api.getNewsFeed_ByID(id);
        call.enqueue(new Callback<List<NewFeeds>>() {
            @Override
            public void onResponse(Call<List<NewFeeds>> call, Response<List<NewFeeds>> response) {
                NewFeeds newFeeds = response.body().get(0);
                callBack.onSuccess(newFeeds);
            }

            @Override
            public void onFailure(Call<List<NewFeeds>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface createComment_CallBack{
        void onSuccess(JSON_MESSAGE jsonMessage);

        void onFailed(String message);
    }

    public void createComment(String user_id, String news_feed_id, String content, createComment_CallBack callBack)
    {
        Call<JSON_MESSAGE> call = Api.commentAction(user_id, news_feed_id, content);
        call.enqueue(new Callback<JSON_MESSAGE>() {
            @Override
            public void onResponse(Call<JSON_MESSAGE> call, Response<JSON_MESSAGE> response) {
                JSON_MESSAGE json_message = response.body();
                callBack.onSuccess(json_message);
            }

            @Override
            public void onFailure(Call<JSON_MESSAGE> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface getComment_ByNFID_CallBack
    {
        void onSuccess(List<Comment> commentList);

        void onFailed(String message);
    }

    public void getComment_ByNFID(String news_feed_id, getComment_ByNFID_CallBack callBack)
    {
        Call<List<Comment>> call = Api.getComment_ByNewsFeedID(news_feed_id);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                try
                {
                    List<Comment> commentList = response.body();
                    callBack.onSuccess(commentList);
                }
                catch (Exception e) {
                    Log.d("Get Comment Failed", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
                Log.d("Get Comment Failed", t.getMessage());
            }
        });
    }

    public interface updateUserCoins_CallBack
    {
        void onSuccess();

        void onFailed(String message);
    }

    public void updateUserCoins(String id, String coins, updateUserCoins_CallBack callBack)
    {
        Call<Void> call = Api.updateCoinsAction(id, coins);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                try
                {
                    callBack.onSuccess();
                }
                catch (Exception e) {
                    callBack.onFailed(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface getProduct_CallBack
    {
        void onSuccess(List<Product> productList);
        void onFailed(String message);
    }

    public void getProduct_Action(getProduct_CallBack callBack)
    {
        Call<List<Product>> call = Api.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> productList = response.body();
                try {
                    callBack.onSuccess(productList);
                } catch (Exception e) {
                    callBack.onFailed(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface createOrder_CallBack
    {
        void onSuccess(Order order);
        void onFailed(String message);
    }

    public void createOrderAction(String user_id, String address, String total_price, createOrder_CallBack callBack)
    {
        Call<Order> call = Api.createOrder(user_id, address, total_price);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                callBack.onSuccess(order);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }

    public interface createOrderDetails_CallBack
    {
        void onSuccess();
        void onFailed(String message);
    }

    public void createOrderDetails_Action(String order_id, String product_id, String quantity, String price, createOrderDetails_CallBack callBack)
    {
        Call<Void> call = Api.createOrderDetails(order_id, product_id, quantity, price);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface getOrder_ByUserId_CallBack
    {
        void onSuccess(List<Order> orderList);
        void onFailed(String message);
    }
    public void getOrder_ByUserId(String user_id, getOrder_ByUserId_CallBack callBack)
    {
        Call<List<Order>> call = Api.getOrder_ByUserId(user_id);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface getOrderDetails_ByOrderId_CallBack
    {
        void onSuccess(List<OrderDetails> orderDetailsList);
        void onFailed(String message);
    }
    public void getOrderDetails_ByOrderId(String order_id, getOrderDetails_ByOrderId_CallBack callBack)
    {
        Call<List<OrderDetails>> call = Api.getOrderDetails_ByOrderId(order_id);
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface getProduct_ById_CallBack
    {
        void onSuccess(Product product);
        void onFailed(String message);
    }
    public void getProduct_ById(String id, getProduct_ById_CallBack callBack)
    {
        Call<List<Product>> call = Api.getProduct_ById(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                callBack.onSuccess(response.body().get(0));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface createFightHistory_CallBack
    {
        void onSuccess(FightHistory fightHistory);
        void onFailed(String message);
    }
    public void createFightHistory(String user_id, String fight_user_id, String rewards, String status, createFightHistory_CallBack callBack)
    {
        Call<FightHistory> call = Api.createFightHistory(user_id, fight_user_id, rewards, status);
        call.enqueue(new Callback<FightHistory>() {
            @Override
            public void onResponse(Call<FightHistory> call, Response<FightHistory> response) {
                FightHistory fightHistory = response.body();
                callBack.onSuccess(fightHistory);
            }

            @Override
            public void onFailure(Call<FightHistory> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface createFightDetails_CallBack
    {
        void onSuccess();
        void onFailed(String message);
    }
    public void createFightDetails(String fight_id, String turn, String damage, String user_currentHP, String fight_user_currentHP, createFightDetails_CallBack callBack)
    {
        Call<Void> call = Api.createFightDetails(fight_id, turn, damage, user_currentHP, fight_user_currentHP);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface getFightHistory_ByUserId_CallBack
    {
        void onSuccess(List<FightHistory> fightHistoryList);
        void onFailed(String message);
    }
    public void getFightHistory_ByUserId(String user_id, getFightHistory_ByUserId_CallBack callBack)
    {
        Call<List<FightHistory>> call = Api.getFightHistory_ByUserId(user_id);
        call.enqueue(new Callback<List<FightHistory>>() {
            @Override
            public void onResponse(Call<List<FightHistory>> call, Response<List<FightHistory>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<FightHistory>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public interface getFightDetails_ByFightID_CallBack
    {
        void onSuccess(List<FightDetails> fightDetailsList);
        void onFailed(String message);
    }
    public void getFightDetails_ByFightID(String fight_id, getFightDetails_ByFightID_CallBack callBack)
    {
        Call<List<FightDetails>> call = Api.getFightDetails_ByFightId(fight_id);
        call.enqueue(new Callback<List<FightDetails>>() {
            @Override
            public void onResponse(Call<List<FightDetails>> call, Response<List<FightDetails>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<FightDetails>> call, Throwable t) {
                callBack.onFailed(t.getMessage());
            }
        });
    }
    public void Test(String user_id, String news_feed_id, String content) {
        Call<JSON_MESSAGE> call = Api.commentAction(user_id, news_feed_id, content);
        call.enqueue(new Callback<JSON_MESSAGE>() {
            @Override
            public void onResponse(Call<JSON_MESSAGE> call, Response<JSON_MESSAGE> response) {
                Log.d("Test", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<JSON_MESSAGE> call, Throwable t) {

            }
        });
    }



}

