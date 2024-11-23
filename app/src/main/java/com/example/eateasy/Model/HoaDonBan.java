package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class HoaDonBan {
    @SerializedName("MaDH")
    private String maDH;

    @SerializedName("MaKH")
    private String maKH;

    @SerializedName("MaNV")
    private String maNV;

    @SerializedName("NgayLap")
    private String ngayLap;

    @SerializedName("PhuongThucThanhToan")
    private String phuongThucThanhToan;

    @SerializedName("TongTien")
    private double tongTien;

    @SerializedName("TrangThai")
    private String trangThai;

    public HoaDonBan(String maDH, String maKH, String maNV, String ngayLap, String phuongThucThanhToan, double tongTien, String trangThai) {
        this.maDH = maDH;
        this.maKH = maKH;
        this.maNV = maNV;
        this.ngayLap = ngayLap;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Getter và Setter cho các thuộc tính
    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
