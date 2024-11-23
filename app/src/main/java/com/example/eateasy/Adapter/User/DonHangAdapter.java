package com.example.eateasy.Adapter.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Model.GioHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Interface.OnQuantityChangeListener;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {

    private final Context context;
    private final List<GioHang> gioHangList;


    public DonHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;

    }

    @NonNull
    @Override
    public DonHangAdapter.DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_payment, parent, false);
        return new DonHangAdapter.DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.DonHangViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);

        // Set data to views
        holder.tenSPV.setText(gioHang.getTenSanPham());
        holder.giaBanTV.setText(String.format("%,d VND", (int) gioHang.getGiaBan()));
        holder.tvNumber.setText(String.format("Số lượng mua: %d", gioHang.getSoLuong()));
        holder.tvThanhTien.setText(String.format("Thành tiền: %,d VND", (int) gioHang.getThanhTien()));
        String imageUrl = gioHang.getAnhSanPham();
        Glide.with(holder.anhSanPhamImgV.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.avatar) // Hình ảnh mặc định trong trường hợp không tải được
                .error(R.drawable.avatar) // Hình ảnh hiển thị khi có lỗi
                .into(holder.anhSanPhamImgV);





        // Tạm thời để ảnh sản phẩm trống (nếu cần load ảnh, dùng Glide hoặc Picasso)
        holder.anhSanPhamImgV.setImageResource(android.R.color.transparent);
    }


    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    static class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPV, giaBanTV, tvNumber, tvThanhTien;

        ImageView anhSanPhamImgV;

        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPV = itemView.findViewById(R.id.tvProductName);
            giaBanTV = itemView.findViewById(R.id.tvProductPrice);
            tvNumber = itemView.findViewById(R.id.tvQuantity);
            tvThanhTien = itemView.findViewById(R.id.tvTotalPrice);
            anhSanPhamImgV = itemView.findViewById(R.id.imgProduct);

        }
    }
}
