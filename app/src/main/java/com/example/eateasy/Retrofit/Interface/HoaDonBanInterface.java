package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.HoaDonBan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HoaDonBanInterface {
    @GET("/api/get-don-hang")
    Call<ArrayList<HoaDonBan>> getAllHoaDonBan();
}
