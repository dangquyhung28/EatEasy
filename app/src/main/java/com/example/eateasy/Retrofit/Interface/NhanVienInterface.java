package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.NhanVien;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface NhanVienInterface {
    @GET("api/nhanvien/getall")
    Call<ArrayList<NhanVien>> getAllNv();
}
