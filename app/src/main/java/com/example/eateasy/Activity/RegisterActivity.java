package com.example.eateasy.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eateasy.Model.SanPham;
import com.example.eateasy.Model.User;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.UserInterface;
import com.example.eateasy.Retrofit.UserUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView buttonBack, buttonFbRegister, buttonGoogleRegister;
    EditText editTextPhoneNumber, editTextPassWord, editTextEmail, editTextConfirmPassword;
    Button btnRegister;
    CheckBox checkBoxShowPassword;
    ArrayList<User> userList;
    UserInterface userInterface;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userList = (ArrayList<User>) bundle.getSerializable("userList");  // Nhận ArrayList<User> từ Bundle

            if (userList != null && !userList.isEmpty()) {
                // Xử lý dữ liệu userArrayList
                Toast.makeText(this, "Danh sách người dùng nhận thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Không có người dùng", Toast.LENGTH_SHORT).show();
            }
        }

        initwiget();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        //hiện pass
        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editTextPassWord.setInputType(InputType.TYPE_CLASS_TEXT);
                editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                editTextPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        //dang ki
        userInterface = UserUtils.getUserService();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInputs(userList)){
                    Toast.makeText(RegisterActivity.this, "Đăng Kí thành công", Toast.LENGTH_SHORT).show();
                    String sdt = editTextPhoneNumber.getText().toString().trim();
                    String email = editTextEmail.getText().toString().trim();
                    String pass = editTextConfirmPassword.getText().toString();
                    User user = new User(sdt,email,pass,1);
                    Gson gson = new Gson();
                    String json = gson.toJson(user);
                    Log.d("UserJSON", json);
                    addUserToDatabase(user);
//                    Intent intent = new Intent(RegisterActivity.this, Login_Activity.class);
//                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, "Lỗi, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs(ArrayList<User> userList) {
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassWord.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Check if email is already used
        for (User user : userList) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                editTextEmail.setError("Email đã được sử dụng");
                return false;
            }
        }

        // Check if phone number is already used
        for (User user : userList) {
            if (user.getSdt().equals(phoneNumber)) {
                editTextPhoneNumber.setError("Số điện thoại đã được sử dụng");
                return false;
            }
        }

        // Check email format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email không đúng định dạng");
            return false;
        }

        // Check password requirements
        if (!isPasswordValid(password)) {
            editTextPassWord.setError("Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt");
            return false;
        }

        // Check if confirm password matches password
        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Mật khẩu nhập lại không khớp");
            return false;
        }

        return true;
    }

    // Hàm kiểm tra yêu cầu mật khẩu
    private boolean isPasswordValid(String password) {
        return password.length() > 6 &&
                password.matches(".*[A-Z].*") &&  // Ít nhất một chữ hoa
                password.matches(".*[a-z].*") &&  // Ít nhất một chữ thường
                password.matches(".*\\d.*") &&    // Ít nhất một chữ số
                password.matches(".*[@#$%^&+=!].*"); // Ít nhất một ký tự đặc biệt
    }

    //ADD user
    private void addUserToDatabase(User user) {
        //Tạo API Interface và gửi yêu cầuuu
        userInterface.addUser(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng quay lại trang đăng nhập để đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initwiget() {
        btnRegister = findViewById(R.id.button_register);
        buttonBack = findViewById(R.id.icon_back_register);
        buttonFbRegister = findViewById(R.id.icon_facebook_register);
        buttonGoogleRegister = findViewById(R.id.icon_google_register);
        editTextPhoneNumber = findViewById(R.id.editText_phone);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassWord = findViewById(R.id.editText_password);
        editTextConfirmPassword = findViewById(R.id.editText_confirm_password);
        checkBoxShowPassword = findViewById(R.id.checkBox_show_password);
    }
}