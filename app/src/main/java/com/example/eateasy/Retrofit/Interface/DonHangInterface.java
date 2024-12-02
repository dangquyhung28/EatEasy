package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.DoanhThuSanPham;
import com.example.eateasy.Model.DonHang;
import com.example.eateasy.Model.SanPham;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DonHangInterface {
    @GET("/api/get-don-hang")
    Call<ArrayList<DonHang>> getAllHoaDonBan();

    @GET("/donhang/xuly")
    Call<ArrayList<DonHang>> getDonXyLy();

    @GET("/api/get-don-hang/khachhang")
    Call<ArrayList<DonHang>> getDonHang(@Query("MaKH") String maKH);

    @GET("/api/doanhthu/sanpham/homnay")
    Call<ArrayList<DoanhThuSanPham>> getDoanhThuSanPhamHomNay();
}
