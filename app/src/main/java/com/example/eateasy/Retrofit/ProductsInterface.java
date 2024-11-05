package com.example.eateasy.Retrofit;

import com.example.eateasy.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductsInterface {
    @GET("api/sanpham/getall")
    Call<ArrayList<SanPham>> getAllSanPham();
}
