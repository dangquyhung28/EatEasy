package com.example.eateasy.Fragments.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eateasy.R;

public class DonBanFragment extends Fragment {

    private EditText edtTenSanPham, edtGiaBan, edtSoLuongMua, edtGiamGia;
    private TableLayout tableProducts;
    private TextView txtTongTien;
    private Button btnAddProduct, btnCreateInvoice;
    private double totalAmount = 0;

    public DonBanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_ban, container, false);

        // Initialize views
        edtTenSanPham = view.findViewById(R.id.edtTenSanPham);
        edtGiaBan = view.findViewById(R.id.edtGiaBan);
        edtSoLuongMua = view.findViewById(R.id.edtSoLuongMua);
        edtGiamGia = view.findViewById(R.id.edtGiamGia);
        tableProducts = view.findViewById(R.id.tableProducts);
        txtTongTien = view.findViewById(R.id.txtTongTien);
        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        btnCreateInvoice = view.findViewById(R.id.btnCreateInvoice);

        // Set click listener for the "Add Product" button
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        // Set click listener for the "Create Invoice" button
        btnCreateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInvoice();
            }
        });

        return view;
    }

    // Function to add product to the table
    private void addProduct() {
        String productName = edtTenSanPham.getText().toString().trim();
        String productPriceStr = edtGiaBan.getText().toString().trim();
        String quantityStr = edtSoLuongMua.getText().toString().trim();
        String discountStr = edtGiamGia.getText().toString().trim();

        if (productName.isEmpty() || productPriceStr.isEmpty() || quantityStr.isEmpty() || discountStr.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        double productPrice = Double.parseDouble(productPriceStr);
        int quantity = Integer.parseInt(quantityStr);
        double discount = Double.parseDouble(discountStr);

        // Calculate the total price for this product
        double totalPrice = (productPrice * quantity) - (productPrice * quantity * discount / 100);

        // Create a new row for the product
        TableRow row = new TableRow(getActivity());
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        // Add product data to the row
        TextView productNameView = new TextView(getActivity());
        productNameView.setText(productName);
        row.addView(productNameView);

        TextView productPriceView = new TextView(getActivity());
        productPriceView.setText(String.format("%,.2f", productPrice));
        row.addView(productPriceView);

        TextView quantityView = new TextView(getActivity());
        quantityView.setText(String.valueOf(quantity));
        row.addView(quantityView);

        TextView discountView = new TextView(getActivity());
        discountView.setText(String.format("%.2f", discount));
        row.addView(discountView);

        TextView totalPriceView = new TextView(getActivity());
        totalPriceView.setText(String.format("%,.2f", totalPrice));
        row.addView(totalPriceView);

        // Add row to the table
        tableProducts.addView(row);

        // Update total amount
        totalAmount += totalPrice;
        txtTongTien.setText(String.format("Tổng tiền: %,.2f", totalAmount));

        // Clear input fields
        edtTenSanPham.setText("");
        edtGiaBan.setText("");
        edtSoLuongMua.setText("");
        edtGiamGia.setText("");
    }

    // Function to create invoice
    private void createInvoice() {
        if (totalAmount == 0) {
            Toast.makeText(getActivity(), "Chưa có sản phẩm nào để tạo hóa đơn", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would handle the creation of the invoice (e.g., saving it to the database or generating a PDF)
        Toast.makeText(getActivity(), "Hóa đơn đã được tạo!", Toast.LENGTH_SHORT).show();

        // Reset the total amount and clear the table
        totalAmount = 0;
        txtTongTien.setText("Tổng tiền: 0");
        tableProducts.removeAllViews();
    }
}