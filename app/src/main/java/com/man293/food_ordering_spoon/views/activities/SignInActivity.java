package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.man293.food_ordering_spoon.R;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        configureBackButton();
        signIn();
    }

    private void signIn() {
        AppCompatButton nutSignin = findViewById(R.id.nutSignin);
        nutSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, AppActivity.class));
            }
        });
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