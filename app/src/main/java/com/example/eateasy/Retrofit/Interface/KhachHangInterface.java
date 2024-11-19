package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.KhachHang;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KhachHangInterface {
    @GET("api/khachhang/getall")
    Call<ArrayList<KhachHang>> getAllKhachHang();
}
