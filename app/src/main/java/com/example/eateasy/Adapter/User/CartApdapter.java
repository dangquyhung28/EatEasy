package com.example.eateasy.Adapter.User;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Adapter.Admin.SanPhamAdapter;
import com.example.eateasy.Fragments.Admin.ProductDetailFragment;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;

import java.util.List;

public class CartApdapter extends RecyclerView.Adapter<CartApdapter.CartViewHolder>{
    private List<SanPham> sanPhamList;
    private Context context;

    public CartApdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public CartApdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new CartApdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartApdapter.CartViewHolder holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        holder.tenSPTextView.setText(sanPham.getTenSP());
        holder.giaBanTextView.setText(String.format("Giá bán: %.0f VNĐ", sanPham.getGiaBan()));
        holder.moTaTextView.setText(sanPham.getMoTa());
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

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPTextView, giaBanTextView, moTaTextView;
        ImageView imageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPTextView = itemView.findViewById(R.id.tenSPV);
            giaBanTextView = itemView.findViewById(R.id.giaBanTV);
            moTaTextView = itemView.findViewById(R.id.moTaTV);
            imageView = itemView.findViewById(R.id.anhSanPhamImgV);

        }
    }
}
