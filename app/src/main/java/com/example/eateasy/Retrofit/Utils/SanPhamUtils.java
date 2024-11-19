package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class SanPhamUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static SanPhamInterface getProdutsService(){
        return RetrofitClient.getClient(BASE_URL).create(SanPhamInterface.class);
    }

}
