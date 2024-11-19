package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.DanhMucInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class DanhMucUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static DanhMucInterface getCategoryService(){
        return RetrofitClient.getClient(BASE_URL).create(DanhMucInterface.class);
    }
}
