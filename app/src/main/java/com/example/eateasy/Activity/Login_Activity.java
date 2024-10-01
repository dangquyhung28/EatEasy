package com.example.eateasy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.Activity.Admin.DashboardActivity;
import com.example.eateasy.Activity.User.HomeActivity;
import com.example.eateasy.R;

public class Login_Activity extends AppCompatActivity {
    EditText edtUsername, edtPass;
    Button btnLogin;
    TextView txtDangKi;
    ImageView imgFb, imgGG;
    ImageButton imgBSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Login_Activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //anh xa
        initwiget();

        //dang nhập test
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPass.getText().toString();

                // Kiểm tra tài khoản admin
                if (username.equals("dangquyhung") && password.equals("123")) {
                    // Chuyển đến trang Admin
                    Intent intent = new Intent(Login_Activity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    // Hiển thị thông báo đăng nhập thất bại
                    Toast.makeText(Login_Activity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Đăng kí test
        txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //đăng nhập Admin
        imgBSetting.setOnClickListener(v -> showAdminLoginDialog());
    }
    //Hộp thoại đăng nhập ADminh
    private void showAdminLoginDialog() {
        // Tạo một AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admin_login, null);
        builder.setView(dialogView);

        // Tìm các EditText trong hộp thoại
        EditText etAdminUsername = dialogView.findViewById(R.id.etAdminUsername);
        EditText etAdminPassword = dialogView.findViewById(R.id.etAdminPassword);

        // Tìm nút "Đăng nhập" trong layout của hộp thoại
        Button btnAdminLogin = dialogView.findViewById(R.id.btnAdminLogin);

        // Tạo AlertDialog và hiển thị
        AlertDialog dialog = builder.create();
        dialog.show();

        // Xử lý sự kiện khi ấn vào nút "Đăng nhập"
        btnAdminLogin.setOnClickListener(v -> {
            String username = etAdminUsername.getText().toString();
            String password = etAdminPassword.getText().toString();

            // Kiểm tra tài khoản admin
            if (username.equals("admin") && password.equals("admin123")) {
                // Chuyển đến trang Admin
                Intent intent = new Intent(Login_Activity.this, DashboardActivity.class);
                startActivity(intent);
                dialog.dismiss();
            } else {
                // Hiển thị thông báo đăng nhập thất bại
                Toast.makeText(Login_Activity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initwiget() {
        edtUsername = findViewById(R.id.etUsername);
        edtPass = findViewById(R.id.etPassword);
        txtDangKi = findViewById(R.id.tvDangKi);
        imgFb  = findViewById(R.id.iconFacebook);
        imgGG = findViewById(R.id.iconGoogle);
        imgBSetting = findViewById(R.id.settings_button);
        btnLogin = findViewById(R.id.button_login);
    }
}