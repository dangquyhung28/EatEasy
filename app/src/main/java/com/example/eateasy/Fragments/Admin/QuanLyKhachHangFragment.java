package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.Adapter.Admin.KhachHangAdapter;
import com.example.eateasy.Model.KhachHang;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.KhachHangInterface;
import com.example.eateasy.Retrofit.Utils.KhachHangUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuanLyKhachHangFragment extends Fragment {
    private RecyclerView recyclerView;
    private KhachHangAdapter khachHangAdapter;
    private ArrayList<KhachHang> khachHangList;
    private AutoCompleteTextView searchKhachHang;
    private ArrayList<KhachHang> originalKhachHang = new ArrayList<>();
    KhachHangInterface khachHangInterface;
    private ArrayAdapter<KhachHang> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_quan_ly_khach_hang, container, false);
        //
        recyclerView = view.findViewById(R.id.rvKhachHang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchKhachHang = view.findViewById(R.id.search_khachhang_admin);
        khachHangInterface = KhachHangUtils.getKhachHangService();
        khachHangList = new ArrayList<>();
        khachHangAdapter = new KhachHangAdapter(getContext(),khachHangList);
        recyclerView.setAdapter(khachHangAdapter);
        khachHangInterface.getAllKhachHang().enqueue(new Callback<ArrayList<KhachHang>>() {
            @Override
            public void onResponse(Call<ArrayList<KhachHang>> call, Response<ArrayList<KhachHang>> response) {
                khachHangList.clear();
                khachHangList.addAll(response.body());
                khachHangAdapter.notifyDataSetChanged();
                originalKhachHang.clear();
                originalKhachHang.addAll(response.body());
                setupAutoComplete(searchKhachHang, khachHangList);
            }

            @Override
            public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {

            }
        });


        return view;
    }
    private void setupAutoComplete(AutoCompleteTextView searchProduct, ArrayList<KhachHang> khachHangs) {
        ArrayList<String> productNames = new ArrayList<>();
        for (KhachHang kh : khachHangs) {
            productNames.add(kh.getSdt()); // Thêm tên sản phẩm vào danh sách gợi ý
        }

        ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, productNames);

        searchProduct.setAdapter(autoCompleteAdapter);

        // Xử lý khi người dùng chọn một sản phẩm từ gợi ý
        searchProduct.setOnItemClickListener((parent, view, position, id) -> {
            String selectedProductName = (String) parent.getItemAtPosition(position);
            filterProduct(selectedProductName);
        });

        // Lọc danh sách khi người dùng nhập từ khóa
        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProduct(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    private void filterProduct(String keyword) {
        khachHangList.clear(); // Xóa danh sách hiện tại

        for (KhachHang kh : originalKhachHang) { // Duyệt qua danh sách gốc
            if (kh.getSdt().toLowerCase().contains(keyword.toLowerCase())) {
                khachHangList.add(kh); // Thêm sản phẩm phù hợp vào danh sách
            }
        }

        khachHangAdapter.notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
    }

}