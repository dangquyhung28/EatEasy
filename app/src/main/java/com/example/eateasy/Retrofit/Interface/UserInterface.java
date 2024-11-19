package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserInterface {
    @GET("api/user/getall")
    Call<ArrayList<User>> getAllUser();

    @POST("api/user/add")
    Call<ResponseBody> addUser(@Body User user);
}
