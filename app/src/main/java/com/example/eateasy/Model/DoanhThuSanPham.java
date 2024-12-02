package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class DoanhThuSanPham {

    @SerializedName("TenSP")
    private String TenSP;

    @SerializedName("SoLuongBan")
    private int SoLuongBan;

    @SerializedName("DoanhThu")
    private double DoanhThu;

    public DoanhThuSanPham(String tenSP, int soLuongBan, double doanhThu) {
        TenSP = tenSP;
        SoLuongBan = soLuongBan;
        DoanhThu = doanhThu;
    }

    // Getters and Setters
    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getSoLuongBan() {
        return SoLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        SoLuongBan = soLuongBan;
    }

    public double getDoanhThu() {
        return DoanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        DoanhThu = doanhThu;
    }
}
