package com.example.eateasy.Retrofit;

import com.example.eateasy.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ProductsInterface {
    @GET("api/sanpham/getall")
    Call<ArrayList<SanPham>> getAllSanPham();

    @POST("api/sanpham/add")
    Call<ResponseBody> addProduct(@Body SanPham sanPham);

    @PUT("api/sanpham/update")
    Call<ResponseBody> updateProduct(@Body SanPham sanPham);

    @DELETE("api/sanpham/delete")
    Call<ResponseBody> deleteProduct(@Body SanPham sanPham);

}
