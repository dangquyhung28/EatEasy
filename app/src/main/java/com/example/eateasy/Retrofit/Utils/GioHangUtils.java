package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class GioHangUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static GioHangInterface getGioHangService(){
        return RetrofitClient.getClient(BASE_URL).create(GioHangInterface.class);
    }
}
