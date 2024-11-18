package com.example.eateasy.Retrofit;

public class ProductsUtils {
    public static final String BASE_URL = RetrofitClient.BASE_URL;
    public static ProductsInterface getProdutsService(){
        return RetrofitClient.getClient(BASE_URL).create(ProductsInterface.class);
    }

}
