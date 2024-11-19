package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.NhanVienInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class NhanVienUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static NhanVienInterface getNhanVienService(){
        return RetrofitClient.getClient(BASE_URL).create(NhanVienInterface.class);
    }
}
