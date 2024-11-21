package com.example.eateasy.Activity.User;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.eateasy.R;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn_detail, imageViewSP, favBtn;
    TextView titleTxt, priceTxt, rateTxt, timeTxt, descriptionTxt, numTxt, totalTxt;
    RatingBar ratingBar;
    Button addBtn, buyBtn;

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
