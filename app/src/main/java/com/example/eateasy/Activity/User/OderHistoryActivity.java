package com.example.eateasy.Activity.User;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.Adapter.Admin.HoaDonBanAdapter;
import com.example.eateasy.Adapter.User.LichSuDonHangAdapter;
import com.example.eateasy.Model.DonHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DonHangInterface;
import com.example.eateasy.Retrofit.Utils.DonHangUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OderHistoryActivity extends AppCompatActivity {

    ImageView backBtn_history;
    ListView order_list;
    String makh;
    private LichSuDonHangAdapter lichSuDonHangAdapter;
    private ArrayList<DonHang> donHangList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_oder_history);
        initWidget();
        Intent intent = getIntent();
        makh = intent.getStringExtra("maKH");
        donHangList = new ArrayList<>();
        loadDonHang(makh);
    }

    private void loadDonHang(String makh) {
        DonHangInterface donHangInterface = DonHangUtils.getHoaDOnBanService();
        donHangInterface.getDonHang(makh).enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                donHangList.clear();
                donHangList.addAll(response.body());
                lichSuDonHangAdapter = new LichSuDonHangAdapter(OderHistoryActivity.this, donHangList);
                order_list.setAdapter(lichSuDonHangAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });
    }

    private void initWidget() {
        backBtn_history = findViewById(R.id.backBtn_history);
        order_list = findViewById(R.id.order_list);
    }
}