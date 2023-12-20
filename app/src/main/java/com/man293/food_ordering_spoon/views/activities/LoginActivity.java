package com.man293.food_ordering_spoon.views.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.AuthTask;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.utils.GoogleSignManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton nutlogin;
    private final  int GOOGLE_AUTH_REQUEST_CODE = 2106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initGoogleAuth();
        // change to home screen
        nutlogin = findViewById(R.id.nutlogin);
        nutlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView errorMessage = findViewById(R.id.error_message);
                // login
                try {
                    // Mã để lấy tên đăng nhập và mật khẩu từ các trường nhập liệu
                    TextInputEditText usernameEditText, passwordEditText;
                    usernameEditText = findViewById(R.id.phoneNumberin);
                    passwordEditText = findViewById(R.id.etPassword);

                    String phoneNumber =  String.valueOf(usernameEditText.getText()).trim();
                    String password =  String.valueOf(passwordEditText.getText()).trim();
                    // Kiểm tra xem tên đăng nhập hoặc mật khẩu có trống không
                    if(phoneNumber.isEmpty() || password.isEmpty()) {
                        throw new Exception("Enter information before confirming!");
                    }
                    // Xây dựng URL đăng nhập và thực hiện một nhiệm vụ đăng nhập bất đồng bộ
                    String url = getString(R.string.BASE_URL) + getString(R.string.API_LOGIN__POST);
                    LoginTask task = new LoginTask(phoneNumber, password, "_token");
                    task.execute(url);
                    // Ẩn bất kỳ thông báo lỗi trước đó
                    errorMessage.setVisibility(View.INVISIBLE);
                } catch (Exception ex) {
                    // Xử lý các ngoại lệ (ví dụ: hiển thị thông báo lỗi)
                    errorMessage.setText(ex.getMessage());
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        configureNextButton();
    }



    private void onLoggedIn(User user) {
        // Xử lý sau khi người dùng đã đăng nhập
        if (user != null) {
            User.saveCurrentUser(LoginActivity.this, user);
            startActivity(new Intent(LoginActivity.this, AppActivity.class));
        } else {
            Log.e(TAG, "User object is null");
            showErrorToast("Something went wrong!.");
        }
    }

    private void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.changeToSignIn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(LoginActivity.this, SignInActivity.class));
            }
        });
    }
    private class LoginTask extends AsyncTask<String, Integer,User> {
        private String phoneNumber, password, token;

        public LoginTask(String phoneNumber, String password, String token) {
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.token = token;
        }
        @Override
        protected User doInBackground(String... strings) {
            // Thực hiện đăng nhập bất đồng bộ
            try {
                OkHttpClient client = new OkHttpClient();
                Map<String, Object> data = new HashMap<>();
                data.put("phone", this.phoneNumber);
                data.put("password", this.password);
                data.put("token", this.token);

                String json = new Gson().toJson(data);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

                Request req = new Request.Builder()
                        .url(strings[0])
                        .post(body)
                        .build();
                Response res = client.newCall(req).execute();

                // Kiểm tra giá trị trước khi xử lý phản hồi từ server
                if (res.body() != null && res.isSuccessful()) {
                    try {
                        // Đọc thông tin người dùng từ JSON
                        JSONObject userJson = new JSONObject(res.body().string());
                        User user = new User(
                                userJson.getString("_id"),
                                userJson.getString("firstName"),
                                userJson.getString("lastName"),
                                userJson.getString("phone"),
                                userJson.getString("address"),
                                userJson.getInt("role"),
                                userJson.getString("picture")
                        );
                        return user;
                    } catch (JSONException e) {
                        // Xử lý ngoại lệ khi có lỗi trong quá trình đọc JSON
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    // Trả về null khi có lỗi xảy ra trong quá trình kiểm tra dữ liệu
                    return null;
                }
            } catch (Exception e) {
                // Xử lý ngoại lệ khi có lỗi trong quá trình gửi yêu cầu
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(User user) {
            // Xử lý sau khi đăng nhập bất đồng bộ hoàn tất
            super.onPostExecute(user);
            onLoggedIn(user);
        }
    }

    private void initGoogleAuth() {
        // Khởi tạo quá trình xác thực Google Sign-In và gán sự kiện click cho nút "google_auth_btn"
        Intent intent = GoogleSignManager
                .getInstance(this)
                .setClient(this)
                .getClient()
                .getSignInIntent();

        findViewById(R.id.google_auth_btn).setOnClickListener( v -> {
            startActivityForResult(intent, GOOGLE_AUTH_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Xử lý kết quả trả về từ việc đăng nhập Google
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_AUTH_REQUEST_CODE && data != null) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Lấy thông tin tài khoản Google nếu quá trình đăng nhập thành công
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if(account != null) {
                    Log.d("GOOGLE_TOKEN", account.getIdToken());
                    sendTokenToServer(account.getIdToken(), getString(R.string.API_GOOGLE_AUTH));
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void sendTokenToServer(String idToken, String path) {
        // Gửi mã thông báo Google Sign-In đến server để xác thực
        AuthTask task =  new AuthTask(idToken);
        task.setOnAuthenticated(this::onLoggedIn);
        task.execute(getString(R.string.BASE_URL)+ path);
    }
}
