package com.example.eateasy.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eateasy.Model.HoaDonBan;
import com.example.eateasy.R;

import java.util.List;

public class HoaDonBanAdapter extends BaseAdapter {

    private Context context;
    private List<HoaDonBan> hoaDonBanList;

    public HoaDonBanAdapter(Context context, List<HoaDonBan> hoaDonBanList) {
        this.context = context;
        this.hoaDonBanList = hoaDonBanList;
    }

    @Override
    public int getCount() {
        return hoaDonBanList.size();
    }

    @Override
    public Object getItem(int position) {
        return hoaDonBanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_hoa_don_ban, parent, false);
        }

        // Lấy đối tượng HoaDonBan tại vị trí hiện tại
        HoaDonBan hoaDonBan = hoaDonBanList.get(position);

        // Gắn giá trị vào các TextView trong item layout
        TextView tvMaDH = convertView.findViewById(R.id.tvMaDH);
        TextView tvMaKH = convertView.findViewById(R.id.tvMaKH);
        TextView tvNgayLap = convertView.findViewById(R.id.tvNgayLap);
        TextView tvPhuongThucThanhToan = convertView.findViewById(R.id.tvPhuongThucThanhToan);
        TextView tvTongTien = convertView.findViewById(R.id.tvTongTien);
        TextView tvTrangThai = convertView.findViewById(R.id.tvTrangThai);

        tvMaDH.setText("Mã Đơn Hàng: " + hoaDonBan.getMaDH());
        tvMaKH.setText("Mã Khách Hàng: " + hoaDonBan.getMaKH());
        tvNgayLap.setText("Ngày Lập: " + hoaDonBan.getNgayLap());
        tvPhuongThucThanhToan.setText("Phương Thức Thanh Toán: " + hoaDonBan.getPhuongThucThanhToan());
        tvTongTien.setText("Tổng Tiền: " + hoaDonBan.getTongTien());
        tvTrangThai.setText("Trạng Thái: " + hoaDonBan.getTrangThai());

        return convertView;
    }
}
