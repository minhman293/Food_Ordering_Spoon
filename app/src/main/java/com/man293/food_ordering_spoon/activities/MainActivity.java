package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloText = findViewById(R.id.helloText);
        helloText.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AppActivity.class));
        });
    }
}