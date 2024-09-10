package com.example.tokuverseproject.ServerAPI;

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

import java.util.List;


import kotlin.jvm.JvmMultifileClass;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @GET("getUser.php")
    Call<List<User>> getUser();

    @GET("getHero.php")
    Call<List<Hero>> getHero();

    @GET("getNewFeeds.php")
    Call<List<NewFeeds>> getNewFeeds();

    @GET("getProduct.php")
    Call<List<Product>> getProduct();

    @FormUrlEncoded
    @POST("login.php")
    Call<List<User>> logIn(@Field("username") String username,
                           @Field("pass") String password);

    @FormUrlEncoded
    @POST("getUser_ByID.php")
    Call<List<User>> getUser_ByID(@Field("id") String id);

    @FormUrlEncoded
    @POST("getHero_ByID.php")
    Call<List<Hero>> getHero_ByID(@Field("id") String id);

    @FormUrlEncoded
    @POST("getHeroDetails_ByUserID.php")
    Call<List<HeroDetails>> getHeroDetails_ByUserID(@Field("user_id") String id);

    @FormUrlEncoded
    @POST("getHeroDetails_ByUserID.php")
    Call<List<HeroDetails>> getHero_ByUserID(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("signUp.php")
    Call<Void> signUpAction(
            @Field("username") String username,
            @Field("pass") String password,
            @Field("email") String email,
            @Field("phone_number") String phoneNumber
    );

    @FormUrlEncoded
    @POST("createPost.php")
    Call<Void> createPostAction(
            @Field("user_id") String user_id,
            @Field("content") String content,
            @Field("date_post") String date_post
    );
    @FormUrlEncoded
    @POST("selectHero.php")
    Call<Void> selecHero(
            @Field("user_id") String user_id,
            @Field("hero_id") String hero_id);

    @FormUrlEncoded
    @POST("addHeroToUser.php")
    Call<Void> addHeroToUser(
            @Field("id") String id,
            @Field("hero_details_id") String hero_details_id);

    @FormUrlEncoded
    @POST("changeAttribute.php")
    Call<JSON_MESSAGE> changeAttrribute(
            @Field("id") String id,
            @Field("attack_point") String attack_point,
            @Field("defense_point") String defense_point,
            @Field("health_point") String health_point,
            @Field("attribute_point") String attribute_point);
    @FormUrlEncoded
    @POST("likeAction.php")
    Call<JSON_MESSAGE> likeAction(
            @Field("news_feed_id") String news_feed_id,
            @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("createComment.php")
    Call<JSON_MESSAGE> commentAction(
            @Field("user_id") String user_id,
            @Field("news_feed_id") String news_feed_id,
            @Field("content") String content);
    @FormUrlEncoded
    @POST("getLike_ByUserID-NewsFeedId.php")
    Call<List<Like>> getLike_ByUserID_and_NewsFeedID(
            @Field("news_feed_id") String news_feed_id,
            @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getNewsFeed_ByID.php")
    Call<List<NewFeeds>> getNewsFeed_ByID(
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("getComment_ByNewsFeedID.php")
    Call<List<Comment>> getComment_ByNewsFeedID(
            @Field("news_feed_id") String news_feed_id
    );
    @FormUrlEncoded
    @POST("updateUserCoins.php")
    Call<Void> updateCoinsAction(
            @Field("id") String id,
            @Field("coins") String coins
    );
    @FormUrlEncoded
    @POST("createOrder.php")
    Call<Order> createOrder(
            @Field("user_id") String user_id,
            @Field("address") String address,
            @Field("total_price") String total_price
    );
    @FormUrlEncoded
    @POST("createOrderDetails.php")
    Call<Void> createOrderDetails(
            @Field("order_id") String order_id,
            @Field("product_id") String product_id,
            @Field("quantity") String quantity,
            @Field("price") String price
    );
    @FormUrlEncoded
    @POST("getOrder_ByUserID.php")
    Call<List<Order>> getOrder_ByUserId(
            @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("getOrderDetails_ByOrderId.php")
    Call<List<OrderDetails>> getOrderDetails_ByOrderId(
            @Field("order_id") String order_id
    );
    @FormUrlEncoded
    @POST("getProduct_ById.php")
    Call<List<Product>> getProduct_ById(
            @Field("id") String id
    );
    @FormUrlEncoded
    @POST("createFightHistory.php")
    Call<FightHistory> createFightHistory(
            @Field("user_id") String user_id,
            @Field("fight_user_id") String fight_user_id,
            @Field("rewards") String rewards,
            @Field("status") String status
    );
    @FormUrlEncoded
    @POST("createFightDetails.php")
    Call<Void> createFightDetails(
            @Field("fight_id") String fight_id,
            @Field("turn") String turn,
            @Field("damage") String damage,
            @Field("user_currentHP") String user_currentHP,
            @Field("fight_user_currentHP") String fight_user_currentHP
    );
    @FormUrlEncoded
    @POST("getFighHistory_ByUserId.php")
    Call<List<FightHistory>> getFightHistory_ByUserId(
            @Field("user_id") String user_id
    );
    @FormUrlEncoded
    @POST("getFightDetails_ByFightId.php")
    Call<List<FightDetails>> getFightDetails_ByFightId(
            @Field("fight_id") String fight_id
    );
}
