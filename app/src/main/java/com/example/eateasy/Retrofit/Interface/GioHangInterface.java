package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.GioHang;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.Model.SanPham;
import com.google.gson.JsonObject;

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

public interface GioHangInterface {
    @POST("/api/giohang/add")
    Call<ResponseBody> addGioHang(@Body JsonObject requestBody);

    @GET("/api/giohang")
    Call<ArrayList<GioHang>> getSanPhamByKhanhHang(@Query("MaKH") String maKH);

    @DELETE("giohang/delete/{MaKH}/{MaSP}")
    Call<Void> deleteProduct(
            @Path("MaKH") String MaKH,
            @Path("MaSP") String MaSP
    );
    @PUT("/api/giohang/update")
    Call<ResponseBody> capNhatSoLuongSanPham(@Body JsonObject requestBody);


}
