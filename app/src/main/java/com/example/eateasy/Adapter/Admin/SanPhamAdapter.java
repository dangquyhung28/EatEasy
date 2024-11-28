package com.example.eateasy.Adapter.Admin;

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
import com.example.eateasy.Fragments.Admin.SanPham.ProductDetailFragment;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {

    private List<SanPham> sanPhamList;
    private Context context;

    public SanPhamAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_admin, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
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
        holder.tvDetail.setOnClickListener(v ->{
            // Tạo một instance của ProductDetailFragment
            ProductDetailFragment detailFragment = new ProductDetailFragment();

            // Truyền dữ liệu qua Bundle
            Bundle bundle = new Bundle();
            bundle.putString("maSP", sanPham.getMaSP());
            bundle.putString("TenSP", sanPham.getTenSP());
            bundle.putString("MoTa", sanPham.getMoTa());
            bundle.putFloat("GiaBan", sanPham.getGiaBan());
            bundle.putFloat("GiaNhap", sanPham.getGiaNhap());
            bundle.putInt("SoLuong", sanPham.getSoLuong());
            bundle.putString("MaDanhMuc", sanPham.getMaDanhMuc());
            bundle.putString("AnhSanPham", sanPham.getAnhSanPham());

            detailFragment.setArguments(bundle);

            // Chuyển sang fragment chi tiết
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_layout_admin, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
        holder.itemView.setOnClickListener(v -> {

        });



    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class SanPhamViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPTextView, giaBanTextView, moTaTextView, tvDetail;
        ImageView imageView;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPTextView = itemView.findViewById(R.id.tenSPTextView);
            giaBanTextView = itemView.findViewById(R.id.giaBanTextView);
            moTaTextView = itemView.findViewById(R.id.moTaTextView);
            imageView = itemView.findViewById(R.id.anhSanPhamImageView);
            tvDetail = itemView.findViewById(R.id.tv_detail);
        }
    }
}
