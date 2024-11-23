package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.HoaDonNhap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HoaDonNhapInterface {
    @GET("/api/get-hoa-don-nhap")
    Call<ArrayList<HoaDonNhap>> getAllHoaDonNhap();
}
