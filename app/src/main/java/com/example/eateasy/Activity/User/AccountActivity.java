package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.Activity.Login_Activity;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    ImageView backBtn_account, profile_image;
    TextView user_name, phone_number, email, address;
    Button btn_edit_profile, btn_change_password, btn_logout;
    String maKH;
    KhachHang khachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);


        initWidgets();

        Intent intent = getIntent();
        maKH = intent.getStringExtra("maKH");

        fetchAndCheckCustomer(maKH);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AccountActivity.this, Login_Activity.class);
                startActivity(intent1);
            }
        });

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AccountActivity.this, OderHistoryActivity.class);
                intent1.putExtra("maKH", maKH);
                startActivity(intent1);
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
                            user_name.setText(hoten);
                            phone_number.setText(khachHang.getSdt());
                            email.setText(khachHang.getSdt());
                            address.setText(diaChi);
                            break;
                        }
                    }
                } else {
                    //Toast.makeText(PaymentActivity.this, "Không thể tải danh sách khách hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {
                Toast.makeText(AccountActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initWidgets() {
        backBtn_account = findViewById(R.id.backBtn_account);
        profile_image = findViewById(R.id.profile_image);
        user_name = findViewById(R.id.user_name);
        phone_number = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_change_password = findViewById(R.id.btn_change_password);
        btn_logout = findViewById(R.id.btn_logout);
    }
}
