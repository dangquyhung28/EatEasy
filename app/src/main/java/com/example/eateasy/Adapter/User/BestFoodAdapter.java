package com.example.eateasy.Adapter.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Activity.User.CartActivity;
import com.example.eateasy.Activity.User.DetailActivity;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BestFoodAdapter extends RecyclerView.Adapter<BestFoodAdapter.BestFoodViewHolder>{
    private List<SanPham> sanPhamList;
    private Context context;
    private String maKH;

    public BestFoodAdapter(Context context, List<SanPham> sanPhamList, String maKH) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.maKH = maKH;
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
            Bundle bundle = new Bundle();
            bundle.putString("maSP", sanPham.getMaSP());
            bundle.putString("TenSP", sanPham.getTenSP());
            bundle.putString("MoTa", sanPham.getMoTa());
            bundle.putFloat("GiaBan", sanPham.getGiaBan());
            bundle.putFloat("GiaNhap", sanPham.getGiaNhap());
            bundle.putInt("SoLuong", sanPham.getSoLuong());
            bundle.putString("MaDanhMuc", sanPham.getMaDanhMuc());
            bundle.putString("AnhSanPham", sanPham.getAnhSanPham());

            bundle.putString("maKH", maKH);


            // Tạo Intent để mở Activity chi tiết sản phẩm
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtras(bundle); // Truyền dữ liệu qua Intent
            context.startActivity(intent);

        });
        holder.btnBuyNow.setOnClickListener(v->{

            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Đang tải...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            themSanPhamVaoGioHang(maKH, sanPham.getMaSP(), 1, progressDialog);
            // Tạo Intent để mở Activity chi tiết sản phẩm

        });



    }
    private void themSanPhamVaoGioHang(String maKH, String maSP, int soLuong, ProgressDialog progressDialog) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("MaKH", maKH);
        requestBody.addProperty("MaSP", maSP);
        requestBody.addProperty("SoLuong", soLuong);

        GioHangInterface gioHangInterface = GioHangUtils.getGioHangService();
        gioHangInterface.addGioHang(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(context, CartActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Thêm thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class BestFoodViewHolder extends RecyclerView.ViewHolder {
        TextView tenSPTextView, giaBanTextView, moTaTextView;
        ImageView imageView;
        Button btnBuyNow;

        public BestFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSPTextView = itemView.findViewById(R.id.tvFoodName);
            giaBanTextView = itemView.findViewById(R.id.tvFoodPrice);
            // moTaTextView = itemView.findViewById(R.id.moTaTextView);
            imageView = itemView.findViewById(R.id.imgFood);
            btnBuyNow = itemView.findViewById(R.id.btnBuyNow);
        }
    }
}
