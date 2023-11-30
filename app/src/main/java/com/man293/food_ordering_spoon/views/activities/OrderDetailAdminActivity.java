package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import com.man293.food_ordering_spoon.R;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OrderDetailAdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_admin);
        findViewById(R.id.BackButton).setOnClickListener(v-> super.onBackPressed());

        final String ORDER_ID = getIntent().getStringExtra("ORDER_ID");
        Toast.makeText(OrderDetailAdminActivity.this, "ORDER_ID: " + ORDER_ID, Toast.LENGTH_SHORT).show();

        //todo: get data buy ORDER_ID
    }
}