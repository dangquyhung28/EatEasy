package com.example.eateasy.Retrofit;

public class CategoryUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static CategoryInterface getCategoryService(){
        return RetrofitClient.getClient(BASE_URL).create(CategoryInterface.class);
    }
}
