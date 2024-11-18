package com.example.eateasy.Retrofit;

import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryInterface {
    @GET("api/danhmuc/getall")
    Call<ArrayList<DanhMuc>> getAllDanhMuc();
}
