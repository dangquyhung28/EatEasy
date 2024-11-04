package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.R;

public class DonBanFragment extends Fragment {
    private EditText edtSanPhamBan, edtSoLuongBan, edtDonGiaBan, edtGiamGiaBan;
    private TextView txtThanhTien, txtTongTien;
    private Button btnTaoHoaDon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_ban, container, false);

        edtSanPhamBan = view.findViewById(R.id.edtSPBan);
        edtSoLuongBan = view.findViewById(R.id.edtSoLuongBan);
        edtDonGiaBan = view.findViewById(R.id.edtDonGiaBan);
        edtGiamGiaBan = view.findViewById(R.id.edtGiamGiaBan);
        txtThanhTien = view.findViewById(R.id.txtThanhTien);
        txtTongTien = view.findViewById(R.id.txtTongTien);
        btnTaoHoaDon = view.findViewById(R.id.btnTaoHoaDon);

        // Khi số lượng hoặc đơn giá hoặc giảm giá thay đổi thì cập nhật lại tổng tiền và thành tiền
        btnTaoHoaDon.setOnClickListener(v -> {
            if (validateInputs()) {
                calculateTotal();
            } else {

            }
        });

        return view;
    }

    private boolean validateInputs() {
        return !edtSanPhamBan.getText().toString().isEmpty()
                && !edtSoLuongBan.getText().toString().isEmpty()
                && !edtDonGiaBan.getText().toString().isEmpty()
                && !edtGiamGiaBan.getText().toString().isEmpty();
    }

    private void calculateTotal() {
        try {
            int soLuong = Integer.parseInt(edtSoLuongBan.getText().toString());
            double donGia = Double.parseDouble(edtDonGiaBan.getText().toString());
            double giamGia = Double.parseDouble(edtGiamGiaBan.getText().toString());

            // Tính thành tiền trước khi giảm giá
            double thanhTien = soLuong * donGia;
            txtThanhTien.setText(String.valueOf(thanhTien));

            // Tính tổng tiền sau khi giảm giá
            double tongTien = thanhTien - (thanhTien * (giamGia / 100));
            txtTongTien.setText(String.valueOf(tongTien));

            Toast.makeText(getContext(), "Hóa đơn được tạo thành công", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Thông tin không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}