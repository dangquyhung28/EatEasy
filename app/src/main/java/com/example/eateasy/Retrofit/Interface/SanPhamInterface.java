package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.SanPham;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SanPhamInterface {
    @GET("api/sanpham/getall")
    Call<ArrayList<SanPham>> getAllSanPham();

    @POST("api/sanpham/add")
    Call<ResponseBody> addProduct(@Body SanPham sanPham);

    @PUT("/api/update-san-pham")
    Call<ResponseBody> updateProduct(@Body SanPham sanPham);

    @DELETE("api/sanpham/delete/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") String id);

    @GET("/api/sanpham/danhmuc")
    Call<ArrayList<SanPham>> getSanPhamByMaDanhMuc(@Query("MaDanhMuc") String maDanhMuc);


}
