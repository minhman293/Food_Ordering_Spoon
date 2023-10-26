package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.data.FakeData;
import com.man293.food_ordering_spoon.models.User;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton nutlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // change to home screen
        nutlogin = findViewById(R.id.nutlogin);
        nutlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView errorMessage = findViewById(R.id.error_message);
                // login
                try {
                    TextInputEditText usernameEditText, passwordEditText;
                    usernameEditText = findViewById(R.id.phoneNumberin);
                    passwordEditText = findViewById(R.id.etPassword);

                    String userName =  String.valueOf(usernameEditText.getText()).trim();
                    String password =  String.valueOf(passwordEditText.getText()).trim();
                    if(userName.isEmpty() || password.isEmpty()) {
                        throw new Exception("Enter information before confirming!");
                    }

                    User currentUser = FakeData.login(new User(userName, password));
                    if(currentUser != null) {
                        SharedPreferences sharedPreferences = getSharedPreferences("auth_info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();

                        String userJson = gson.toJson(currentUser);
                        editor.putString("current_user", userJson);
                        editor.apply();
                        Log.i("CURRENT_USER", userJson);

                        errorMessage.setVisibility(View.INVISIBLE);
                        startActivity(new  Intent(LoginActivity.this, AppActivity.class));
                    }
                } catch (Exception ex) {
                    errorMessage.setText(ex.getMessage());
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        configureNextButton();
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
}