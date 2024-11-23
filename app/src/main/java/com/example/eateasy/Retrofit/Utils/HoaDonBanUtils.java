package com.example.eateasy.Retrofit.Utils;


import com.example.eateasy.Retrofit.Interface.HoaDonBanInterface;

import com.example.eateasy.Retrofit.RetrofitClient;

public class HoaDonBanUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static HoaDonBanInterface getHoaDOnBanService(){
        return RetrofitClient.getClient(BASE_URL).create(HoaDonBanInterface.class);
    }
}
