package com.example.eateasy.Model;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChiTietDonHang {
    @SerializedName("MaDonHang")
    private String maDonHang;

    @SerializedName("TenKhachHang")
    private String tenKhachHang;

    @SerializedName("DiaChiKhachHang")
    private String diaChiKhachHang;

    @SerializedName("NgayTaoGioHang")
    private String ngayTaoGioHang;

    @SerializedName("SDT")
    private String sdt;

    @SerializedName("SanPham")
    private List<SanPhamDonHang> sanPham;

    public ChiTietDonHang(){}

    public ChiTietDonHang(String maDonHang, String tenKhachHang, String diaChiKhachHang, String ngayTaoGioHang, String sdt, List<SanPhamDonHang> sanPham) {
        this.maDonHang = maDonHang;
        this.tenKhachHang = tenKhachHang;
        this.diaChiKhachHang = diaChiKhachHang;
        this.ngayTaoGioHang = ngayTaoGioHang;
        this.sdt = sdt;
        this.sanPham = sanPham;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }


    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getDiaChiKhachHang() {
        return diaChiKhachHang;
    }

    public void setDiaChiKhachHang(String diaChiKhachHang) {
        this.diaChiKhachHang = diaChiKhachHang;
    }

    public String getNgayTaoGioHang() {
        return ngayTaoGioHang;
    }

    public void setNgayTaoGioHang(String ngayTaoGioHang) {
        this.ngayTaoGioHang = ngayTaoGioHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public List<SanPhamDonHang> getSanPham() {
        return sanPham;
    }

    public void setSanPham(List<SanPhamDonHang> sanPham) {
        this.sanPham = sanPham;
    }
}
