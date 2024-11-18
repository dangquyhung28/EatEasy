package com.example.eateasy.Fragments.Admin;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Adapter.Admin.SanPhamAdapter;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.CategoryInterface;
import com.example.eateasy.Retrofit.CategoryUtils;
import com.example.eateasy.Retrofit.ProductsInterface;
import com.example.eateasy.Retrofit.ProductsUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    ProductsInterface productsInterface;
    RecyclerView recyclerView;
    SanPhamAdapter adapter;
    private HashMap<String, String> danhMucMap;
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    FloatingActionButton fabAddProduct;
    CategoryInterface categoryInterface;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private EditText etAnhSanPham;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        fabAddProduct = view.findViewById(R.id.fab_add_HDB);
        productsInterface = ProductsUtils.getProdutsService();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SanPhamAdapter(getContext(), sanPhamList);
        recyclerView.setAdapter(adapter);
        categoryInterface = CategoryUtils.getCategoryService();
        //load product
        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
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
        fabAddProduct.setOnClickListener(v -> showAddProduct());

        return view;
    }

    //edit product


    //add product
    private void showAddProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_dialog, null);
        builder.setView(dialogView);

        EditText etTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText etMoTa = dialogView.findViewById(R.id.etMoTa);
        EditText etGiaBan = dialogView.findViewById(R.id.etGiaBan);
        EditText etGiaNhap = dialogView.findViewById(R.id.etGiaNhap);
        Spinner spinnerMaDanhMuc = dialogView.findViewById(R.id.spinnerMaDanhMuc);
        EditText etSoLuong = dialogView.findViewById(R.id.etSoLuong);
        EditText etAnhSanPham = dialogView.findViewById(R.id.etAnhSanPham);
        Button btnChonAnh = dialogView.findViewById(R.id.btnChonAnh);

        // Xử lý nút chọn ảnh
        btnChonAnh.setOnClickListener(v -> requestPermissionAndSelectImage());

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            // Tạm thời bỏ trống, xử lý trong onClick button
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (validateInputs(etTenSP, etMoTa, etGiaBan, etGiaNhap, etSoLuong)) {
                    String tenSP = etTenSP.getText().toString().trim();
                    String moTa = etMoTa.getText().toString().trim();
                    float giaBan = Float.parseFloat(etGiaBan.getText().toString().trim());
                    float giaNhap = Float.parseFloat(etGiaNhap.getText().toString().trim());
                    int soLuong = Integer.parseInt(etSoLuong.getText().toString().trim());
                    String anhSanPham = etAnhSanPham.getText().toString().trim();

                    String tenDanhMuc = spinnerMaDanhMuc.getSelectedItem().toString();
                    String maDanhMuc = danhMucMap.get(tenDanhMuc);

                    if (maDanhMuc == null) {
                        Toast.makeText(getContext(), "Danh mục không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SanPham sanPham = new SanPham("", tenSP, moTa, giaBan, giaNhap, soLuong, maDanhMuc, anhSanPham);
                    sanPhamList.add(sanPham);
                    adapter.notifyDataSetChanged();

                    Gson gson = new Gson();
                    String json = gson.toJson(sanPham);
                    Log.d("SanPhamJSON", json);

                    addProductToDatabase(sanPham);

                    dialog.dismiss();
                } else {
                    // Nếu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    // Không đóng dialog
                }
            });
        });

        dialog.show();
        dialog.setCancelable(false); // Ngăn không cho đóng bằng cách nhấn ngoài vùng dialog
        fetchDanhMucForSpinner(spinnerMaDanhMuc);
    }

    private void requestPermissionAndSelectImage() {
        // Kiểm tra quyền truy cập bộ nhớ
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else {
            // Khởi tạo Intent để chọn ảnh
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            // Thay getView() bằng requireView() để đảm bảo view đã được khởi tạo
            EditText etAnhSanPham = requireView().findViewById(R.id.etAnhSanPham);
            etAnhSanPham.setText(imageUri.toString());

            ImageView ivAnhSanPham = requireView().findViewById(R.id.ivAnhSanPham);
            Glide.with(getContext())  // Sử dụng Glide để tải ảnh
                    .load(imageUri)
                    .into(ivAnhSanPham);
        }
    }








    // Hàm riêng biệt để gọi API lấy danh mục và cập nhật Spinner
    private void fetchDanhMucForSpinner(Spinner spinnerMaDanhMuc) {
        categoryInterface.getAllDanhMuc().enqueue(new Callback<ArrayList<DanhMuc>>() {
            @Override
            public void onResponse(Call<ArrayList<DanhMuc>> call, Response<ArrayList<DanhMuc>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DanhMuc> danhMucList = response.body();
                    if (danhMucList != null) {
                        ArrayList<String> danhMucNames = new ArrayList<>();
                        danhMucMap = new HashMap<>(); // Map tên danh mục với mã danh mục

                        for (DanhMuc danhMuc : danhMucList) {
                            danhMucNames.add(danhMuc.getTenDanhMuc());
                            danhMucMap.put(danhMuc.getTenDanhMuc(), danhMuc.getMaDanhMuc()); // Lưu MaDanhMuc với TenDanhMuc
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, danhMucNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerMaDanhMuc.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DanhMuc>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //add sanpham vao database
    private void addProductToDatabase(SanPham sanPham) {
         //Tạo API Interface và gửi yêu cầu
        productsInterface.addProduct(sanPham).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Sản phẩm đã được thêm vào database!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //check valid
    private boolean validateInputs(EditText etTenSP, EditText etMoTa, EditText etGiaBan, EditText etGiaNhap, EditText etSoLuong) {
        boolean isValid = true;

        // Kiểm tra trường tên sản phẩm
        if (etTenSP.getText().toString().trim().isEmpty()) {
            etTenSP.setError("Bạn phải nhập tên sản phẩm");
            isValid = false;
        }

        // Kiểm tra trường mô tả
        if (etMoTa.getText().toString().trim().isEmpty()) {
            etMoTa.setError("Bạn phải nhập mô tả");
            isValid = false;
        }

        // Kiểm tra trường giá bán
        String giaBanText = etGiaBan.getText().toString().trim();
        if (giaBanText.isEmpty()) {
            etGiaBan.setError("Bạn phải nhập giá bán");
            isValid = false;
        } else {
            try {
                float giaBan = Float.parseFloat(giaBanText);
                if (giaBan <= 0) {
                    etGiaBan.setError("Giá bán phải là số dương");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etGiaBan.setError("Giá bán phải là số hợp lệ");
                isValid = false;
            }
        }

        // Kiểm tra trường giá nhập
        String giaNhapText = etGiaNhap.getText().toString().trim();
        if (giaNhapText.isEmpty()) {
            etGiaNhap.setError("Bạn phải nhập giá nhập");
            isValid = false;
        } else {
            try {
                float giaNhap = Float.parseFloat(giaNhapText);
                if (giaNhap <= 0) {
                    etGiaNhap.setError("Giá nhập phải là số dương");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etGiaNhap.setError("Giá nhập phải là số hợp lệ");
                isValid = false;
            }
        }

        // Kiểm tra trường số lượng
        String soLuongText = etSoLuong.getText().toString().trim();
        if (soLuongText.isEmpty()) {
            etSoLuong.setError("Bạn phải nhập số lượng");
            isValid = false;
        } else {
            try {
                int soLuong = Integer.parseInt(soLuongText);
                if (soLuong <= 0) {
                    etSoLuong.setError("Số lượng phải lớn hơn 0 và là số nguyên");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                etSoLuong.setError("Số lượng phải là số nguyên hợp lệ");
                isValid = false;
            }
        }

        return isValid;
    }




    //select img


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


}
