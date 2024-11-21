package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Activity.Login_Activity;
import com.example.eateasy.Adapter.User.BestFoodAdapter;
import com.example.eateasy.Adapter.User.CategoryAdapter;
import com.example.eateasy.Adapter.User.GoiYSpAdapter;
import com.example.eateasy.Adapter.User.ScrollLineDecoration;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DanhMucInterface;
import com.example.eateasy.Retrofit.Utils.DanhMucUtils;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    // Declaring UI components
    private TextView textView, tvNameAccount, tvBestFood, tvSuggestions, tvViewAll, tvSeeMore;
    private EditText edtSearchFood;
    private ImageView logoutBtn, filterBtn, searchBtn, cartBtn;
    private RecyclerView bestFoodView, suggestionsView, goiYSanPham;
    private Spinner locationSp, timeSp, priceSp;
    SanPhamInterface productsInterface;
    BestFoodAdapter bestFoodAdapter;
    GoiYSpAdapter goiYSpAdapter;
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    DanhMucInterface categoryInterface;
    ArrayList<DanhMuc> danhMuclist = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    private ScrollLineDecoration scrollLineDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Assuming this is your layout XML file
        initwiget();
        setupListeners();

        //load san Pham
        bestFoodView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        bestFoodAdapter = new BestFoodAdapter(HomeActivity.this, sanPhamList);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        goiYSanPham.setLayoutManager(layoutManager);
        bestFoodView.setAdapter(bestFoodAdapter);
        goiYSanPham.setAdapter(bestFoodAdapter);
        productsInterface = SanPhamUtils.getProdutsService();
        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
                    sanPhamList.clear();
                    sanPhamList.addAll(response.body());
                    bestFoodAdapter.notifyDataSetChanged();
                    loadDanhMucSanPham();

                    Toast.makeText(HomeActivity.this, "Dữ liệu được cập nhật!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        scrollLineDecoration = new ScrollLineDecoration();
        goiYSanPham.addItemDecoration(scrollLineDecoration);
        goiYSanPham.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Kiểm tra nếu có cuộn thì hiển thị đường kẻ
                if (dy != 0) {
                    scrollLineDecoration.setScrolled(true);  // Đặt trạng thái cuộn là true
                } else {
                    scrollLineDecoration.setScrolled(false); // Đặt trạng thái cuộn là false khi không cuộn
                }
                goiYSanPham.invalidateItemDecorations();  // Cập nhật lại việc vẽ
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // Khi dừng cuộn (mặc định là IDLE), ẩn đường kẻ
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollLineDecoration.setScrolled(false);  // Ẩn đường kẻ khi dừng cuộn
                    goiYSanPham.invalidateItemDecorations();  // Cập nhật lại việc vẽ
                }
            }
        });

    }

    private void loadDanhMucSanPham() {
        //load danh muc
        categoryInterface = DanhMucUtils.getCategoryService();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        suggestionsView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(HomeActivity.this, danhMuclist);
        suggestionsView.setAdapter(categoryAdapter);
        categoryInterface.getAllDanhMuc().enqueue(new Callback<ArrayList<DanhMuc>>() {
            @Override
            public void onResponse(Call<ArrayList<DanhMuc>> call, Response<ArrayList<DanhMuc>> response) {
                if(response.isSuccessful()){
                    danhMuclist.clear();
                    danhMuclist.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DanhMuc>> call, Throwable t) {

            }
        });
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
        //tvSeeMore = findViewById(R.id.tvSeeMore);
        locationSp = findViewById(R.id.locationSp);
        timeSp = findViewById(R.id.timeSp);
        priceSp = findViewById(R.id.priceSp);
        goiYSanPham = findViewById(R.id.goiYSanPham);
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