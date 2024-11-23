package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.HoaDonNhapInterface;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class HoaDonNhapUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static HoaDonNhapInterface getHoaDonNhap(){
        return RetrofitClient.getClient(BASE_URL).create(HoaDonNhapInterface.class);
    }
}
