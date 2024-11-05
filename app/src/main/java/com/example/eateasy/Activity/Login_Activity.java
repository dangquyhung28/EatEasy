package com.example.eateasy.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.Activity.Admin.DashboardActivity;
import com.example.eateasy.Activity.User.HomeActivity;
import com.example.eateasy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login_Activity extends AppCompatActivity {
    EditText edtUsername, edtPass;
    Button btnLogin;
    TextView txtDangKi;
    ImageView imgFb, imgGG;
    ImageButton imgBSetting;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //anh xa
        initwiget();

        mAuth = FirebaseAuth.getInstance();
//        String phone = "0335739296";
//        String email = "hung@gmail.com";
//        String pass = "hung123@";
//        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Log.d("Main", "tạo tài khoản với email thành công");
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    Toast.makeText(getApplicationContext(), user.getEmail(), Toast.LENGTH_LONG).show();
//                }else{
//                    Log.w("Main", "Tạo tài khoản thất bại", task.getException());
//                    Toast.makeText(Login_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUsername.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login_Activity.this, "Vui lòng nhập đầy đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Đăng nhập bằng email và mật khẩu đã nhập
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công
                            Intent intent = new Intent(Login_Activity.this, HomeActivity.class);
                            startActivity(intent);
                            Log.d("Login", "Đăng nhập thành công");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công: " + user.getEmail(), Toast.LENGTH_LONG).show();
                            // Chuyển đến màn hình chính hoặc thực hiện hành động khác
                        } else {
                            // Đăng nhập thất bại
                            Log.w("Login", "Đăng nhập thất bại", task.getException());
                            Toast.makeText(Login_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // Khi nhấn vào
                        v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100).start();
                        break;
                    case MotionEvent.ACTION_UP: // Khi thả ra
                    case MotionEvent.ACTION_CANCEL:
                        v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                        break;
                }
                return false;
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