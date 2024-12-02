package com.example.eateasy.Retrofit.Interface;


import com.example.eateasy.Model.ChiTietDonHang;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ChiTietDonHangInnterface {
    @GET("/api/donhang/chitiet")
    Call<ChiTietDonHang> getChiTietDonHang(@Query("MaDonHang") String maDonHang);

    @PUT("/api/donhang/nhan")
    Call<ResponseBody> nhanDon(@Body JsonObject requestBody);

    @PUT("/api/donhang/xulydon")
    Call<ResponseBody> xulydon(@Body JsonObject requestBody);
}
