package com.example.kimnam_final.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.kimnam_final.R;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);

        ImageButton bt_back = findViewById(R.id.button_back);
        bt_back.setOnClickListener(v -> {
            super.onBackPressed();
            onBackPressed();
        });
    }
}