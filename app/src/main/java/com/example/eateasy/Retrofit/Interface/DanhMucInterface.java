package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.DanhMuc;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DanhMucInterface {
    @GET("api/danhmuc/getall")
    Call<ArrayList<DanhMuc>> getAllDanhMuc();
}
