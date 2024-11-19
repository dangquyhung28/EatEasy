package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class NhaCungCap {
    @SerializedName("MaNCC")
    private  String maNcc;

    @SerializedName("TenNCC")
    private String tenNcc;

    public NhaCungCap(String maNcc, String tenNcc) {
        this.maNcc = maNcc;
        this.tenNcc = tenNcc;
    }

    public String getMaNcc() {
        return maNcc;
    }

    public void setMaNcc(String maNcc) {
        this.maNcc = maNcc;
    }

    public String getTenNcc() {
        return tenNcc;
    }

    public void setTenNcc(String tenNcc) {
        this.tenNcc = tenNcc;
    }

    @Override
    public String toString() {
        return tenNcc;
    }
}
