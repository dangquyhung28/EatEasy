package com.example.eateasy.Activity.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Adapter.Admin.SanPhamAdapter;
import com.example.eateasy.Adapter.User.CartApdapter;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ImageView backBtn_cart;
    RecyclerView rvcCart;
    EditText edtCode;
    Button applyBtn, placeOderBtn;
    TextView Subtotal, Delivery, Total_Tax, Total;
    ArrayList<SanPham> sanPhams = new ArrayList<>();
    CartApdapter cartApdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        initWidgets();
        cartApdapter = new CartApdapter(CartActivity.this, sanPhams);
        rvcCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rvcCart.setAdapter(cartApdapter);
        loadProductDetails();
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

            SanPham sp = new SanPham(maSP,tenSP,moTa,giaBan,giaNhap,soLuong,maDanhMuc,anhSanPham);
            sanPhams.add(sp);
            cartApdapter.notifyDataSetChanged();



        }
    }

    private void initWidgets() {
        backBtn_cart = findViewById(R.id.backBtn_cart);
        rvcCart = findViewById(R.id.rvcCart);
        edtCode = findViewById(R.id.edtCode);
        applyBtn = findViewById(R.id.applyBtn);
        placeOderBtn = findViewById(R.id.placeOderBtn);
        Subtotal = findViewById(R.id.Subtotal);
        Delivery = findViewById(R.id.Delivery);
        Total_Tax = findViewById(R.id.Total_Tax);
        Total = findViewById(R.id.Total);

    }
}