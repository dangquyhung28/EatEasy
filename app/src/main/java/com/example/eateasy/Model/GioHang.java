package com.example.eateasy.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GioHang {
    @SerializedName("MaDonHang")
    private String maDonHang;

    @SerializedName("MaKhachHang")
    private String maKhachHang;

    @SerializedName("TenKhachHang")
    private String tenKhachHang;

    @SerializedName("MaSanPham")
    private String maSanPham;

    @SerializedName("TenSanPham")
    private String tenSanPham;

    @SerializedName("MoTaSanPham")
    private String moTaSanPham;

    @SerializedName("GiaBan")
    private double giaBan;

    @SerializedName("AnhSanPham")
    private String anhSanPham;

    @SerializedName("SoLuong")
    private int soLuong;

    @SerializedName("GiamGia")
    private double giamGia;

    @SerializedName("ThanhTien")
    private double thanhTien;

    @SerializedName("NgayTaoGioHang")
    private String ngayTaoGioHang;

    // Constructor


    public GioHang(String maDonHang, String maKhachHang, String tenKhachHang, String maSanPham, String tenSanPham, String moTaSanPham, double giaBan, String anhSanPham, int soLuong, double giamGia, double thanhTien, String ngayTaoGioHang) {
        this.maDonHang = maDonHang;
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.moTaSanPham = moTaSanPham;
        this.giaBan = giaBan;
        this.anhSanPham = anhSanPham;
        this.soLuong = soLuong;
        this.giamGia = giamGia;
        this.thanhTien = thanhTien;
        this.ngayTaoGioHang = ngayTaoGioHang;
    }

    // Getters and Setters


    public String getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(String anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
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

    public String getNgayTaoGioHang() {
        return ngayTaoGioHang;
    }

    public void setNgayTaoGioHang(String ngayTaoGioHang) {
        this.ngayTaoGioHang = ngayTaoGioHang;
    }
}
