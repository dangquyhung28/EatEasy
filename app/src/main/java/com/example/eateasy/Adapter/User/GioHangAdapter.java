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
import com.example.eateasy.Activity.User.CartActivity;
import com.example.eateasy.R;
import com.example.eateasy.Model.GioHang;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Interface.OnQuantityChangeListener;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {

    private final Context context;
    private final List<GioHang> gioHangList;
    private OnQuantityChangeListener quantityChangeListener;
    private String maKH;

    public GioHangAdapter(Context context, List<GioHang> gioHangList, String makh,OnQuantityChangeListener listener) {
        this.context = context;
        this.gioHangList = gioHangList;
        this.maKH = makh;
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart_product, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);

        // Set data to views
        holder.tenSPV.setText(gioHang.getTenSanPham());
        holder.giaBanTV.setText(String.format("%,d VND", (int) gioHang.getGiaBan()));
        holder.moTaTV.setText(gioHang.getMoTaSanPham());
        holder.tvNumber.setText(String.valueOf(gioHang.getSoLuong()));
        String imageUrl = gioHang.getAnhSanPham();
        Glide.with(holder.anhSanPhamImg.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.avatar) // Hình ảnh mặc định trong trường hợp không tải được
                .error(R.drawable.avatar) // Hình ảnh hiển thị khi có lỗi
                .into(holder.anhSanPhamImg);

        // Nút cộng
        holder.btnPlus.setOnClickListener(v -> {
            int soLuongMoi = gioHang.getSoLuong() + 1;
            gioHang.setSoLuong(soLuongMoi);
            holder.tvNumber.setText(String.valueOf(soLuongMoi));

            // Gọi callback khi số lượng thay đổi
            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged(position, soLuongMoi);
            }
        });

        // Nút trừ
        holder.btnMinus.setOnClickListener(v -> {
            int soLuongMoi = Math.max(0, gioHang.getSoLuong() - 1); // Không giảm dưới 1
            gioHang.setSoLuong(soLuongMoi);
            holder.tvNumber.setText(String.valueOf(soLuongMoi));

            // Gọi callback khi số lượng thay đổi
            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged(position, soLuongMoi);
            }
        });


        // Xóa sản phẩm
        holder.iconDelete.setOnClickListener(v -> {
            String maSP = gioHangList.get(position).getMaSanPham();
            deleteProduct(maSP, maKH);
            gioHangList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, gioHangList.size());

        });

        // Tạm thời để ảnh sản phẩm trống (nếu cần load ảnh, dùng Glide hoặc Picasso)
        //holder.anhSanPhamImg.setImageResource(android.R.color.transparent);
    }

    private void deleteProduct(String masp, String maKH) {
        GioHangInterface gioHangInterface;
        gioHangInterface = GioHangUtils.getGioHangService();
        gioHangInterface.deleteProduct(maKH,masp).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context.getApplicationContext(), "Đã xóa sản phẩm khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), "Không thể xóa sản phẩm khỏi giỏ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    static class GioHangViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPV, giaBanTV, moTaTV, tvNumber;
        TextView btnPlus, btnMinus;
        ImageView anhSanPhamImg, iconDelete;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPV = itemView.findViewById(R.id.tenSPV);
            giaBanTV = itemView.findViewById(R.id.giaBanTV);
            moTaTV = itemView.findViewById(R.id.moTaTV);
            tvNumber = itemView.findViewById(R.id.tv_number);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            anhSanPhamImg = itemView.findViewById(R.id.anhSanPhamImgV);
            iconDelete = itemView.findViewById(R.id.icon_delete);
        }
    }
}
