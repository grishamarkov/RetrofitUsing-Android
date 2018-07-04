package com.n2me.androidtv.common.rest;


import com.n2me.androidtv.common.rest.model.Categories;
import com.n2me.androidtv.common.rest.model.Media;
import com.n2me.androidtv.common.rest.model.User;
import com.n2me.androidtv.common.rest.model.Users;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface N2meApi
{
    @FormUrlEncoded
    @POST("https://static.n2me.tv/api/v1/session")
    public Call<Users> session(@Field("email") String email,
                              @Field("password") String password);

    @GET("user")
    public Call<Users> user(@Query("user_email") String userEmail,
                            @Query("user_token") String userToken);

    @GET("categories")
    public Call<Categories> categoriesAll();

    @GET("categories")
    public Call<Categories> categories(@Query("user_email") String userEmail,
                                       @Query("user_token") String userToken);

    @GET("categories/{categoryId}")
    public Call<Categories> categoriesById(@Path("categoryId") int categoryId,
                                       @Query("user_email") String userEmail,
                                       @Query("user_token") String userToken);

    @GET("categories/{categoryId}/media/{mediaId}")
    public Call<Media> media(@Path("categoryId") int categoryId,
                             @Path("mediaId") int mediaId,
                             @Query("user_email") String userEmail,
                             @Query("user_token") String userToken);

    @GET("media/{id}")
    public Call<Media> mediaById(@Path("id") int mediaID,
                                      @Query("user_email") String userEmail,
                                      @Query("user_token") String userToken);

    @GET("categories/{categoryId}/media")
    public Call<Media> mediaByCategoryId(@Path("categoryId") int categoryId,
                                         @Query("page") int page,
                                         @Query("per_page") int perPage,
                                         @Query("user_email") String userEmail,
                                         @Query("user_token") String userToken);

}
