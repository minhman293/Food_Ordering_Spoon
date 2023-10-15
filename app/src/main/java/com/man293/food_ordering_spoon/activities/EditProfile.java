package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.man293.food_ordering_spoon.R;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageButton bt_exit = findViewById(R.id.button_back_edit_profile);
        bt_exit.setOnClickListener(v -> {
            super.onBackPressed();
            onBackPressed();
        });
    }
}