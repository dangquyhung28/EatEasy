package com.example.eateasy.Fragments.Admin;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eateasy.Adapter.Admin.SanPhamAdapter;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.ProductsInterface;
import com.example.eateasy.Retrofit.ProductsUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    ProductsInterface productsInterface;
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    FloatingActionButton fabAddProduct;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private EditText etAnhSanPham;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        fabAddProduct = view.findViewById(R.id.fab_add_product);
        productsInterface = ProductsUtils.getProdutsService();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SanPhamAdapter(getContext(), sanPhamList);
        recyclerView.setAdapter(adapter);
        //load product
        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
                    ArrayList<SanPham> kq = response.body();
                    sanPhamList.clear();
                    sanPhamList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Dữ liệu được cập nhật!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //add product
        fabAddProduct.setOnClickListener(v -> showAddProduct());
        //return view
        return view;
    }

    //function add product
    private void showAddProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_dialog, null);
        builder.setView(dialogView);

        EditText etTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText etMoTa = dialogView.findViewById(R.id.etMoTa);
        EditText etGiaBan = dialogView.findViewById(R.id.etGiaBan);
        EditText etGiaNhap = dialogView.findViewById(R.id.etGiaNhap);
        Spinner etMaDanhMuc = dialogView.findViewById(R.id.spinnerMaDanhMuc);
        EditText etSoLuong = dialogView.findViewById(R.id.etSoLuong);
        etAnhSanPham = dialogView.findViewById(R.id.etAnhSanPham);
        Button btnChonAnh = dialogView.findViewById(R.id.btnChonAnh);
        btnChonAnh.setOnClickListener(v -> requestPermissionAndSelectImage());

        builder.setTitle("Thêm Sản Phẩm");
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            // Lấy dữ liệu từ các EditText
            String tenSP = etTenSP.getText().toString().trim();
            String moTa = etMoTa.getText().toString().trim();
            float giaBan = Float.parseFloat(etGiaBan.getText().toString().trim());
            float giaNhap = Float.parseFloat(etGiaNhap.getText().toString().trim());
            //String maDanhMuc = etMaDanhMuc.getText().toString().trim();
            int soLuong = Integer.parseInt(etSoLuong.getText().toString().trim());
            String anhSanPham = etAnhSanPham.getText().toString().trim();

            // Tạo đối tượng sản phẩm mới
            SanPham sanPham = new SanPham();
            sanPham.setTenSP(tenSP);
            sanPham.setMoTa(moTa);
            sanPham.setGiaBan(giaBan);
            sanPham.setGiaNhap(giaNhap);
            //sanPham.setMaDanhMuc(maDanhMuc);
            sanPham.setSoLuong(soLuong);
            sanPham.setAnhSanPham(anhSanPham);

            // Thực hiện thêm sản phẩm vào database hoặc danh sách (tùy theo cách bạn muốn xử lý)
            //addProductToDatabase(sanPham);

            Toast.makeText(getContext(), "Sản phẩm đã được thêm!", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //select img

    private void requestPermissionAndSelectImage() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
        } else {
            selectImage();
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(getContext(), "Bạn cần cấp quyền để chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Lưu hoặc hiển thị URI của ảnh trong EditText anhSanPham
                etAnhSanPham.setText(selectedImageUri.toString());
            }
        }
    }
}
