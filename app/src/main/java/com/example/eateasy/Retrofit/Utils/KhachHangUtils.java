package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class KhachHangUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static KhachHangInterface getKhachHangService(){
        return RetrofitClient.getClient(BASE_URL).create(KhachHangInterface.class);
    }
}
