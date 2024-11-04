package com.example.eateasy.Activity.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.R;

public class CartActivity extends AppCompatActivity {
    ImageView backBtn_cart;
    RecyclerView rvcCart;
    EditText edtCode;
    Button applyBtn, placeOderBtn;
    TextView Subtotal, Delivery, Total_Tax, Total;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.CartActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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