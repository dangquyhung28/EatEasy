package com.example.eateasy.Model;

public class SoDonHang {
    private int TongSoDonHangHomNay;
    private int TongSoDonHangThangNay;
    private int TongSoDonHangThangTruoc;
    private int TongSoHoaDon;

    public SoDonHang(int tongSoDonHangHomNay, int tongSoDonHangThangNay, int tongSoDonHangThangTruoc, int tongSoHoaDon) {
        TongSoDonHangHomNay = tongSoDonHangHomNay;
        TongSoDonHangThangNay = tongSoDonHangThangNay;
        TongSoDonHangThangTruoc = tongSoDonHangThangTruoc;
        TongSoHoaDon = tongSoHoaDon;
    }

    public int getTongSoDonHangHomNay() {
        return TongSoDonHangHomNay;
    }

    public void setTongSoDonHangHomNay(int tongSoDonHangHomNay) {
        TongSoDonHangHomNay = tongSoDonHangHomNay;
    }

    public int getTongSoDonHangThangNay() {
        return TongSoDonHangThangNay;
    }

    public void setTongSoDonHangThangNay(int tongSoDonHangThangNay) {
        TongSoDonHangThangNay = tongSoDonHangThangNay;
    }

    public int getTongSoDonHangThangTruoc() {
        return TongSoDonHangThangTruoc;
    }

    public void setTongSoDonHangThangTruoc(int tongSoDonHangThangTruoc) {
        TongSoDonHangThangTruoc = tongSoDonHangThangTruoc;
    }

    public int getTongSoHoaDon() {
        return TongSoHoaDon;
    }

    public void setTongSoHoaDon(int tongSoHoaDon) {
        TongSoHoaDon = tongSoHoaDon;
    }
}
