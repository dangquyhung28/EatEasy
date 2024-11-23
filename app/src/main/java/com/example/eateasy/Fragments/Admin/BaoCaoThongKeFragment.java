package com.example.eateasy.Fragments.Admin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.R;

import java.util.Calendar;

import javax.annotation.Nullable;


public class BaoCaoThongKeFragment extends Fragment {

    private TextView tvNgay, tvThang, tvNam, tvDoanhThu;
    private Button btnChonNgay, btnChonThang, btnChonNam, btnXemDoanhThu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bao_cao_thong_ke, container, false);

        // Ánh xạ các view
        tvNgay = rootView.findViewById(R.id.tvNgay);
        tvThang = rootView.findViewById(R.id.tvThang);
        tvNam = rootView.findViewById(R.id.tvNam);
        tvDoanhThu = rootView.findViewById(R.id.tvDoanhThu);
        btnChonNgay = rootView.findViewById(R.id.btnChonNgay);
        btnChonThang = rootView.findViewById(R.id.btnChonThang);
        btnChonNam = rootView.findViewById(R.id.btnChonNam);
        btnXemDoanhThu = rootView.findViewById(R.id.btnXemDoanhThu);

        // Chọn Ngày
        btnChonNgay.setOnClickListener(v -> openDatePicker("ngay"));

        // Chọn Tháng
        btnChonThang.setOnClickListener(v -> openDatePicker("thang"));

        // Chọn Năm
        btnChonNam.setOnClickListener(v -> openDatePicker("nam"));

        // Xem Doanh Thu
        btnXemDoanhThu.setOnClickListener(v -> {
            // Hiển thị dữ liệu mẫu (thay bằng API thực tế nếu cần)
            String ngay = tvNgay.getText().toString();
            String thang = tvThang.getText().toString();
            String nam = tvNam.getText().toString();
            tvDoanhThu.setText(String.format("Doanh thu:\nNgày: %s\nTháng: %s\nNăm: %s\nTổng: %s VNĐ",
                    ngay.isEmpty() ? "Chưa chọn" : ngay,
                    thang.isEmpty() ? "Chưa chọn" : thang,
                    nam.isEmpty() ? "Chưa chọn" : nam,
                    "1.000.000")); // Ví dụ doanh thu mẫu
        });

        return rootView;
    }

    // Mở DatePicker cho ngày/tháng/năm
    private void openDatePicker(String type) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            switch (type) {
                case "ngay":
                    tvNgay.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year));
                    break;
                case "thang":
                    tvThang.setText(String.format("%02d/%04d", month + 1, year));
                    break;
                case "nam":
                    tvNam.setText(String.format("%04d", year));
                    break;
            }
        };

        if (type.equals("ngay")) {
            new DatePickerDialog(requireContext(), listener, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (type.equals("thang")) {
            new DatePickerDialog(requireContext(), (view, year, month, day) -> listener.onDateSet(view, year, month, 1),
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1).show();
        } else {
            new DatePickerDialog(requireContext(), (view, year, month, day) -> listener.onDateSet(view, year, 0, 1),
                    calendar.get(Calendar.YEAR), 0, 1).show();
        }
    }
}