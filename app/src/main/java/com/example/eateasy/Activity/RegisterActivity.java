package com.example.eateasy.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView buttonBack, buttonFbRegister, buttonGoogleRegister;
    EditText editTextPhoneNumber, editTextPassWord, editTextEmail, editTextConfirmPassword;
    Button btnRegister;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Register_Activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initwiget();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void initwiget() {
        buttonBack = findViewById(R.id.icon_back_register);
        buttonFbRegister = findViewById(R.id.icon_facebook_register);
        buttonGoogleRegister = findViewById(R.id.icon_google_register);
        editTextPhoneNumber = findViewById(R.id.editText_phone);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassWord = findViewById(R.id.editText_password);
        editTextConfirmPassword = findViewById(R.id.editText_confirm_password);
    }
}