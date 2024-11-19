package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.Interface.NccInterface;
import com.example.eateasy.Retrofit.RetrofitClient;

public class NccUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static NccInterface getNccService(){
        return RetrofitClient.getClient(BASE_URL).create(NccInterface.class);
    }

}
