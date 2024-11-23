package com.example.eateasy.Activity.User;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Adapter.User.GioHangAdapter;
import com.example.eateasy.Model.GioHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Interface.OnQuantityChangeListener;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    ImageView backBtn_cart;
    RecyclerView rvcCart;
    EditText edtCode;
    Button applyBtn, placeOderBtn;
    TextView thanhTienView, giamGiaView, tongTienView;
    ArrayList<GioHang> sanPhamGioHang= new ArrayList<>();
    GioHangAdapter cartApdapter;
    private String maKH;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        Intent intent = getIntent();
        maKH = intent.getStringExtra("maKH");
        initWidgets();
        sanPhamGioHang = new ArrayList<>();
        cartApdapter = new GioHangAdapter(CartActivity.this, sanPhamGioHang,maKH, new OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int position, int newQuantity) {
                GioHang gioHang = sanPhamGioHang.get(position);
                gioHang.setSoLuong(newQuantity);
                LoadTongTien();

            }
        });
        rvcCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rvcCart.setAdapter(cartApdapter);
        loadProductDetails(maKH);

        placeOderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
                progressDialog.setMessage("Đang cập nhật giỏ hàng...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                // Bắt đầu cập nhật sản phẩm
                updateProductRecursively(0, maKH, progressDialog);

            }
        });

        //

    }
    private void updateProductRecursively(final int index, String maKH, ProgressDialog progressDialog) {
        // Kiểm tra nếu đã hoàn thành việc cập nhật tất cả sản phẩm
        if (index >= sanPhamGioHang.size()) {
            // Đóng ProgressDialog khi hoàn thành
            progressDialog.dismiss();

            // Chuyển sang Activity tiếp theo
            Intent intent1 = new Intent(CartActivity.this, PaymentActivity.class);
            intent1.putExtra("maKH", maKH);
            startActivity(intent1);
            finish();
            return;
        }

        // Lấy sản phẩm hiện tại
        GioHang gioHang = sanPhamGioHang.get(index);

        // Chuẩn bị dữ liệu cho API
        GioHangInterface gioHangInterface = GioHangUtils.getGioHangService();
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("MaKH", maKH);
        requestBody.addProperty("MaSP", gioHang.getMaSanPham());
        requestBody.addProperty("SoLuong", gioHang.getSoLuong());

        // Gửi yêu cầu cập nhật API
        gioHangInterface.capNhatSoLuongSanPham(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Tiếp tục với sản phẩm tiếp theo
                    updateProductRecursively(index + 1, maKH, progressDialog);
                } else {
                    // Xử lý lỗi cập nhật thất bại
                    Log.e("API Error", "Lỗi khi cập nhật sản phẩm: " + response.message());
                    progressDialog.dismiss();
                    Toast.makeText(CartActivity.this, "Cập nhật sản phẩm thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý lỗi kết nối
                Log.e("API Failure", "Lỗi kết nối: " + t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(CartActivity.this, "Kết nối thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void LoadTongTien() {
        double thanhTien = 0;
        double giamGia = 0;
        for(GioHang gh : sanPhamGioHang){
            thanhTien = thanhTien + (gh.getSoLuong()*gh.getGiaBan());
            giamGia = gh.getGiamGia();
        }
        double tongTien = thanhTien - (thanhTien*(giamGia/100));
        thanhTienView.setText(String.valueOf(thanhTien));
        giamGiaView.setText(giamGia +"%");
        tongTienView.setText(String.valueOf(tongTien));

    }


    private void loadProductDetails(String makh) {
        GioHangInterface gioHangInterface;
        gioHangInterface = GioHangUtils.getGioHangService();
        gioHangInterface.getSanPhamByKhanhHang(maKH).enqueue(new Callback<ArrayList<GioHang>>() {
            @Override
            public void onResponse(Call<ArrayList<GioHang>> call, Response<ArrayList<GioHang>> response) {
                sanPhamGioHang.clear();
                if(response.body() != null && !response.body().isEmpty()){
                    sanPhamGioHang.addAll(response.body());
                    rvcCart.setVisibility(View.VISIBLE);
                    LoadTongTien();
                }else {
                    rvcCart.setVisibility(View.GONE);
                }
                cartApdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<GioHang>> call, Throwable t) {

            }
        });

    }


    private void initWidgets() {
        backBtn_cart = findViewById(R.id.backBtn_cart);
        rvcCart = findViewById(R.id.rvcCart);
        edtCode = findViewById(R.id.edtCode);
        applyBtn = findViewById(R.id.applyBtn);
        placeOderBtn = findViewById(R.id.placeOrderBtn);
        thanhTienView = findViewById(R.id.thanhTien);
        giamGiaView = findViewById(R.id.giamGia);
        tongTienView = findViewById(R.id.TongTien);

    }
}