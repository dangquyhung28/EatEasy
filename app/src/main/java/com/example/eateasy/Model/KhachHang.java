package com.example.eateasy.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KhachHang implements Serializable {

    @SerializedName("MaKH")
    private String maKH;

    @SerializedName("HoTen")
    private String hoten;

    @SerializedName("SĐT")
    private String sdt;

    @SerializedName("Email")
    private String email;

    @SerializedName("DiaChi")
    private String diaChi;

    @SerializedName("User_id")
    private int user_id;

    public KhachHang(String maKH, String hoten, String sdt, String email, String diaChi, int user_id) {
        this.maKH = maKH;
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.user_id = user_id;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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
