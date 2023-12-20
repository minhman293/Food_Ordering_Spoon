package com.man293.food_ordering_spoon.views.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

   private AppCompatButton nutSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        nutSignin = findViewById(R.id.nutSignin);
        nutSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView errorMessage = findViewById(R.id.error_message);
               //        signIn
                try {
                    TextInputEditText firstNameEditText,lastNameEditText,phoneNumberEditText,addressEditText, passwordEditText;
                    firstNameEditText = findViewById(R.id.FirstNamein);
                    lastNameEditText = findViewById(R.id.LastNamein);
                    phoneNumberEditText = findViewById(R.id.phoneNumberin2);
                    addressEditText = findViewById(R.id.AddressIn);
                    passwordEditText = findViewById(R.id.enterPassword);


                    String firstName =  String.valueOf(firstNameEditText.getText()).trim();
                    String lastName =  String.valueOf(lastNameEditText.getText()).trim();
                    String address =  String.valueOf(addressEditText.getText()).trim();
                    String phoneNumber =  String.valueOf(phoneNumberEditText.getText()).trim();
                    String password =  String.valueOf(passwordEditText.getText()).trim();
                    if(phoneNumber.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() ) {
                        throw new Exception("Enter information before confirming!");
                    }

                    String url = getString(R.string.BASE_URL) + getString(R.string.API_REGISTER__POST);
                    SignInActivity.SigninTask task = new SignInActivity.SigninTask(firstName,lastName, phoneNumber,address, password,"token");
                    task.execute(url);
                    errorMessage.setVisibility(View.INVISIBLE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        configureBackButton();


    }
    private class SigninTask extends AsyncTask<String, Integer, User> {

        private String firstName,lastName, phoneNumber,address, password, token;

        public SigninTask(String firstName,String lastName,String phoneNumber,String address, String password,String token) {
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.token = token;
        }
        @Override
        protected User doInBackground(String... strings) {
            try {
               // Thực hiện kết nối với server
                OkHttpClient client = new OkHttpClient();
                Map<String, Object> data = new HashMap<>();
                data.put("firstName", this.firstName);
                data.put("lastName", this.lastName);
                data.put("phone", this.phoneNumber);
                data.put("address", this.address);
                data.put("password", this.password);
                data.put("token", this.token);

                String json = new Gson().toJson(data);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

                Request req = new Request.Builder()
                        .url(strings[0])
                        .post(body)
                        .build();
                Response res = client.newCall(req).execute();

                // Log phản hồi cho mục đích gỡ lỗi
                Log.d(TAG, "Response Code: " + res.code());

                // Kiểm tra giá trị trước khi xử lý phản hồi từ server
                if (res.body() != null && res.isSuccessful()) {
                    String jsonResponse = res.body().string();
                    Log.d(TAG, "JSON Response: " + jsonResponse);
                  // Đọc thông tin người dùng từ JSON
                    try {
                        JSONObject userJson = new JSONObject(jsonResponse);
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
                    // Log the error body for debugging
                    Log.e(TAG, "Error Body: " + res.body().string());
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
            super.onPostExecute(user);
            onSignIn(user);
        }
    }
    private void onSignIn(User user) {
        if (user != null) {
           // Lưu thông tin người dùng và chuyển hướng sang AppActivity
            User.saveCurrentUser(SignInActivity.this, user);
            startActivity(new Intent(SignInActivity.this, AppActivity.class));
        } else {
            Log.e(TAG, "User object is null");
            // Hiển thị thông báo lỗi
            showErrorToast("Đăng ký thất bại. Kiểm tra lại số điện thoại và mật khẩu.");
        }
    }
// Hiển thị thông báo lỗi dưới dạng Toast
    private void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void configureBackButton() {
        Button nextButton = (Button) findViewById(R.id.nutback);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(SignInActivity.this, LoginActivity.class));
            }
        });
    }
}
