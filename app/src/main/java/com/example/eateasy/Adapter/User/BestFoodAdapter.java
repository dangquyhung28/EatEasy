package com.example.eateasy.Adapter.User;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;

import java.util.List;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.BestFoodViewHolder>{
    private List<SanPham> sanPhamList;
    private Context context;

    public BestFoodAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public BestFoodAdapter.BestFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.best_food_item, parent, false);
        return new BestFoodAdapter.BestFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestFoodAdapter.BestFoodViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tenSPTextView.setText(sanPham.getTenSP());
        holder.giaBanTextView.setText(String.format("Giá bán: %.0f VNĐ", sanPham.getGiaBan()));
        //holder.moTaTextView.setText(sanPham.getMoTa());
        String imageUrl =sanPham.getAnhSanPham();
        Glide.with(holder.imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.avatar) // Hình ảnh mặc định trong trường hợp không tải được
                .error(R.drawable.avatar) // Hình ảnh hiển thị khi có lỗi
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            
        });


    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class BestFoodViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPTextView, giaBanTextView, moTaTextView;
        ImageView imageView;

        public BestFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPTextView = itemView.findViewById(R.id.tvFoodName);
            giaBanTextView = itemView.findViewById(R.id.tvFoodPrice);
            // moTaTextView = itemView.findViewById(R.id.moTaTextView);
            imageView = itemView.findViewById(R.id.imgFood);
        }
    }
}
