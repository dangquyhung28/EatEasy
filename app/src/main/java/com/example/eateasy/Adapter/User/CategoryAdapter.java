package com.example.eateasy.Adapter.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Activity.User.HomeActivity;
import com.example.eateasy.Activity.User.SanPhamActivity;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter  extends  RecyclerView.Adapter<CategoryAdapter.CategotyViewHolder>{
    private List<DanhMuc> danhMucList;
    private Context context;

    public CategoryAdapter(Context context, List<DanhMuc> danhMucList) {
        this.context = context;
        this.danhMucList = danhMucList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategotyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc, parent, false);
        return new CategoryAdapter.CategotyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategotyViewHolder holder, int position) {
        DanhMuc danhMuc = danhMucList.get(position);
        holder.tenDMTextView.setText(danhMuc.getTenDanhMuc());
        int iconResId = getIconForDanhMuc(danhMuc.getMaDanhMuc());
        holder.imageView.setImageResource(iconResId);


        holder.itemView.setOnClickListener(v -> {
            String maDanhMuc = danhMuc.getMaDanhMuc();

            Intent intent = new Intent(v.getContext(), SanPhamActivity.class);
            intent.putExtra("maDanhMuc",maDanhMuc);
            List<DanhMuc> danhMucArrayList = getDanhMucList();
            intent.putExtra("danhMucList", (Serializable) danhMucList);
            v.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return danhMucList.size();
    }

    public static class CategotyViewHolder extends RecyclerView.ViewHolder {
        TextView tenDMTextView;
        ImageView imageView;

        public CategotyViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDMTextView = itemView.findViewById(R.id.tenDanhMuc);
            // moTaTextView = itemView.findViewById(R.id.moTaTextView);
            imageView = itemView.findViewById(R.id.iconDanhMuc);
        }
    }
    public List<DanhMuc> getDanhMucList() {
        return danhMucList;
    }
    private int getIconForDanhMuc(String maDanhMuc) {
        // Gán icon dựa trên MaDanhMuc
        switch (maDanhMuc) {
            case "DM01":
                return R.drawable.snack_icon;
            case "DM02":
                return R.drawable.icon_home_admin;
            case "DM03":
                return R.drawable.ic_product_management;
            default:
                return R.drawable.icon_cart_admin;
        }
    }
}

