package com.example.eateasy.Retrofit;

public class ProductsUtils {
    public static final String BASE_URL = "http://192.168.30.106:5000/";
    public static ProductsInterface getProdutsService(){
        return RetrofitClient.getClient(BASE_URL).create(ProductsInterface.class);
    }
}
