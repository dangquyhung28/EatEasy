package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.DonHang;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DonHangInterface {
    @GET("/api/get-don-hang")
    Call<ArrayList<DonHang>> getAllHoaDonBan();

    @GET("/donhang/xuly")
    Call<ArrayList<DonHang>> getDonXyLy();
}
