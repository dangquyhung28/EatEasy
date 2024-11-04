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

import com.example.eateasy.R;

public class DetailActivity extends AppCompatActivity {
    ImageView backBtn_detail, imageViewSP, favBtn;
    TextView titleTxt, priceTxt, rateTxt, timeTxt, descriptionTxt, minusBtn, plusBtn, numTxt, totalTxt;
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
    }

    private void initWidgets() {
        backBtn_detail = findViewById(R.id.backBtn_detail);
        imageViewSP = findViewById(R.id.imageViewSP);
        favBtn = findViewById(R.id.favBtn);
        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        rateTxt = findViewById(R.id.rateTxt);
        timeTxt = findViewById(R.id.timeTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        numTxt = findViewById(R.id.numTxt);
        totalTxt = findViewById(R.id.totalTxt);
        ratingBar = findViewById(R.id.ratingBar);
        addBtn = findViewById(R.id.addBtn);
        buyBtn = findViewById(R.id.buyBtn);
    }
}
