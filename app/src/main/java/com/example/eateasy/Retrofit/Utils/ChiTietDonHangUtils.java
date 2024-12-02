package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.ChiTietDonHangInnterface;
import com.example.eateasy.Retrofit.Interface.DanhMucInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class ChiTietDonHangUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static ChiTietDonHangInnterface getChiTietDonHangService(){
        return RetrofitClient.getClient(BASE_URL).create(ChiTietDonHangInnterface.class);
    }
}
