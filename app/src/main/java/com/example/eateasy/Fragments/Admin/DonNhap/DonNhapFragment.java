package com.example.eateasy.Fragments.Admin.DonNhap;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.Model.NhaCungCap;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.NccInterface;
import com.example.eateasy.Retrofit.Utils.NccUtils;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;

import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonNhapFragment extends Fragment {

    private TableLayout tableSanPham;
    private TextView tvTongTien;
    private float tongTien = 0;
    SanPhamInterface productsInterface;
    NccInterface nccInterfacel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_nhap, container, false);

        EditText etMaNV = view.findViewById(R.id.etMaNV);
        Spinner spMaNCC = view.findViewById(R.id.spMaNCC);
        EditText etNgayLap = view.findViewById(R.id.etNgayLap);
        Button btnThemSanPham = view.findViewById(R.id.btnThemSanPham);
        Button btnGuiHoaDon = view.findViewById(R.id.btnGuiHoaDon);
        tableSanPham = view.findViewById(R.id.tableSanPham);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        productsInterface = SanPhamUtils.getProdutsService();
        nccInterfacel = NccUtils.getNccService();

        ArrayList<NhaCungCap> nhaCungCaps = new ArrayList<>();
        ArrayAdapter<NhaCungCap> spinnerAdapterNcc = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, nhaCungCaps);
        spinnerAdapterNcc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMaNCC.setAdapter(spinnerAdapterNcc);
        nccInterfacel.getALLNcc().enqueue(new Callback<ArrayList<NhaCungCap>>() {
            @Override
            public void onResponse(Call<ArrayList<NhaCungCap>> call, Response<ArrayList<NhaCungCap>> response) {
                nhaCungCaps.clear();
                nhaCungCaps.addAll(response.body());
                spinnerAdapterNcc.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<NhaCungCap>> call, Throwable t) {
                Toast.makeText(getContext(), "Không tải được danh sách NCC", Toast.LENGTH_SHORT).show();
            }
        });


        // Thiết lập chọn ngày
        etNgayLap.setOnClickListener(v -> showDatePickerDialog(etNgayLap));

        // Sự kiện khi bấm nút thêm sản phẩm
        btnThemSanPham.setOnClickListener(v -> showAddProductDialog());

        // Sự kiện khi bấm nút tạo hóa đơn
        btnGuiHoaDon.setOnClickListener(v -> {
            // Gửi hóa đơn xử lý ở đây
            Toast.makeText(requireContext(), "Hóa đơn đã được tạo thành công!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    // Hiển thị Dialog thêm sản phẩm
    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Thêm sản phẩm");

        // Inflate layout của dialog
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        Spinner spTenSP = dialogView.findViewById(R.id.spTenSP);
        TextView tvGiaNhap = dialogView.findViewById(R.id.tvGiaNhap);
        EditText etSoLuong = dialogView.findViewById(R.id.etSoLuong);
        EditText etGiamGia = dialogView.findViewById(R.id.etGiamGia);
        Button btnAddProduct = dialogView.findViewById(R.id.btnAddProduct);

        ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();
        ArrayAdapter<SanPham> spinnerAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, sanPhamArrayList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTenSP.setAdapter(spinnerAdapter);

        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sanPhamArrayList.clear();
                    sanPhamArrayList.addAll(response.body());
                    spinnerAdapter.notifyDataSetChanged(); // Thông báo dữ liệu đã thay đổi
                } else {
                    Toast.makeText(getContext(), "Không tải được danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        spTenSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SanPham selectedProduct = (SanPham) parent.getItemAtPosition(position);
                tvGiaNhap.setText(String.format("Giá nhập: %.2f VNĐ", selectedProduct.getGiaNhap()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tvGiaNhap.setText("");
            }
        });

        AlertDialog dialog = builder.create();

        btnAddProduct.setOnClickListener(v -> {
            SanPham selectedProduct = (SanPham) spTenSP.getSelectedItem();
            String soLuongStr = etSoLuong.getText().toString().trim();
            String giamGiaStr = etGiamGia.getText().toString().trim();

            if (soLuongStr.isEmpty() || giamGiaStr.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int soLuong = Integer.parseInt(soLuongStr);
            float giamGia = Float.parseFloat(giamGiaStr);
            float thanhTien = (selectedProduct.getGiaNhap() * soLuong) - giamGia;

            TableRow row = new TableRow(requireContext());
            row.addView(createTextView(selectedProduct.getTenSP()));
            row.addView(createTextView(String.format("%.2f", selectedProduct.getGiaNhap())));
            row.addView(createTextView(String.format("%.2f", giamGia)));
            row.addView(createTextView(String.format("%.2f", thanhTien)));
            tableSanPham.addView(row);

            tongTien += thanhTien; // Cập nhật tổng tiền
            tvTongTien.setText(String.format("Tổng tiền: %.2f VNĐ", tongTien));

            dialog.dismiss();
        });

        dialog.show();
    }

    // Tạo TextView trong bảng
    private TextView createTextView(String text) {
        TextView textView = new TextView(requireContext());
        textView.setText(text);
        textView.setPadding(8, 8, 8, 8);
        return textView;
    }

    // Dữ liệu giả lập danh sách nhà cung cấp
    private ArrayList<String> getDanhSachNCC() {
        ArrayList<String> nccList = new ArrayList<>();
        nccList.add("NCC01");
        nccList.add("NCC02");
        nccList.add("NCC03");
        return nccList;
    }

    // Dữ liệu giả lập danh sách sản phẩm
    private ArrayList<SanPham> getDanhSachSanPham() {
        ArrayList<SanPham> sanPhamList = new ArrayList<>();

        return sanPhamList;
    }

    // Hiển thị DatePickerDialog
    private void showDatePickerDialog(EditText etNgayLap) {
        // Lấy ngày hiện tại để hiển thị trong DatePicker
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    etNgayLap.setText(date); // Gán giá trị ngày vào EditText
                }, year, month, day);

        datePickerDialog.show(); // Hiển thị dialog
    }

}