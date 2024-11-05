package com.example.eateasy.Activity.User;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.R;

public class AccountActivity extends AppCompatActivity {
    ImageView backBtn_account, profile_image;
    TextView user_name, phone_number, email, address;
    Button btn_edit_profile, btn_change_password, btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initWidgets();
    }

    private void initWidgets() {
        backBtn_account = findViewById(R.id.backBtn_account);
        profile_image = findViewById(R.id.profile_image);
        user_name = findViewById(R.id.user_name);
        phone_number = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_change_password = findViewById(R.id.btn_change_password);
        btn_logout = findViewById(R.id.btn_logout);
    }
}
