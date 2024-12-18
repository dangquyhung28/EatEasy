package com.example.eateasy.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eateasy.Activity.Admin.DashboardActivity;
import com.example.eateasy.Activity.User.HomeActivity;
import com.example.eateasy.Model.User;
import com.example.eateasy.R;
import com.example.eateasy.Retrofit.Interface.UserInterface;
import com.example.eateasy.Retrofit.Utils.UserUtils;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login_Activity extends AppCompatActivity {
    EditText edtUsername, edtPass;
    Button btnLogin;
    TextView txtDangKi, tvQuenMK;
    ImageView imgFb, imgGG;
    ImageButton imgBSetting;
    UserInterface userInterface;
    CheckBox cbShowPassword;
    ArrayList<User> userArrayList = new ArrayList<>();
    int user_id;
    CallbackManager callbackManager;


    private BroadcastReceiver networkStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Kiểm tra status mạng từ intent
            String status = intent.getStringExtra("status");

            if (status != null) {
                if (status.equals("connected")) {
                    // Hiển thị Snackbar khi kết nối lại
                    Snackbar.make(findViewById(android.R.id.content), "Kết nối mạng đã được phục hồi!", Snackbar.LENGTH_LONG).show();
                } else if (status.equals("disconnected")) {
                    // Hiển thị Snackbar khi mất kết nối
                    Snackbar.make(findViewById(android.R.id.content), "Không có kết nối mạng!", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.eateasy.NETWORK_STATUS");
        registerReceiver(networkStatusReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStatusReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //anh xa
        initwiget();
        callbackManager = CallbackManager.Factory.create();

        //hiện pass
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtPass.setSelection(edtPass.length());
        });

        //xu ly DANG NHAP
        loadUser();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPass.getText().toString().trim();
                if (checkLogin(username, password) == 1 || checkLogin(username, password) == 0) {

                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, HomeActivity.class);
                    intent.putExtra("userId", user_id);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login_Activity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }

        });
        //login Fb
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Lấy Access Token
                        String accessToken = loginResult.getAccessToken().getToken();

                        // Gửi yêu cầu để lấy thông tin người dùng
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            // Lấy thông tin từ object
                                            String facebookId = object.getString("id");
                                            String name = object.optString("name");
                                            String email = object.optString("email");

                                            // Đăng nhập thành công, lưu thông tin vào bảng User
                                            saveUserToDatabase(facebookId, name, email);

                                            // Chuyển tới HomeActivity
                                            startActivity(new Intent(Login_Activity.this, HomeActivity.class));
                                            finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        // Yêu cầu các trường thông tin từ Facebook
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }


                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        imgFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Login_Activity.this, Arrays.asList("public_profile"));
            }
        });
        //LOGIN GG


        //Đăng kí
        txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login_Activity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userList", userArrayList);  // Truyền ArrayList<User> qua Bundle
                intent.putExtras(bundle);  // Đưa Bundle vào Intent
                startActivity(intent);
            }
        });

        //đăng nhập Admin
        imgBSetting.setOnClickListener(v -> showAdminLoginDialog());

        //QUÊN MẬT KHÂU
        tvQuenMK.setOnClickListener(v -> {
            // Tạo dialog yêu cầu người dùng nhập email hoặc số điện thoại
            AlertDialog.Builder builder = new AlertDialog.Builder(Login_Activity.this);

            // Giao diện cho email hoặc số điện thoại
            View view = getLayoutInflater().inflate(R.layout.dialog_reset_password, null);
            EditText etInput = view.findViewById(R.id.etInput);
            RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
            builder.setView(view);

            builder.setPositiveButton("Gửi", (dialog, which) -> {
                String input = etInput.getText().toString().trim();
                if (input.isEmpty()) {
                    Toast.makeText(Login_Activity.this, "Vui lòng nhập email hoặc số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra người dùng chọn email hay số điện thoại
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.rbEmail) {
                    for (User user : userArrayList) {
                        if (user.getEmail().equalsIgnoreCase(input)) {
                            etInput.setError("Email chưa đươợc sử dụng");
                            return;
                        }
                    }
                    // Khôi phục mật khẩu qua Email
                } else if (selectedId == R.id.rbPhone) {
                    for (User user : userArrayList) {
                        if (user.getSdt().equalsIgnoreCase(input)) {
                            etInput.setError("Email chưa đươợc sử dụng");
                            return;
                        }
                    }
                }
            });

            builder.setNegativeButton("Hủy", null);
            builder.show();
        });
    }

    private void loadUser() {
        userInterface = UserUtils.getUserService();
        userInterface.getAllUser().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userArrayList.clear();
                userArrayList.addAll(response.body());
                //Toast.makeText(Login_Activity.this,""+userArrayList.get(0).getEmail(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Toast.makeText(Login_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToDatabase(String facebookId, String name, String email) {
        User user = new User("trống",email,"trống",1,facebookId);
        addUserToDatabase(user, email);
    }

    private void addUserToDatabase(User user, String email) {
        //Tạo API Interface và gửi yêu cầuuu
        userInterface.addUser(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    loadUser();
                    for (User user : userArrayList) {
                        if ((user.getEmail().equals(email) && user.getType() == 1)) {
                            user_id = user.getUserId();
                            Intent intent = new Intent(Login_Activity.this, HomeActivity.class);
                            intent.putExtra("userId", user_id);
                            startActivity(intent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Login_Activity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //fb

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Hộp thoại đăng nhập ADminh
    private void showAdminLoginDialog() {
        // Tạo một AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_admin_login, null);
        builder.setView(dialogView);

        EditText etAdminUsername = dialogView.findViewById(R.id.etAdminUsername);
        EditText etAdminPassword = dialogView.findViewById(R.id.etAdminPassword);


        Button btnAdminLogin = dialogView.findViewById(R.id.btnAdminLogin);


        AlertDialog dialog = builder.create();
        dialog.show();


        btnAdminLogin.setOnClickListener(v -> {
            String username = etAdminUsername.getText().toString();
            String password = etAdminPassword.getText().toString();
            if (checkLogin(username, password) == 0) {
                Toast.makeText(Login_Activity.this, "Đăng nhập admin thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Activity.this, DashboardActivity.class);
                intent.putExtra("userId", user_id);
                startActivity(intent);
                return;
            } else {
                Toast.makeText(Login_Activity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }

        });


        //Hieu ung Nút đăng nhập
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
    }


        private void initwiget() {
            edtUsername = findViewById(R.id.etUsername);
            edtPass = findViewById(R.id.etPassword);
            txtDangKi = findViewById(R.id.tvDangKi);
            imgFb = findViewById(R.id.iconGG);
            imgGG = findViewById(R.id.iconFb);
            imgBSetting = findViewById(R.id.settings_button);
            btnLogin = findViewById(R.id.button_login);
            cbShowPassword = findViewById(R.id.cbShowPassword);
            tvQuenMK = findViewById(R.id.tvQuenMK);
        }

        //check login
        private Integer checkLogin (String username, String password){

            if (username.isEmpty()) {
                edtUsername.setError("Tên đăng nhập không được trống");
                return -1;
            }

            if (password.isEmpty()) {
                edtPass.setError("Mật khẩu không được trống");
                return -1;
            }

            for (User user : userArrayList) {
                if ((user.getEmail().equals(username) || user.getSdt().equals(username)) && user.getPassword().equals(password) && user.getType() == 1) {
                    user_id = user.getUserId();
                    return 1;
                }
                if ((user.getEmail().equals(username) || user.getSdt().equals(username)) && user.getPassword().equals(password) && user.getType() == 0) {
                    user_id = user.getUserId();
                    return 0;
                }
            }
            return -1;
        }
    }