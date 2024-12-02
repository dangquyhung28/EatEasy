package com.example.eateasy.Model;
import com.google.gson.annotations.SerializedName;

public class SanPhamDonHang {
    @SerializedName("MaSanPham")
    private String maSanPham;

    @SerializedName("TenSanPham")
    private String tenSanPham;

    @SerializedName("MoTaSanPham")
    private String moTaSanPham;

    @SerializedName("GiaBan")
    private double giaBan;

    @SerializedName("SoLuong")
    private int soLuongBan;

    @SerializedName("GiamGia")
    private double giamGia;

    @SerializedName("ThanhTien")
    private double thanhTien;

    public SanPhamDonHang(String maSanPham, String tenSanPham, double giaBan, String moTaSanPham, int soLuongBan, double giamGia, double thanhTien) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.moTaSanPham = moTaSanPham;
        this.soLuongBan = soLuongBan;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMoTaSanPham() {
        return moTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        this.moTaSanPham = moTaSanPham;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}
