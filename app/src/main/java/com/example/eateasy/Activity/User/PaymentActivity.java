package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Adapter.User.DonHangAdapter;
import com.example.eateasy.Model.GioHang;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
    ImageView backBtn_pay;
    TextView tvTen, tvSdt, tvTongSoSP, pay_total;
    RecyclerView rcvOrder;
    RadioButton payment_cod, payment_credit_card, payment_e_wallet;
    ArrayList<GioHang> sanPhams;
    DonHangAdapter donHangAdapter;
    Button btnDatHang;
    String maKH;
    KhachHang khachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        initWidgets();
        Intent intent = getIntent();
        maKH = intent.getStringExtra("maKH");


        sanPhams = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(PaymentActivity.this, sanPhams);
        rcvOrder.setLayoutManager(new LinearLayoutManager(PaymentActivity.this));
        rcvOrder.setAdapter(donHangAdapter);
        loadProductDetails(maKH);





    }

    private void loadProductDetails(String maKH) {
        GioHangInterface gioHangInterface;
        gioHangInterface = GioHangUtils.getGioHangService();
        gioHangInterface.getSanPhamByKhanhHang(maKH).enqueue(new Callback<ArrayList<GioHang>>() {
            @Override
            public void onResponse(Call<ArrayList<GioHang>> call, Response<ArrayList<GioHang>> response) {
                sanPhams.clear();
                if(response.body() != null && !response.body().isEmpty()){
                    sanPhams.addAll(response.body());
                    rcvOrder.setVisibility(View.VISIBLE);
                    LoadTongTien();
                    fetchAndCheckCustomer(maKH);
                }else {
                    rcvOrder.setVisibility(View.GONE);
                }
                donHangAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<GioHang>> call, Throwable t) {

            }
        });
    }

    private void LoadTongTien() {
        double tongTien = 0;
        for(GioHang gh : sanPhams){
            tongTien = tongTien + gh.getThanhTien();
        }
        pay_total.setText(String.valueOf(tongTien + " VND"));
    }


    private void initWidgets() {
        backBtn_pay = findViewById(R.id.backBtn_history);
        tvTen = findViewById(R.id.tvTen);
        tvSdt = findViewById(R.id.tvSdt);

        tvTongSoSP = findViewById(R.id.tvTongSoSP);
        pay_total = findViewById(R.id.pay_total);
        rcvOrder = findViewById(R.id.rcvDonHang);
        payment_cod = findViewById(R.id.payment_cod);
        payment_credit_card = findViewById(R.id.payment_credit_card);
        payment_e_wallet = findViewById(R.id.payment_e_wallet);
        btnDatHang = findViewById(R.id.btnDatHang);
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
                            String hoten = customer.getHoten() != null ? customer.getHoten() : "user_shdka8iwj";
                            String diaChi = customer.getDiaChi() != null ? customer.getDiaChi() : "Chưa cài đăt";
                            khachHang = new KhachHang(customer.getMaKH(), hoten, customer.getSdt(), customer.getEmail(), diaChi,customer.getUser_id());
                            tvTen.setText(khachHang.getHoten());
                            tvSdt.setText(khachHang.getSdt());
                            break;
                        }
                    }
                } else {
                    //Toast.makeText(PaymentActivity.this, "Không thể tải danh sách khách hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {
                Toast.makeText(PaymentActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
