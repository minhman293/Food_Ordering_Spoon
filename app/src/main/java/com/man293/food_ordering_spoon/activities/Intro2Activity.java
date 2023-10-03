package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;

public class Intro2Activity extends AppCompatActivity {

    private AppCompatButton btnCreateAnAccount;
    private TextView textHaveAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro2);

        btnCreateAnAccount = findViewById(R.id.btn_createAnAccount) ;
        textHaveAccount = findViewById(R.id.text_haveAccount);

        /** Change to login activity */
        btnCreateAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro2Activity.this, LoginActivity.class));
            }
        });

        /** Change to sign in activity */
        textHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intro2Activity.this, SignInActivity.class));
            }
        });
    }
}