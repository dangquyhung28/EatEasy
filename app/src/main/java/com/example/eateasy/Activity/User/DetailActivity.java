package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn_detail, imageViewSP, favBtn;
    TextView titleTxt, priceTxt, rateTxt, timeTxt, descriptionTxt, numTxt, totalTxt;
    RatingBar ratingBar;
    Button addBtn, buyBtn;
    SanPham sanPham;
    String maKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initWidgets(); // Gọi hàm khởi tạo các widget
        loadProductDetails();

        backBtn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = 0;
                themSanPhamVaoGioHang(maKH, sanPham.getMaSP(), soLuong);
            }
        });
    }

    private void themSanPhamVaoGioHang(String maKH, String maSP, int soLuong) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("MaKH", maKH);
        requestBody.addProperty("MaSP", maSP);
        requestBody.addProperty("SoLuong", soLuong);

        GioHangInterface gioHangInterface = GioHangUtils.getGioHangService();
        gioHangInterface.addGioHang(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetailActivity.this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Thêm thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initWidgets() {
        backBtn_detail = findViewById(R.id.backBtn);
        imageViewSP = findViewById(R.id.imageViewSP);

        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        rateTxt = findViewById(R.id.rateTxt);
        timeTxt = findViewById(R.id.timeTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        numTxt = findViewById(R.id.tvQuantity);
        totalTxt = findViewById(R.id.totalTxt);
        ratingBar = findViewById(R.id.ratingBar);
        addBtn = findViewById(R.id.addBtn);
        buyBtn = findViewById(R.id.buyBtn);

    }
    private void loadProductDetails() {
        // Nhận thông tin từ Intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String maSP = bundle.getString("maSP");
            String tenSP = bundle.getString("TenSP");
            String moTa = bundle.getString("MoTa");
            float giaBan = bundle.getFloat("GiaBan");
            float giaNhap = bundle.getFloat("GiaNhap");
            int soLuong = bundle.getInt("SoLuong");
            String maDanhMuc = bundle.getString("MaDanhMuc");
            String anhSanPham = bundle.getString("AnhSanPham");
            maKH = bundle.getString("maKH");
            sanPham = new SanPham(maSP,tenSP,moTa,giaBan,giaNhap,soLuong,maDanhMuc,anhSanPham);
            // Gán thông tin vào các widget
            titleTxt.setText(tenSP);
            priceTxt.setText(giaBan+" VND");
            descriptionTxt.setText(moTa);
            //ratingBar.setRating(soLuong);

            Glide.with(getApplication())
                    .load(anhSanPham)
                    .placeholder(R.drawable.ic_product_management)
                    .error(R.drawable.icon_infomation)
                    .into(imageViewSP);
            totalTxt.setText(String.valueOf(soLuong));
        }
    }
}
