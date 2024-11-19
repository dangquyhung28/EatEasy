package com.example.eateasy.Retrofit.Utils;

import com.example.eateasy.Retrofit.RetrofitClient;
import com.example.eateasy.Retrofit.Interface.UserInterface;

public class UserUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static UserInterface getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserInterface.class);
    }
}
