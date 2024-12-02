package com.example.eateasy.Retrofit.Utils;


import com.example.eateasy.Retrofit.Interface.DonHangInterface;

import com.example.eateasy.Retrofit.RetrofitClient;

public class DonHangUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static DonHangInterface getHoaDOnBanService(){
        return RetrofitClient.getClient(BASE_URL).create(DonHangInterface.class);
    }
}
