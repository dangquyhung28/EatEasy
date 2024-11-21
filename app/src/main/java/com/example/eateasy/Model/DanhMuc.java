package com.example.eateasy.Model;

import java.io.Serializable;

public class DanhMuc implements Serializable {
    private String MaDanhMuc;
    private String TenDanhMuc;

    public DanhMuc(String maDanhMuc, String tenDanhMuc) {
        MaDanhMuc = maDanhMuc;
        TenDanhMuc = tenDanhMuc;
    }
    public DanhMuc(){}

    public String getMaDanhMuc() {
        return MaDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        MaDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        TenDanhMuc = tenDanhMuc;
    }

    @Override
    public String toString() {
        return TenDanhMuc; // Hiển thị tên danh mục trên Spinner
    }
}

