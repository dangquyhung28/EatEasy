package com.example.eateasy.Adapter.Admin;

import com.example.eateasy.Model.SanPhamDonHang;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.R;

import java.util.List;

public class SanPhamDonHangAdapter extends RecyclerView.Adapter<SanPhamDonHangAdapter.ViewHolder> {

    private List<SanPhamDonHang> sanPhamList;

    public SanPhamDonHangAdapter(List<SanPhamDonHang> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_chitietdonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPhamDonHang sanPham = sanPhamList.get(position);
        holder.tvTenSanPham.setText(sanPham.getTenSanPham());
        holder.tvSoLuong.setText(String.valueOf(sanPham.getSoLuongBan()));
        holder.tvThanhTien.setText(String.format("%.0f VND", sanPham.getThanhTien()));
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham, tvSoLuong, tvThanhTien;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
        }
    }
}