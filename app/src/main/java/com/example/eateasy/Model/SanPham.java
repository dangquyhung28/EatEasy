package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class SanPham {
    @SerializedName("MaSP")
    private String maSP;

    @SerializedName("TenSP")
    private String tenSP;

    @SerializedName("MoTa")
    private String moTa;

    @SerializedName("GiaBan")
    private float giaBan;

    @SerializedName("GiaNhap")
    private float giaNhap;

    @SerializedName("MaDanhMuc")
    private String maDanhMuc;

    @SerializedName("SL")
    private int soLuong;

    @SerializedName("AnhSanPham")
    private String anhSanPham;

    // Getters and Setters
    public String getMaSP() { return maSP; }
    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public float getGiaBan() { return giaBan; }
    public void setGiaBan(float giaBan) { this.giaBan = giaBan; }

    public float getGiaNhap() { return giaNhap; }
    public void setGiaNhap(float giaNhap) { this.giaNhap = giaNhap; }

    public String getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(String maDanhMuc) { this.maDanhMuc = maDanhMuc; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public String getAnhSanPham() { return anhSanPham; }  // Getter cho thuộc tính ảnh
    public void setAnhSanPham(String anhSanPham) { this.anhSanPham = anhSanPham; }  // Setter cho thuộc tính ảnh
}
