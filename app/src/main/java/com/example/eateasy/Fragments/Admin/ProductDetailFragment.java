package com.example.eateasy.Fragments.Admin;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.CategoryInterface;
import com.example.eateasy.Retrofit.CategoryUtils;
import com.example.eateasy.Retrofit.ProductsInterface;
import com.example.eateasy.Retrofit.ProductsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    private ImageView productImage, closeButton, iconEdit, iconDelete, imgProduct;
    private TextView tenSPTextView, giaBanTextView, giaNhapTextView, moTaTextView, soLuongTextView, maDanhMucTextView;
    private HashMap<String, String> danhMucMap = new HashMap<>();
    private ArrayList<String> danhMucNames = new ArrayList<>();
    private CategoryInterface categoryInterface;
    private String maSP;
    private Uri productImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        // Ánh xạ các thành phần giao diện
        categoryInterface = CategoryUtils.getCategoryService();
        productImage = view.findViewById(R.id.product_image);
        closeButton = view.findViewById(R.id.btnBack);
        iconEdit = view.findViewById(R.id.iconEdit);
        iconDelete = view.findViewById(R.id.iconDelete);
        tenSPTextView = view.findViewById(R.id.tenSPTextView);
        giaBanTextView = view.findViewById(R.id.giaBanTextView);
        giaNhapTextView = view.findViewById(R.id.giaNhapTextView);
        moTaTextView = view.findViewById(R.id.moTaTextView);
        soLuongTextView = view.findViewById(R.id.soLuongTextView);
        maDanhMucTextView = view.findViewById(R.id.maDanhMucTextView);

        // Nhận dữ liệu từ Bundle
        if (getArguments() != null) {
            // Lấy dữ liệu từ bundle
            maSP = getArguments().getString("maSP", "Chưa có tên sản phẩm");
            String tenSP = getArguments().getString("TenSP", "Chưa có tên sản phẩm");
            float giaBan = getArguments().getFloat("GiaBan", 0);
            float giaNhap = getArguments().getFloat("GiaNhap", 0);
            String moTa = getArguments().getString("MoTa", "Chưa có mô tả");
            int soLuong = getArguments().getInt("SoLuong", 0);
            String maDanhMuc = getArguments().getString("MaDanhMuc", "Chưa có mã danh mục");
            String productImageUrl = getArguments().getString("AnhSanPham");

            if (productImageUrl == null) {
                productImageUrl = "android.resource://com.example.eateasy/drawable/ic_product_management.png";
            }

            productImageUri = Uri.parse(productImageUrl);

            // Hiển thị dữ liệu lên giao diện
            tenSPTextView.setText(tenSP);
            giaBanTextView.setText(String.valueOf(giaBan));
            giaNhapTextView.setText(String.valueOf(giaNhap));
            moTaTextView.setText(moTa);
            soLuongTextView.setText(String.valueOf(soLuong));
            fetchDanhMucForSpinner();
            maDanhMucTextView.setText(maDanhMuc);

            // Sử dụng Glide để tải ảnh sản phẩm
            Glide.with(requireContext())
                    .load(productImageUrl)
                    .placeholder(R.drawable.ic_product_management)
                    .error(R.drawable.icon_infomation)
                    .into(productImage);
        }

        // Xử lý nút Close
        closeButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Xử lý nút Edit
        iconEdit.setOnClickListener(v -> showEditDialog());

        // Xử lý nút Delete
        iconDelete.setOnClickListener(v -> {
            deleteProduct();
        });

        return view;
    }

    private void showEditDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater1 = getLayoutInflater();
        View dialogView = inflater1.inflate(R.layout.dialog_edit_product, null);
        builder.setView(dialogView);

        // Ánh xạ view trong dialog
        EditText editTenSP = dialogView.findViewById(R.id.editTenSP);
        EditText editGiaBan = dialogView.findViewById(R.id.editGiaBan);
        EditText editGiaNhap = dialogView.findViewById(R.id.editGiaNhap);
        EditText editMoTa = dialogView.findViewById(R.id.editMoTa);
        EditText editSoLuong = dialogView.findViewById(R.id.editSoLuong);
        Spinner spinnerMaDanhMuc = dialogView.findViewById(R.id.editMaDanhMuc);
        Button btnSave = dialogView.findViewById(R.id.btnSave);
        Button btnClose = dialogView.findViewById(R.id.btnCancel);
        imgProduct = dialogView.findViewById(R.id.imgProduct);
        Button btnEditImage = dialogView.findViewById(R.id.btnEditImage);

        // Hiển thị dữ liệu hiện tại
        editTenSP.setText(tenSPTextView.getText());
        editGiaBan.setText(giaBanTextView.getText());
        editGiaNhap.setText(giaNhapTextView.getText());
        editMoTa.setText(moTaTextView.getText());
        editSoLuong.setText(soLuongTextView.getText());

        // Nạp dữ liệu danh mục vào spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, danhMucNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaDanhMuc.setAdapter(adapter);

        // Load ảnh sản phẩm
        Glide.with(requireContext())
                .load(productImageUri)
                .placeholder(R.drawable.ic_product_management)
                .error(R.drawable.icon_infomation)
                .into(imgProduct);

        // Hiển thị dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Xử lý các nút trong dialog
        btnEditImage.setOnClickListener(v -> openImagePicker());
        btnSave.setOnClickListener(v -> {
            // Cập nhật dữ liệu...
            alertDialog.dismiss();
        });
        btnClose.setOnClickListener(v -> alertDialog.dismiss());
    }

    private void deleteProduct() {
        // Thêm logic xóa sản phẩm tại đây
        Toast.makeText(requireContext(), "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                productImageUri = selectedImageUri;
                Glide.with(requireContext())
                        .load(productImageUri)
                        .placeholder(R.drawable.ic_product_management)
                        .error(R.drawable.icon_infomation)
                        .into(imgProduct);
            }
        }
    }



    private void fetchDanhMucForSpinner() {
        categoryInterface.getAllDanhMuc().enqueue(new Callback<ArrayList<DanhMuc>>() {
            @Override
            public void onResponse(Call<ArrayList<DanhMuc>> call, Response<ArrayList<DanhMuc>> response) {
                if (response.isSuccessful()) {
                    ArrayList<DanhMuc> danhMucList = response.body();
                    if (danhMucList != null) {
                        for (DanhMuc danhMuc : danhMucList) {
                            danhMucNames.add(danhMuc.getTenDanhMuc());
                            danhMucMap.put(danhMuc.getTenDanhMuc(), danhMuc.getMaDanhMuc()); // Lưu MaDanhMuc với TenDanhMuc
                        }
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
    //update
    private void updateProduct(SanPham updatedProduct) {
        ProductsInterface productsService = ProductsUtils.getProdutsService();

        // Chỉ gửi các thông tin mới, không cần cập nhật MaSP
        // MaSP đã có sẵn trong đối tượng `updatedProduct`

        Call<ResponseBody> call = productsService.updateProduct(updatedProduct);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Cập nhật thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}