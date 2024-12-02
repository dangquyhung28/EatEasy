package com.example.eateasy.Fragments.Admin.SanPham;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eateasy.Adapter.Admin.SanPhamAdapter;
import com.example.eateasy.Model.DanhMuc;
import com.example.eateasy.Model.SanPham;
import com.example.eateasy.Network.FirebaseStorageHelper;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.DanhMucInterface;
import com.example.eateasy.Retrofit.Utils.DanhMucUtils;
import com.example.eateasy.Retrofit.Interface.SanPhamInterface;
import com.example.eateasy.Retrofit.Utils.SanPhamUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    SanPhamInterface productsInterface;
    RecyclerView recyclerView;
    TextView sl, tvTatCa , tvBanChay, tvSapHetHang, tvBanYeu;
    ImageView imgProductnew;
    SanPhamAdapter adapter;
    private HashMap<String, String> danhMucMap;
    ArrayList<SanPham> sanPhamList = new ArrayList<>();
    FloatingActionButton fabAddProduct;
    DanhMucInterface categoryInterface;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private ArrayList<SanPham> originalSanPhamList = new ArrayList<>();
    private String imgEdit;
    private AutoCompleteTextView searchProduct;
    int quantity = 0;
    TextView tvQuantity;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        fabAddProduct = view.findViewById(R.id.fab_add_Pro);
        productsInterface = SanPhamUtils.getProdutsService();
        sl = view.findViewById(R.id.tv_total_products);
        searchProduct = view.findViewById(R.id.search_product_admin);
        recyclerView = view.findViewById(R.id.recyclerView);
        tvTatCa = view.findViewById(R.id.tv_tat_ca);
        tvBanChay = view.findViewById(R.id.tv_ban_chay);
        tvSapHetHang = view.findViewById(R.id.tv_sap_het_hang);
        tvBanYeu = view.findViewById(R.id.tv_ban_yeu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SanPhamAdapter(getContext(), sanPhamList);
        recyclerView.setAdapter(adapter);
        categoryInterface = DanhMucUtils.getCategoryService();
        //load product
        updateProductList();
        tvTatCa.setOnClickListener(v -> {
            updateProductList();
        });

        tvBanChay.setOnClickListener(v -> {

        });

        tvSapHetHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sanPhamList.clear();
                for(SanPham sp : originalSanPhamList){

                    if(sp.getSoLuong() <= 20){
                        sanPhamList.add(sp);

                    }
                }
                Log.d("SanPhamList", "Tổng số sản phẩm: " + sanPhamList.size());
                Log.d("SanPhamList", "Tổng số sản phẩm: " + originalSanPhamList.size());
                sl.setText("Tổng số sản phẩm: "+sanPhamList.size());
                adapter.notifyDataSetChanged();
                setupAutoComplete(searchProduct, sanPhamList);
                Toast.makeText(getContext(), "Danh sách sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });

        tvBanYeu.setOnClickListener(v -> {

        });
        sl.setText(String.valueOf(sanPhamList.size()));
        fabAddProduct.setOnClickListener(v -> showAddProduct());

        return view;
    }

    private void updateProductList() {
        productsInterface.getAllSanPham().enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                if(response.isSuccessful()){
                    sanPhamList.clear();
                    sanPhamList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    sl.setText("Tổng số sản phẩm: "+sanPhamList.size());
                    originalSanPhamList.clear();
                    originalSanPhamList.addAll(response.body());
                    setupAutoComplete(searchProduct, sanPhamList);
                    Toast.makeText(getContext(), "Danh sách sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm thiết lập AutoComplete
    private void setupAutoComplete(AutoCompleteTextView searchProduct, ArrayList<SanPham> sanPhamList) {
        ArrayList<String> productNames = new ArrayList<>();
        for (SanPham product : sanPhamList) {
            productNames.add(product.getTenSP()); // Thêm tên sản phẩm vào danh sách gợi ý
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
        sanPhamList.clear(); // Xóa danh sách hiện tại

        for (SanPham product : originalSanPhamList) { // Duyệt qua danh sách gốc
            if (product.getTenSP().toLowerCase().contains(keyword.toLowerCase())) {
                sanPhamList.add(product); // Thêm sản phẩm phù hợp vào danh sách
            }
        }

        adapter.notifyDataSetChanged(); // Cập nhật giao diện RecyclerView
    }



    //edit product


    //add product
    private void showAddProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_dialog, null);
        builder.setView(dialogView);
        imgProductnew = dialogView.findViewById(R.id.ivAnhSanPham);
        EditText etTenSP = dialogView.findViewById(R.id.etTenSP);
        EditText etMoTa = dialogView.findViewById(R.id.etMoTa);
        EditText etGiaBan = dialogView.findViewById(R.id.etGiaBan);
        EditText etGiaNhap = dialogView.findViewById(R.id.etGiaNhap);
        Spinner spinnerMaDanhMuc = dialogView.findViewById(R.id.spinnerMaDanhMuc);
        Button btnIncrease, btnDecrease;
        Button btnChonAnh = dialogView.findViewById(R.id.btnChonAnh);
        tvQuantity = dialogView.findViewById(R.id.tvQuantity);
        btnIncrease = dialogView.findViewById(R.id.btnIncrease);
        btnDecrease = dialogView.findViewById(R.id.btnDecrease);

        //nut so luong
        // Sự kiện khi nhấn nút tăng
        btnIncrease.setOnClickListener(v -> {
            quantity++;
            updateQuantityDisplay();
        });

        // Sự kiện khi nhấn nút giảm
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                updateQuantityDisplay();
            }
        });

        // Xử lý nút chọn ảnh
        btnChonAnh.setOnClickListener(v -> openImagePicker());

        builder.setPositiveButton("Thêm", (dialog, which) -> {
            // Tạm thời bỏ trống, xử lý trong onClick button
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> {
                if (validateInputs(etTenSP, etMoTa, etGiaBan, etGiaNhap)) {
                    String tenSP = etTenSP.getText().toString().trim();
                    String moTa = etMoTa.getText().toString().trim();
                    float giaBan = Float.parseFloat(etGiaBan.getText().toString().trim());
                    float giaNhap = Float.parseFloat(etGiaNhap.getText().toString().trim());


                    if (imgEdit == null || imgEdit.isEmpty()) {
                        Toast.makeText(getContext(), "Đang tải ảnh lên, vui lòng chờ...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String tenDanhMuc = spinnerMaDanhMuc.getSelectedItem().toString();
                    String maDanhMuc = danhMucMap.get(tenDanhMuc);

                    if (maDanhMuc == null) {
                        Toast.makeText(getContext(), "Danh mục không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SanPham sanPham = new SanPham("", tenSP, moTa, giaBan, giaNhap, quantity, maDanhMuc, imgEdit);
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
    private void updateQuantityDisplay() {
        tvQuantity.setText(String.valueOf(quantity));
    }

    //up anh
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @javax.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData(); // URI của ảnh
            if (selectedImageUri != null) {
                uploadImageToFirebase(selectedImageUri);
            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        FirebaseStorageHelper storageHelper = new FirebaseStorageHelper();

        storageHelper.uploadImage(imageUri, new FirebaseStorageHelper.OnImageUploadCallback() {
            @Override
            public void onSuccess(String imageUrl) {
                Toast.makeText(getContext(), "Upload thành công!", Toast.LENGTH_SHORT).show();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Log.d("Firebase URL", imageUrl);  // Kiểm tra URL
                    imgEdit = imageUrl;
                    Glide.with(requireContext())
                            .load(imgEdit)
                            .placeholder(R.drawable.ic_product_management)  // Hình ảnh placeholder
                            .error(R.drawable.icon_infomation)  // Hình ảnh nếu có lỗi
                            .into(imgProductnew);
                } else {
                    Log.e("Error", "URL from Firebase is null or empty");
                    // Xử lý khi URL không hợp lệ
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(getContext(), "Lỗi: " + errorMessage, Toast.LENGTH_SHORT).show();
                Log.d("Lỗi: ",errorMessage);
            }
        });
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
                    Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
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
    private boolean validateInputs(EditText etTenSP, EditText etMoTa, EditText etGiaBan, EditText etGiaNhap) {
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
