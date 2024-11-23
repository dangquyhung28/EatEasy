package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.Adapter.User.BestFoodAdapter;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText edtSearch;
    private Spinner spinnerSort;
    private ArrayList<SanPham> originalSanPhamList = new ArrayList<>();
    private RecyclerView recyclerViewSanPham;
    private BestFoodAdapter sanPhamAdapter;
    private ArrayList<SanPham> sanPhamList;
    private String maDanhMuc;
    SanPhamInterface sanPhamInterface;
    HorizontalScrollView horizontalScrollView ;
    LinearLayout linearLayoutDanhMuc;
    List<DanhMuc> danhMucList;
    TextView tvNoProducts;
    String maKH;
    private AutoCompleteTextView searchProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_san_pham);
        Intent intent = getIntent();
        danhMucList = (List<DanhMuc>) intent.getSerializableExtra("danhMucList");
        maDanhMuc = intent.getStringExtra("maDanhMuc");
        maKH = intent.getStringExtra("maKH");
        initwiget();
        sanPhamList = new ArrayList<>();
        sanPhamAdapter = new BestFoodAdapter(this, sanPhamList,maKH);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewSanPham.setLayoutManager(layoutManager);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);
        sanPhamInterface = SanPhamUtils.getProdutsService();
        loadSanPham(maDanhMuc);
        loadDanhMuc();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        //sort
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Giá tăng dần
                        sortSanPhamByGia(true);
                        break;
                    case 1: // Giá giảm dần
                        sortSanPhamByGia(false);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì nếu không có lựa chọn
            }
        });


    }

    private void sortSanPhamByGia(boolean isAscending) {
        if (sanPhamList != null && !sanPhamList.isEmpty()) {
            Collections.sort(sanPhamList, (o1, o2) -> {
                if (isAscending) {
                    return Float.compare(o1.getGiaBan(), o2.getGiaBan()); // Sắp xếp tăng dần
                } else {
                    return Float.compare(o2.getGiaBan(), o1.getGiaBan()); // Sắp xếp giảm dần
                }
            });
            sanPhamAdapter.notifyDataSetChanged();
        }
    }

    private void loadSanPham(String maDanhMuc) {
        sanPhamInterface.getSanPhamByMaDanhMuc(maDanhMuc).enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                sanPhamList.clear();
                if (response.body() != null && !response.body().isEmpty()) {
                    sanPhamList.addAll(response.body());
                    originalSanPhamList.clear();
                    originalSanPhamList.addAll(response.body());
                    setupAutoComplete(searchProduct, sanPhamList);
                    recyclerViewSanPham.setVisibility(View.VISIBLE);
                    tvNoProducts.setVisibility(View.GONE);
                } else {
                    recyclerViewSanPham.setVisibility(View.GONE);
                    tvNoProducts.setVisibility(View.VISIBLE);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(SanPhamActivity.this, "Lỗi, vui lòng đợi chút", Toast.LENGTH_SHORT).show();
                Log.d("Lỗi đây này: ",t.getMessage());
            }
        });

    }

    private void setupAutoComplete(AutoCompleteTextView searchProduct, ArrayList<SanPham> sanPhamList) {
        ArrayList<String> productNames = new ArrayList<>();
        for (SanPham product : sanPhamList) {
            productNames.add(product.getTenSP()); // Thêm tên sản phẩm vào danh sách gợi ý
        }

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(SanPhamActivity.this,
                android.R.layout.simple_dropdown_item_1line, productNames);

        searchProduct.setAdapter(autoCompleteAdapter);

        // Xử lý khi người dùng chọn một sản phẩm từ gợi ý
        searchProduct.setOnItemClickListener((parent, view, position, id) -> {
            String selectedProductName = (String) parent.getItemAtPosition(position);
            filterProduct(selectedProductName);
        });

        // Lọc danh sách khi người dùng nhập từ khóa
        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProduct(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void filterProduct(String keyword) {
        sanPhamList.clear(); // Xóa danh sách hiện tại

        for (SanPham product : originalSanPhamList) { // Duyệt qua danh sách gốc
            if (product.getTenSP().toLowerCase().contains(keyword.toLowerCase())) {
                sanPhamList.add(product); // Thêm sản phẩm phù hợp vào danh sách
            }
        }

        sanPhamAdapter.notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
    }

    private void loadDanhMuc() {
        List<TextView> textViewList = new ArrayList<>();
        for (DanhMuc danhMuc : danhMucList) {
            String tenDanhMuc = danhMuc.getTenDanhMuc().toUpperCase();
            TextView textView = new TextView(this);
            textViewList.add(textView);
            textView.setText(tenDanhMuc);
            textView.setPadding(32, 8, 16, 8);
            textView.setTextSize(12);
            textView.setGravity(Gravity.CENTER);


            textView.setOnClickListener(v -> {
                maDanhMuc = danhMuc.getMaDanhMuc();
                // Xóa đường gạch dưới cho tất cả TextView (nếu có nhiều TextView)
                for (TextView tv : textViewList) {
                    tv.setPaintFlags(tv.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
                }

                // Hiển thị đường gạch dưới cho TextView khi được chọn
                textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                loadSanPham(maDanhMuc);
                Toast.makeText(this, tenDanhMuc, Toast.LENGTH_SHORT).show();

            });

            // Thêm TextView vào LinearLayout
            linearLayoutDanhMuc.addView(textView);
        }
    }

    private void initwiget() {
        // Ánh xạ view
        btnBack = findViewById(R.id.btnBack);
        searchProduct = findViewById(R.id.search_product);
        spinnerSort = findViewById(R.id.spinnerSort);
        recyclerViewSanPham = findViewById(R.id.recyclerViewSanPham);
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        linearLayoutDanhMuc = findViewById(R.id.linearLayoutDanhMuc);
        tvNoProducts = findViewById(R.id.tvNoProducts);
    }
    
    
}