package com.example.eateasy.Adapter.Admin;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eateasy.Fragments.Admin.DonBan.ChiTietDonFragment;
import com.example.eateasy.Model.DonHang;
import com.example.eateasy.R;

import java.util.List;

public class DonXyLyAdapter extends BaseAdapter {

    private Context context;
    private List<DonHang> donHangList;

    public DonXyLyAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    @Override
    public int getCount() {
        return donHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return donHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_donhang_xuly, parent, false);
        }

        // Lấy đối tượng HoaDonBan tại vị trí hiện tại
        DonHang donHang = donHangList.get(position);

        // Gắn giá trị vào các TextView trong item layout
        TextView tvMaDH = convertView.findViewById(R.id.tvMaDH);
        TextView tvMaKH = convertView.findViewById(R.id.tvMaKH);
        TextView tvNgayLap = convertView.findViewById(R.id.tvNgayLap);
        TextView tvPhuongThucThanhToan = convertView.findViewById(R.id.tvPhuongThucThanhToan);
        TextView tvTongTien = convertView.findViewById(R.id.tvTongTien);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangThai);
        TextView tvChiTiet = convertView.findViewById(R.id.tv_today_detail);

        tvMaDH.setText("Mã Đơn Hàng: " + donHang.getMaDH());
        tvMaKH.setText("Mã Khách Hàng: " + donHang.getMaKH());
        tvNgayLap.setText("Ngày Lập: " + donHang.getNgayLap());
        tvPhuongThucThanhToan.setText("Phương Thức Thanh Toán: " + donHang.getPhuongThucThanhToan());
        tvTongTien.setText("Tổng Tiền: " + donHang.getTongTien());
        tvTrangThai.setText("Trạng Thái: " + donHang.getTrangThai());

        tvChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChiTietDonFragment chiTietDonFragment = new ChiTietDonFragment();

                // Truyền mã đơn hàng vào fragment
                Bundle bundle = new Bundle();
                bundle.putString("MaDonHang", donHang.getMaDH()); // Truyền mã đơn hàng
                chiTietDonFragment.setArguments(bundle);

                // Thực hiện thay đổi fragment
                if (context instanceof AppCompatActivity) {
                    AppCompatActivity activity = (AppCompatActivity) context;
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout_admin, chiTietDonFragment) // R.id.fragment_container là ID của container chứa fragment
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return convertView;
    }
}

