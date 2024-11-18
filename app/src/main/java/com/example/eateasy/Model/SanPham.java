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
    private String tenFileAnh;

    public SanPham(String maSP, String tenSP, String moTa, float giaBan, float giaNhap, int soLuong, String maDanhMuc, String anhSanPham) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        this.maDanhMuc = maDanhMuc;
        this.tenFileAnh = anhSanPham;
    }

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

    public String getAnhSanPham() { return tenFileAnh; }  // Getter cho thuộc tính ảnh
    public void setAnhSanPham(String anhSanPham) { this.tenFileAnh = anhSanPham; }  // Setter cho thuộc tính ảnh

    @Override
    public String toString() {
        // Trả về tên sản phẩm để hiển thị trong Spinner
        return tenSP;
    }
}
