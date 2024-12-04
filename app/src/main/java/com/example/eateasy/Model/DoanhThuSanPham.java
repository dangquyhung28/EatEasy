package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class DoanhThuSanPham {

    @SerializedName("TenSP")
    private String TenSP;

    @SerializedName("Ngay")
    private String Ngay;

    @SerializedName("SoLuongBan")
    private int SoLuongBan;

    @SerializedName("DoanhThu")
    private double DoanhThu;

    @SerializedName("LoiNhuan")
    private double LoiNhuan;

    public DoanhThuSanPham(String tenSP, String ngay, int soLuongBan, double doanhThu, double loiNhuan) {
        TenSP = tenSP;
        Ngay = ngay;
        SoLuongBan = soLuongBan;
        DoanhThu = doanhThu;
        LoiNhuan = loiNhuan;
    }


    // Getters and Setters

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public double getLoiNhuan() {
        return LoiNhuan;
    }

    public void setLoiNhuan(double loiNhuan) {
        LoiNhuan = loiNhuan;
    }

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
