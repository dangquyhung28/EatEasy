package com.example.eateasy.Retrofit.Interface;

import com.example.eateasy.Model.NhaCungCap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NccInterface {
    @GET("api/ncc/getall")
    Call<ArrayList<NhaCungCap>> getALLNcc();
}
