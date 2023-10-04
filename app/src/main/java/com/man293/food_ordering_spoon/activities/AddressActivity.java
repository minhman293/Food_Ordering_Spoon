package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.man293.food_ordering_spoon.R;

import java.sql.Array;

public class AddressActivity extends AppCompatActivity {

    private AutoCompleteTextView autocompleteCity, autoCompleteDistrict;
    private String[] PROVINCES =  { "Đà Nẵng", "Đắk Lắk",  "Phú Yên", "Quảng Bình",  "Quảng Nam",  "Quảng Ngãi"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        autocompleteCity = findViewById(R.id.autocomplete_city);
        autoCompleteDistrict = findViewById(R.id.autocomplete_district);

        autocompleteCity.setAdapter(new ArrayAdapter<String>(
                AddressActivity.this,
                R.layout.address_item,
                PROVINCES
        ));

        autoCompleteDistrict.setAdapter(new ArrayAdapter<String>(
                AddressActivity.this,
                R.layout.address_item,
                PROVINCES
        ));

    }
}