package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.KhachHang;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface KhachHangInterface {
    @GET("api/khachhang/getall")
    Call<ArrayList<KhachHang>> getAllKhachHang();

    @PUT("/api/khachhang/update")
    Call<ResponseBody> capNhatKhachHang(@Body JsonObject requestBody);
}
