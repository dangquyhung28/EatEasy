package com.example.eateasy.Fragments.Admin.DonNhap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eateasy.Adapter.Admin.HoaDonNhapAdapter;
import com.example.eateasy.Model.HoaDonNhap;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.HoaDonNhapInterface;
import com.example.eateasy.Retrofit.Utils.HoaDonNhapUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyDonNhapFragment extends Fragment {

    private EditText searchBillAdmin;
    private TextView tvTotalProducts;
    private ListView listProducts;
    ArrayList<HoaDonNhap> hoaDonNhapArrayList;
    private HoaDonNhapAdapter hoaDonNhapAdapter;
    private FloatingActionButton fabAddHdNhap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_quan_ly_don_nhap, container, false);

        // Ánh xạ các thành phần giao diện
        searchBillAdmin = view.findViewById(R.id.search_bill_admin);
        tvTotalProducts = view.findViewById(R.id.tv_total_products);
        listProducts = view.findViewById(R.id.list_products);
        fabAddHdNhap = view.findViewById(R.id.fab_add_hdNhap);

        // Xử lý sự kiện FloatingActionButton
        fabAddHdNhap.setOnClickListener(v -> {
            // Chuyển sang Fragment Hóa Đơn Nhập
            DonNhapFragment hoaDonNhapFragment = new DonNhapFragment();
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout_admin, hoaDonNhapFragment) // `fragment_container` là ID của container hiện tại
                    .addToBackStack(null)
                    .commit();
        });

       hoaDonNhapArrayList = new ArrayList<>();

       loadHoaDon();

        return view;
    }

    private void loadHoaDon() {
        HoaDonNhapInterface hoaDonNhapInterface;
        hoaDonNhapInterface = HoaDonNhapUtils.getHoaDonNhap();
        hoaDonNhapInterface.getAllHoaDonNhap().enqueue(new Callback<ArrayList<HoaDonNhap>>() {
            @Override
            public void onResponse(Call<ArrayList<HoaDonNhap>> call, Response<ArrayList<HoaDonNhap>> response) {
                if(response.isSuccessful()){
                    hoaDonNhapArrayList.clear();
                    hoaDonNhapArrayList.addAll(response.body());
                    hoaDonNhapAdapter = new HoaDonNhapAdapter(getContext(),hoaDonNhapArrayList);
                    listProducts.setAdapter(hoaDonNhapAdapter);
                    tvTotalProducts.setText("Tổng số hóa đơn nhập: " + hoaDonNhapArrayList.size());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HoaDonNhap>> call, Throwable t) {

            }
        });
    }
}