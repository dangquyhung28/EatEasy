package com.example.eateasy.Fragments.Admin.BaoCaoThongKe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.eateasy.R;

public class KhoHangFragment extends Fragment {

    TextView tvTongQuan;
    TextView tvPhanTich;
    FrameLayout flContent;
    TextView tvGiaTriKho, tvSoLuong, tvSanPhamCon, tvSanPhamHet, tvChiTiet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho_hang, container, false);
        //khai bao
        tvTongQuan = view.findViewById(R.id.tvTongQuan);
        tvPhanTich = view.findViewById(R.id.tvPhanTich);
        flContent = view.findViewById(R.id.flContent);


        //su kien clic
        tvTongQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flContent.removeAllViews();
                View tongQuanView = getLayoutInflater().inflate(R.layout.layout_tong_quan, null);
                flContent.addView(tongQuanView);

                setOnClickListenersForTongQuan(inflater);
            }
        });

        tvPhanTich.setOnClickListener(v -> {
            // Hiển thị nội dung Phân tích
            flContent.removeAllViews();
            View phanTichView = getLayoutInflater().inflate(R.layout.layout_phan_tich, null);
            flContent.addView(phanTichView);
        });
        return view;
    }

    private void setOnClickListenersForTongQuan(LayoutInflater inflater) {
        // Lấy các view từ layout đã được inflate vào flContent
        tvGiaTriKho = flContent.findViewById(R.id.tvGiaTriKho);
        tvSoLuong = flContent.findViewById(R.id.tvSoLuong);
        tvSanPhamCon = flContent.findViewById(R.id.tvSanPhamCon);
        tvSanPhamHet = flContent.findViewById(R.id.tvSanPhamHet);
        tvChiTiet = flContent.findViewById(R.id.tvChiTiet); // Lấy tvChiTiet sau khi layout đã được thêm vào
        tvChiTiet.setText("Thông tin chi tiết về Giá trị kho:\n- Tổng giá trị: 1,000,000 VNĐ\n...");

        // Kiểm tra nếu tvChiTiet là null trước khi sử dụng
        if (tvChiTiet != null) {
            // Sự kiện click cho các ô
            tvGiaTriKho.setOnClickListener(v1 -> {
                tvChiTiet.setText("Thông tin chi tiết về Giá trị kho:\n- Tổng giá trị: 1,000,000 VNĐ\n...");
            });

            tvSoLuong.setOnClickListener(v1 -> {
                tvChiTiet.setText("Thông tin chi tiết về Số lượng:\n- Số lượng tổng: 500 sản phẩm\n...");
            });

            tvSanPhamCon.setOnClickListener(v1 -> {
                tvChiTiet.setText("Thông tin chi tiết về Sản phẩm còn:\n- Danh sách sản phẩm còn...\n...");
            });

            tvSanPhamHet.setOnClickListener(v1 -> {
                tvChiTiet.setText("Thông tin chi tiết về Sản phẩm hết:\n- Danh sách sản phẩm hết...\n...");
            });
        } else {
            Log.e("KhoHangFragment", "tvChiTiet is null");
        }
    }

}