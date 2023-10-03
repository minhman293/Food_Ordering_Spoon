package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.man293.food_ordering_spoon.R;

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
                startActivity(new  Intent(LoginActivity.this, AppActivity.class));
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