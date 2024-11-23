package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DanhMucInterface {
    @GET("api/danhmuc/getall")
    Call<ArrayList<DanhMuc>> getAllDanhMuc();


}
