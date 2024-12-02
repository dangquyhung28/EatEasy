package com.example.eateasy.Activity.User;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.Adapter.User.DonHangAdapter;
import com.example.eateasy.Model.GioHang;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.ChiTietDonHangInnterface;
import com.example.eateasy.Retrofit.Interface.GioHangInterface;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Utils.ChiTietDonHangUtils;
import com.example.eateasy.Retrofit.Utils.GioHangUtils;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
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
    AppCompatButton btnDatHang;
    String maKH;
    String maDon;
    KhachHang khachHang;
    ChiTietDonHangInnterface chiTietDonHangInnterface;

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
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkKH()){
                    xuLyDonHang(maDon);
//                    Intent intent1 = new Intent(PaymentActivity.this, OderHistoryActivity.class);
//                    intent1.putExtra("MaKH", maKH);
//                    startActivity(intent1);
                }
            }
        });





    }

    private boolean checkKH() {
        if(khachHang.getHoten().compareTo("Chưa cài đặt")==0 || khachHang.getDiaChi().compareTo("Chưa cài đặt")==0){
            Toast.makeText(PaymentActivity.this, "Vui lòng cập nhật thông tin nhận hàng", Toast.LENGTH_SHORT).show();
            showEditCustomerDialog();
            return false;
        }
        return true;
    }
    private void xuLyDonHang(String maDonHang) {
        chiTietDonHangInnterface = ChiTietDonHangUtils.getChiTietDonHangService();
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("MaDonHang", maDonHang);
        chiTietDonHangInnterface.xulydon(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Nhận đon thành công " + response.message(), Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(PaymentActivity.this, "Nhận đơn thất bại " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void showEditCustomerDialog() {
        // Tạo dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_customer);

        // Liên kết các view
        EditText etHoTen = dialog.findViewById(R.id.etHoTen);
        EditText etDiaChi = dialog.findViewById(R.id.etDiaChi);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);

        // Xử lý sự kiện nút "Cập nhật"
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoTen = etHoTen.getText().toString().trim();
                String diaChi = etDiaChi.getText().toString().trim();

                if (hoTen.isEmpty() || diaChi.isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(PaymentActivity.this, "Cập nhật thành công: " + hoTen, Toast.LENGTH_SHORT).show();
                    updateCustomer(maKH,hoTen,diaChi);
                    dialog.dismiss(); // Đóng dialog sau khi cập nhật
                }
            }
        });

        // Hiển thị dialog
        dialog.show();
    }

    private void updateCustomer(String makh, String ten, String diaChi) {
        KhachHangInterface gioHangInterface = KhachHangUtils.getKhachHangService();
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("MaKH", makh);
        requestBody.addProperty("HoTen", ten);
        requestBody.addProperty("DiaChi", diaChi);

        gioHangInterface.capNhatKhachHang(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PaymentActivity.this, "Cạp nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
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
                    maDon = sanPhams.get(0).getMaDonHang();
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
                            String hoten = customer.getHoten() != null ? customer.getHoten() : "Chưa cài đặt";
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
