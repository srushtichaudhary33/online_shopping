package com.example.onlineshopping;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("signup.php")
    Call<GetSignupData> doSignupData (
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<GetLoginData> doLoginData(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<GetSignupData> doUpdateProfileData(
            @Field("userid") String userid,
            @Field("name") String name,
            @Field("email") String email,
            @Field("contact") String contact,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("delete_profile.php")
    Call<GetSignupData> doDeleteData(@Field("userid") String userid);

    @Multipart
    @POST("updateProfileImage.php")
    Call<UpdateProfileImageData> updateProfileImageData(
            @Part("userid") RequestBody userid,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("contact") RequestBody contact,
            @Part("password") RequestBody password,
            @Part MultipartBody.Part image);

}