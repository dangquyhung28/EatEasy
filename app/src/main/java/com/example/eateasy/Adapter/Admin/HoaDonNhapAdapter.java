package com.example.eateasy.Adapter.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.eateasy.R;

import com.example.eateasy.Model.HoaDonNhap;

import java.util.List;

public class HoaDonNhapAdapter extends BaseAdapter {

    private Context context;
    private List<HoaDonNhap> hoaDonNhapList;
    private LayoutInflater inflater;

    public HoaDonNhapAdapter(Context context, List<HoaDonNhap> hoaDonNhapList) {
        this.context = context;
        this.hoaDonNhapList = hoaDonNhapList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return hoaDonNhapList.size();
    }

    @Override
    public Object getItem(int position) {
        return hoaDonNhapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tạo view nếu chưa có
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_hoa_don_nhap, parent, false);
        }

        // Lấy thông tin từ đối tượng HoaDonNhap
        HoaDonNhap hoaDonNhap = hoaDonNhapList.get(position);

        // Gắn dữ liệu vào các TextView
        TextView textViewMaHDN = convertView.findViewById(R.id.textViewMaHDN);
        TextView textViewMaNCC = convertView.findViewById(R.id.textViewMaNCC);
        TextView textViewNgayLap = convertView.findViewById(R.id.textViewNgayLap);
        TextView textViewTongTien = convertView.findViewById(R.id.textViewTongTien);

        textViewMaHDN.setText(hoaDonNhap.getMaHDN());
        textViewMaNCC.setText(hoaDonNhap.getMaNCC());
        textViewNgayLap.setText(hoaDonNhap.getNgayLap());
        textViewTongTien.setText(String.valueOf(hoaDonNhap.getTongTien()));

        return convertView;
    }
}
