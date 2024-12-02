package com.example.eateasy.Fragments.Admin.DonBan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.Activity.User.CartActivity;
import com.example.eateasy.Adapter.Admin.SanPhamDonHangAdapter;
import com.example.eateasy.Model.ChiTietDonHang;
import com.example.eateasy.Model.SanPhamDonHang;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.ChiTietDonHangInnterface;
import com.example.eateasy.Retrofit.Utils.ChiTietDonHangUtils;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonFragment extends Fragment {
    private TextView tvMaDonHang, tvTenKhachHang, tvDiaChiKhachHang, tvNgayTaoGioHang, tvSDTKhachHang;
    private RecyclerView rvSanPham;
    private Button btnNhanDon, btnHuyDon;
    ChiTietDonHang chiTietDonHang;

    private ChiTietDonHangInnterface chiTietDonHangInnterface;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_don, container, false);

        // Ánh xạ các view
        tvMaDonHang = view.findViewById(R.id.tvMaDonHang);
        tvTenKhachHang = view.findViewById(R.id.tvTenKhachHang);
        tvDiaChiKhachHang = view.findViewById(R.id.tvDiaChiKhachHang);
        tvNgayTaoGioHang = view.findViewById(R.id.tvNgayTaoGioHang);
        tvSDTKhachHang = view.findViewById(R.id.tvSDTKhachHang);
        rvSanPham = view.findViewById(R.id.rvSanPham);
        btnNhanDon = view.findViewById(R.id.btnNhanDon);
        btnHuyDon = view.findViewById(R.id.btnHuyDon);

        // Cấu hình RecyclerView
        rvSanPham.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy dữ liệu đơn hàng (giả sử mã đơn hàng được truyền qua arguments)
        String maDonHang = getArguments() != null ? getArguments().getString("MaDonHang") : null;
        if (maDonHang != null) {
            fetchChiTietDonHang(maDonHang);
        }

        // Xử lý sự kiện nút
        btnNhanDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDonHang = chiTietDonHang.getMaDonHang() ;  // Mã đơn hàng cần cập nhật
                nhanDonHang(maDonHang);
            }
        });
        return view;
    }

    private void nhanDonHang(String maDonHang) {
        chiTietDonHangInnterface = ChiTietDonHangUtils.getChiTietDonHangService();
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("MaDonHang", maDonHang);
        chiTietDonHangInnterface.nhanDon(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Nhận đon thành công " + response.message(), Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(), "Nhận đơn thất bại " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void fetchChiTietDonHang(String maDonHang) {
        chiTietDonHangInnterface = ChiTietDonHangUtils.getChiTietDonHangService();
        chiTietDonHangInnterface.getChiTietDonHang(maDonHang).enqueue(new Callback<ChiTietDonHang>() {
            @Override
            public void onResponse(Call<ChiTietDonHang> call, Response<ChiTietDonHang> response) {
                chiTietDonHang = response.body();
                bindDataToViews(chiTietDonHang);
            }

            @Override
            public void onFailure(Call<ChiTietDonHang> call, Throwable t) {

            }
        });

    }

    private void bindDataToViews(ChiTietDonHang chiTietDonHang) {
        // Hiển thị thông tin đơn hàng
        tvMaDonHang.setText("Mã đơn hàng: " + chiTietDonHang.getMaDonHang());
        tvTenKhachHang.setText("Tên khách hàng: " + chiTietDonHang.getTenKhachHang());
        tvDiaChiKhachHang.setText("Địa chỉ: " + chiTietDonHang.getDiaChiKhachHang());
        tvNgayTaoGioHang.setText("Ngày tạo: " + chiTietDonHang.getNgayTaoGioHang());
        tvSDTKhachHang.setText("SĐT: " + chiTietDonHang.getSdt());

        // Hiển thị danh sách sản phẩm trong RecyclerView
        ArrayList<SanPhamDonHang> sanPhamList = (ArrayList<SanPhamDonHang>) chiTietDonHang.getSanPham();
        rvSanPham.setAdapter(new SanPhamDonHangAdapter(sanPhamList));
    }
}