package com.example.eateasy.Fragments.Admin.BaoCaoThongKe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KhoHangFragment extends Fragment {
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    SanPhamInterface productsInterface;
    TextView tvTongQuan;
    TextView tvPhanTich;
    FrameLayout flContent;
    TextView tvGiaTriKho, tvSoLuong, tvSanPhamCon, tvSanPhamHet, tvChiTiet;
    NumberFormat numberFormat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kho_hang, container, false);
        //updateProductList();
        //khai bao
        numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);
        tvTongQuan = view.findViewById(R.id.tvTongQuan);
        tvPhanTich = view.findViewById(R.id.tvPhanTich);
        flContent = view.findViewById(R.id.flContent);

        updateProductList();
        //su kien clic
        tvTongQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(tvTongQuan);
                flContent.removeAllViews();
                View tongQuanView = getLayoutInflater().inflate(R.layout.layout_tong_quan, null);
                flContent.addView(tongQuanView);
                setOnClickListenersForTongQuan(inflater);
            }
        });

        tvPhanTich.setOnClickListener(v -> {
            // Hiển thị nội dung Phân tích
            setSelected(tvPhanTich);
            flContent.removeAllViews();
            View phanTichView = getLayoutInflater().inflate(R.layout.layout_phan_tich, null);
            flContent.addView(phanTichView);
        });
        setSelected(tvTongQuan);
        flContent.removeAllViews();
        View tongQuanView = getLayoutInflater().inflate(R.layout.layout_tong_quan, null);
        flContent.addView(tongQuanView);

        setOnClickListenersForTongQuan(inflater);
        return view;
    }
    private void updateProductList() {
        productsInterface = SanPhamUtils.getProdutsService();
        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
                    sanPhamList.clear();
                    sanPhamList.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickListenersForTongQuan(LayoutInflater inflater) {
        // Lấy các view từ layout đã được inflate vào flContent
        //updateProductList();
        tvGiaTriKho = flContent.findViewById(R.id.tvGiaTriKho);
        tvSoLuong = flContent.findViewById(R.id.tvSoLuong);
        tvSanPhamCon = flContent.findViewById(R.id.tvSanPhamCon);
        tvSanPhamHet = flContent.findViewById(R.id.tvSanPhamHet);
        tvChiTiet = flContent.findViewById(R.id.tvChiTiet);

        //giá trị kho
        double tongGiaTriKho = 0;
        int tongSoLuong = 0;
        int soSanPhamCon = 0;
        int soSanPhamHet = 0;

        for (SanPham sp : sanPhamList) {
            tongGiaTriKho += sp.getSoLuong() * sp.getGiaBan();
            tongSoLuong += sp.getSoLuong();
            if (sp.getSoLuong() > 0) soSanPhamCon++;
            if (sp.getSoLuong() == 0) soSanPhamHet++;
        }
        String formattedRevenue = numberFormat.format(tongGiaTriKho) + " VND";
        tvGiaTriKho.setText("Giá trị kho \n"+formattedRevenue);
        tvChiTiet = flContent.findViewById(R.id.tvChiTiet);
        tvChiTiet.setText("Thông tin chi tiết về Giá trị kho:\n- Tổng giá trị: "+formattedRevenue);
        tvSoLuong.setText("Số lượng\n" +tongSoLuong);
        tvSanPhamCon.setText("Sản phẩm còn\n" +soSanPhamCon);
        tvSanPhamHet.setText("Sản phẩm hết\n" +soSanPhamHet);
        if (tvChiTiet != null) {
            tvGiaTriKho.setOnClickListener(v1 -> {
                tvChiTiet.setText("Thông tin chi tiết về Giá trị kho:\n- Tổng giá trị: "+formattedRevenue);
            });

            int finalTongSoLuong = tongSoLuong;
            tvSoLuong.setOnClickListener(v1 -> {
                tvChiTiet.setText("Thông tin chi tiết về Số lượng:\n- Số lượng tổng: "+ finalTongSoLuong +" sản phẩm");
            });

            tvSanPhamCon.setOnClickListener(v1 -> {
                StringBuilder sanPhamConHang = new StringBuilder("Thông tin chi tiết về sản phẩm còn:\n");
                for (SanPham sp : sanPhamList) {
                    if (sp.getSoLuong() > 0) {
                        sanPhamConHang.append("- ")
                                .append(sp.getTenSP())
                                .append(": ")
                                .append(sp.getSoLuong())
                                .append("\n");
                    }
                }
                tvChiTiet.setText(sanPhamConHang.toString());
            });


            tvSanPhamHet.setOnClickListener(v1 -> {
                StringBuilder sanPhamHetHang = new StringBuilder("Thông tin chi tiết về sản phẩm hết:\n");
                for (SanPham sp : sanPhamList) {
                    if (sp.getSoLuong() == 0) {
                        sanPhamHetHang.append("- ")
                                .append(sp.getTenSP())
                                .append(": ")
                                .append(sp.getSoLuong())
                                .append("\n");
                    }
                }
                tvChiTiet.setText(sanPhamHetHang.toString());
            });

        } else {
            Log.e("KhoHangFragment", "tvChiTiet is null");
        }
    }
    private void setSelected(TextView selectedTextView) {
        // Đặt màu sắc cho TextView đã chọn
        selectedTextView.setTextColor(getResources().getColor(R.color.white)); // Ví dụ: Màu đã chọn
        selectedTextView.setBackgroundResource(R.drawable.backgroud_tv); // Ví dụ: Màu nền đã chọn

        // Đặt lại màu cho các TextView còn lại
        if (selectedTextView != tvTongQuan) {
            tvTongQuan.setTextColor(getResources().getColor(R.color.black));
            tvTongQuan.setBackgroundResource(R.drawable.grey_backgound);
        }
        if (selectedTextView != tvPhanTich) {
            tvPhanTich.setTextColor(getResources().getColor(R.color.black));
            tvPhanTich.setBackgroundResource(R.drawable.grey_backgound);
        }


    }

}