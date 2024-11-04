package com.example.eateasy.Activity.User;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eateasy.R;

public class PaymentActivity extends AppCompatActivity {
    ImageView backBtn_pay;
    TextView tvTen, tvSdt, tvAddress, tvTongSoSP, pay_total;
    RecyclerView rcvOrder;
    RadioButton payment_cod, payment_credit_card, payment_e_wallet;
    Button btnDatHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initWidgets();
    }
    private void initWidgets() {
        backBtn_pay = findViewById(R.id.backBtn_history);
        tvTen = findViewById(R.id.tvTen);
        tvSdt = findViewById(R.id.tvSdt);
        tvAddress = findViewById(R.id.tvAddress);
        tvTongSoSP = findViewById(R.id.tvTongSoSP);
        pay_total = findViewById(R.id.pay_total);
        rcvOrder = findViewById(R.id.rcvOrder);
        payment_cod = findViewById(R.id.payment_cod);
        payment_credit_card = findViewById(R.id.payment_credit_card);
        payment_e_wallet = findViewById(R.id.payment_e_wallet);
        btnDatHang = findViewById(R.id.btnDatHang);
    }
}