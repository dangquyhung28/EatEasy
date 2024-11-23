package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class HoaDonNhap {
    @SerializedName("MaHDN")
    private String MaHDN;

    @SerializedName("MaNCC")
    private String MaNCC;

    @SerializedName("MaNV")
    private String MaNV;

    @SerializedName("NgayLap")
    private String NgayLap;

    @SerializedName("TongTien")
    private double TongTien;

    // Getter và Setter
    public String getMaHDN() {
        return MaHDN;
    }

    public void setMaHDN(String maHDN) {
        MaHDN = maHDN;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String maNCC) {
        MaNCC = maNCC;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String ngayLap) {
        NgayLap = ngayLap;
    }

    public double getTongTien() {
        return TongTien;
    }

    public void setTongTien(double tongTien) {
        TongTien = tongTien;
    }
}
