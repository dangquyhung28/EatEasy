package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
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
import androidx.viewpager2.widget.ViewPager2;

import com.example.eateasy.Activity.Login_Activity;
import com.example.eateasy.Adapter.User.BestFoodAdapter;
import com.example.eateasy.Adapter.User.CategoryAdapter;
import com.example.eateasy.Adapter.User.GoiYSpAdapter;
import com.example.eateasy.Adapter.User.ScrollLineDecoration;
import com.example.eateasy.Adapter.User.SliderAdapter;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DanhMucInterface;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Interface.UserInterface;
import com.example.eateasy.Retrofit.Utils.DanhMucUtils;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;
import com.example.eateasy.Retrofit.Utils.UserUtils;
import com.google.common.util.concurrent.TimeLimiter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    // Declaring UI components
    private TextView tvNameAccount, tvViewMore;

    private ImageView logoutBtn, cartBtn, iconSearch, btnUser;
    private RecyclerView bestFoodView, suggestionsView, goiYSanPham;
    SanPhamInterface productsInterface;
    BestFoodAdapter bestFoodAdapter;
    GoiYSpAdapter goiYSpAdapter;
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    DanhMucInterface categoryInterface;
    ArrayList<DanhMuc> danhMuclist = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    KhachHang khachHang;
    private ScrollLineDecoration scrollLineDecoration;
    int userId;
    private AutoCompleteTextView searchProduct;
    private ViewPager2 slider;
    private Handler sliderHandler;
    private Timer sliderTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Assuming this is your layout XML file
        initwiget();

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 1);
        Toast.makeText(HomeActivity.this, " "+ userId, Toast.LENGTH_SHORT).show();

        fetchAndCheckCustomer(userId);

        //load san Pham

        tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, SanPhamList.class);
                intent.putExtra("maKH", khachHang.getMaKH());
                startActivity(intent1);
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


                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    scrollLineDecoration.setScrolled(false);  // Ẩn đường kẻ khi dừng cuộn
                    goiYSanPham.invalidateItemDecorations();  // Cập nhật lại việc vẽ
                }
            }
        });
        //gio hang
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, CartActivity.class);
                intent1.putExtra("maKH",khachHang.getMaKH());
                startActivity(intent1);
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, Login_Activity.class);
                startActivity(intent1);
            }
        });

        //btn user
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HomeActivity.this, AccountActivity.class);
                intent1.putExtra("maKH",khachHang.getMaKH());
                startActivity(intent1);
            }
        });


        //side
        SliderAdapter adapter = new SliderAdapter(getListImages()); // getListImages() trả về danh sách dữ liệu
        slider.setAdapter(adapter);

        // Tự động chạy slider
        sliderHandler = new Handler(Looper.getMainLooper());
        sliderTimer = new Timer();

        sliderTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                sliderHandler.post(() -> {
                    int currentItem = slider.getCurrentItem();
                    int totalItems = adapter.getItemCount();
                    slider.setCurrentItem((currentItem + 1) % totalItems, true);
                });
            }
        }, 3000, 3000); // Thời gian de

    }

    private void loadSanPham() {
        bestFoodView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        bestFoodAdapter = new BestFoodAdapter(HomeActivity.this, sanPhamList, khachHang.getMaKH());
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
    }

    private void fetchAndCheckCustomer(int id) {
        KhachHangInterface khachHangInterface = KhachHangUtils.getKhachHangService();

        khachHangInterface.getAllKhachHang().enqueue(new Callback<ArrayList<KhachHang>>() {
            @Override
            public void onResponse(Call<ArrayList<KhachHang>> call, Response<ArrayList<KhachHang>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<KhachHang> customerList = new ArrayList<>();
                    customerList.addAll(response.body());
                    for (KhachHang customer : customerList) {
                        if (customer.getUser_id() == id) {
                            String hoten = customer.getHoten() != null ? customer.getHoten() : "khách hàng";
                            String diaChi = customer.getDiaChi() != null ? customer.getDiaChi() : "Chưa cài đăt";
                            khachHang = new KhachHang(customer.getMaKH(), hoten, customer.getSdt(), customer.getEmail(), diaChi,customer.getUser_id());
                            String email = khachHang.getEmail();
                            String maskedEmail = maskEmail(email);
                            tvNameAccount.setText(maskedEmail);
                            loadSanPham();
                            break;
                        }
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "Không thể tải danh sách khách hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadDanhMucSanPham() {
        //load danh muc
        categoryInterface = DanhMucUtils.getCategoryService();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        suggestionsView.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(HomeActivity.this, danhMuclist,khachHang.getMaKH());
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

        tvNameAccount = findViewById(R.id.tv_nameAccount);
        logoutBtn = findViewById(R.id.logoutBtn);

        cartBtn = findViewById(R.id.cartBtn);
        bestFoodView = findViewById(R.id.bestFoodView);
        suggestionsView = findViewById(R.id.suggestionsView);
        goiYSanPham = findViewById(R.id.goiYSanPham);
        searchProduct = findViewById(R.id.search_product_home);

        tvViewMore = findViewById(R.id.tvViewAll);
        iconSearch = findViewById(R.id.icon_search);
        slider = findViewById(R.id.slider);
        btnUser = findViewById(R.id.user_btn);

    }

    private void logout() {
       Intent intent = new Intent(HomeActivity.this, Login_Activity.class);
       startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sliderTimer != null) {
            sliderTimer.cancel();
        }
    }

    // Dummy data cho slider
    private List<String> getListImages() {
        List<String> images = new ArrayList<>();
        images.add("https://oishi.cdn.vccloud.vn/storage/page-home/November2022/DSRgg53KKgnH6XwyoSXF.png");
        images.add("https://oishi.cdn.vccloud.vn/images/history/step-5-7.png");
        images.add("https://oishi.cdn.vccloud.vn/images/history/step-3-1-1.png");
        return images;
    }
    private String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return "Email không hợp lệ";
        }

        String[] parts = email.split("@");
        String namePart = parts[0];
        String domainPart = parts[1];
        if (namePart.length() <= 3) {
            return email;
        }

        String maskedName = namePart.substring(0, 3) + "***";

        return maskedName + "@" + domainPart;
    }



}