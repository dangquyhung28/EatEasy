package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Activity.Login_Activity;
import com.example.eateasy.R;

public class HomeActivity extends AppCompatActivity {
    ImageView cartBtn, logoutBtn, filterBtn, searchBtn;
    TextView tv_nameAccount, tvViewAll;
    EditText edtSearchFood;
    Spinner locationSp, timeSp, priceSp;
    RecyclerView bestFoodView, categoryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ánh xá
        initwiget();

        //logout
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(HomeActivity.this, Login_Activity.class);
                startActivity(inten);
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        tv_nameAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initwiget() {
        logoutBtn = findViewById(R.id.logoutBtn);
        cartBtn = findViewById(R.id.cartBtn);
        filterBtn = findViewById(R.id.filterBtn);
        searchBtn = findViewById(R.id.searchBtn);
        tv_nameAccount = findViewById(R.id.tv_nameAccount);
        tvViewAll = findViewById(R.id.tvViewAll);
        edtSearchFood = findViewById(R.id.edtSearchFood);
        locationSp = findViewById(R.id.locationSp);
        timeSp = findViewById(R.id.timeSp);
        priceSp = findViewById(R.id.priceSp);
        bestFoodView = findViewById(R.id.bestFoodView);
        categoryView = findViewById(R.id.categoryView);
    }
}