package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Activity.Login_Activity;
import com.example.eateasy.Adapter.Admin.SanPhamAdapter;
import com.example.eateasy.Adapter.User.BestFoodAdapter;
import com.example.eateasy.Adapter.User.CategoryAdapter;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.CategoryInterface;
import com.example.eateasy.Retrofit.CategoryUtils;
import com.example.eateasy.Retrofit.ProductsInterface;
import com.example.eateasy.Retrofit.ProductsUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    // Declaring UI components
    private TextView textView, tvNameAccount, tvBestFood, tvSuggestions, tvViewAll, tvSeeMore;
    private EditText edtSearchFood;
    private ImageView logoutBtn, filterBtn, searchBtn, cartBtn;
    private RecyclerView bestFoodView, suggestionsView;
    private Spinner locationSp, timeSp, priceSp;
    ProductsInterface productsInterface;
    BestFoodAdapter bestFoodAdapter;
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    CategoryInterface categoryInterface;
    ArrayList<DanhMuc> danhMuclist = new ArrayList<>();
    CategoryAdapter categoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Assuming this is your layout XML file
        initwiget();
        setupListeners();

        //load san Pham
        bestFoodView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        bestFoodAdapter = new BestFoodAdapter(HomeActivity.this, sanPhamList);
        bestFoodView.setAdapter(bestFoodAdapter);
        productsInterface = ProductsUtils.getProdutsService();
        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
                    sanPhamList.clear();
                    sanPhamList.addAll(response.body());
                    bestFoodAdapter.notifyDataSetChanged();
                    Toast.makeText(HomeActivity.this, "Dữ liệu được cập nhật!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        //load danh muc
        categoryInterface = CategoryUtils.getCategoryService();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        suggestionsView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(HomeActivity.this, danhMuclist);
        categoryInterface.getAllDanhMuc().enqueue(new Callback<ArrayList<DanhMuc>>() {
            @Override
            public void onResponse(Call<ArrayList<DanhMuc>> call, Response<ArrayList<DanhMuc>> response) {
                if(response.isSuccessful()){
                    danhMuclist.clear();
                    danhMuclist.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DanhMuc>> call, Throwable t) {

            }
        });
        suggestionsView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


    }

    private void initwiget() {
        textView = findViewById(R.id.textView);
        tvNameAccount = findViewById(R.id.tv_nameAccount);
        edtSearchFood = findViewById(R.id.edtSearchFood);
        logoutBtn = findViewById(R.id.logoutBtn);
        filterBtn = findViewById(R.id.filterBtn);
        searchBtn = findViewById(R.id.searchBtn);
        cartBtn = findViewById(R.id.cartBtn);
        bestFoodView = findViewById(R.id.bestFoodView);
        suggestionsView = findViewById(R.id.suggestionsView);
        tvBestFood = findViewById(R.id.tvBestFood);
        tvSuggestions = findViewById(R.id.tvSuggestions);
        tvViewAll = findViewById(R.id.tvViewAll);
        tvSeeMore = findViewById(R.id.tvSeeMore);
        locationSp = findViewById(R.id.locationSp);
        timeSp = findViewById(R.id.timeSp);
        priceSp = findViewById(R.id.priceSp);
    }

    private void setupListeners() {
        // Example: Setting a click listener on the logout button
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Example: Setting a click listener for the search button
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFood();
            }
        });
    }

    private void logout() {
       Intent intent = new Intent(HomeActivity.this, Login_Activity.class);
       startActivity(intent);
    }

    private void searchFood() {
        String query = edtSearchFood.getText().toString();

    }
}