package com.example.eateasy.Retrofit;

public class UserUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static UserInterface getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserInterface.class);
    }
}
