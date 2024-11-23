package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import com.example.eateasy.Adapter.User.BestFoodAdapter;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamList extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText edtSearch;
    private Spinner spinnerSort;
    private ArrayList<SanPham> originalSanPhamList = new ArrayList<>();
    private RecyclerView recyclerViewSanPham;
    private BestFoodAdapter sanPhamAdapter;
    private ArrayList<SanPham> sanPhamList;
    SanPhamInterface sanPhamInterface;
    TextView tvNoProducts;
    String maKH;
    KhachHang khachHang;
    private AutoCompleteTextView searchProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_san_pham_list);

        initwiget();

        Intent intent = getIntent();
        maKH = intent.getStringExtra("maKH");
        Log.d("MAKH",maKH);
        sanPhamList = new ArrayList<>();
        sanPhamAdapter = new BestFoodAdapter(this, sanPhamList,maKH);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewSanPham.setLayoutManager(layoutManager);
        recyclerViewSanPham.setAdapter(sanPhamAdapter);
        sanPhamInterface = SanPhamUtils.getProdutsService();
        loadSanPham();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void loadSanPham() {
        sanPhamInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
                    sanPhamList.clear();
                    sanPhamList.addAll(response.body());
                    sanPhamAdapter.notifyDataSetChanged();
                    fetchAndCheckCustomer(maKH);
                    Toast.makeText(SanPhamList.this, "Sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(SanPhamList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchAndCheckCustomer(String id) {
        KhachHangInterface khachHangInterface = KhachHangUtils.getKhachHangService();

        khachHangInterface.getAllKhachHang().enqueue(new Callback<ArrayList<KhachHang>>() {
            @Override
            public void onResponse(Call<ArrayList<KhachHang>> call, Response<ArrayList<KhachHang>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<KhachHang> customerList = new ArrayList<>();
                    customerList.addAll(response.body());
                    for (KhachHang customer : customerList) {
                        if (customer.getMaKH().compareTo(id) ==0) {
                            String hoten = customer.getHoten() != null ? customer.getHoten() : "Chưa cài đặt";
                            String diaChi = customer.getDiaChi() != null ? customer.getDiaChi() : "Chưa cài đăt";
                            khachHang = new KhachHang(customer.getMaKH(), hoten, customer.getSdt(), customer.getEmail(), diaChi,customer.getUser_id());
                            break;
                        }
                    }
                } else {
                    //Toast.makeText(PaymentActivity.this, "Không thể tải danh sách khách hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {
                Toast.makeText(SanPhamList.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initwiget() {
        btnBack = findViewById(R.id.btnBack_home);
        searchProduct = findViewById(R.id.search_product_list);
        spinnerSort = findViewById(R.id.spinnerSort_sp);
        recyclerViewSanPham = findViewById(R.id.recyclerViewSanPham_list);
        tvNoProducts = findViewById(R.id.tvNoProducts_list);
    }
}