package com.example.eateasy.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.R;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.KhachHangViewHolder> {

    private Context context;
    private ArrayList<KhachHang> khachHangList;

    public KhachHangAdapter(Context context, ArrayList<KhachHang> khachHangList) {
        this.context = context;
        this.khachHangList = khachHangList;
    }

    @NonNull
    @Override
    public KhachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khach_hang, parent, false);
        return new KhachHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachHangViewHolder holder, int position) {
        KhachHang khachHang = khachHangList.get(position);

        holder.tvMaKH.setText(khachHang.getMaKH());
        holder.tvHoTen.setText(khachHang.getHoten() == null ? "Không rõ" : khachHang.getHoten());
        holder.tvEmail.setText(khachHang.getEmail());
        holder.tvSDT.setText(khachHang.getSdt());
    }

    @Override
    public int getItemCount() {
        return khachHangList.size();
    }

    public static class KhachHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaKH, tvHoTen, tvEmail, tvSDT;

        public KhachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaKH = itemView.findViewById(R.id.tvMaKH);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvSDT = itemView.findViewById(R.id.tvSDT);
        }
    }
}
