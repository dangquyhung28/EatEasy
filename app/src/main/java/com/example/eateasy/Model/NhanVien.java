package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

public class NhanVien {
    @SerializedName("MaNV")
    private String maNV;

    @SerializedName("TenNV")
    private String tenNv;

    @SerializedName("SDT")
    private String sdt;

    @SerializedName("Email")
    private String email;

    @SerializedName("GioiTinh")
    private String gioiTinh;

    @SerializedName("NgaySinh")
    private String ngaySinh;

    @SerializedName("ChucVu")
    private String nchucVu;

    @SerializedName("DiaChi")
    private String diaChi;

    @SerializedName("User_id")
    private int user_id;

    public NhanVien(String maNV, String tenNv, String email, String sdt, String gioiTinh, String ngaySinh, String nchucVu, String diaChi, int user_id) {
        this.maNV = maNV;
        this.tenNv = tenNv;
        this.email = email;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.nchucVu = nchucVu;
        this.diaChi = diaChi;
        this.user_id = user_id;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNv() {
        return tenNv;
    }

    public void setTenNv(String tenNv) {
        this.tenNv = tenNv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNchucVu() {
        return nchucVu;
    }

    public void setNchucVu(String nchucVu) {
        this.nchucVu = nchucVu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
