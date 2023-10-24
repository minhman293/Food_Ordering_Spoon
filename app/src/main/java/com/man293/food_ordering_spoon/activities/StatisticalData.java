package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.man293.food_ordering_spoon.R;

import java.util.ArrayList;
import java.util.List;

public class StatisticalData extends AppCompatActivity {
    private Spinner spn_Month;
    private Spinner spn_Year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_data);

        spn_Month = findViewById(R.id.spinner_month);
        spn_Year = findViewById(R.id.spinner_year);

        List<String> listMonth = new ArrayList<>();
        listMonth.add("January");
        listMonth.add("February");
        listMonth.add("March");
        listMonth.add("April");
        listMonth.add("May");
        listMonth.add("June");
        listMonth.add("July");
        listMonth.add("August");
        listMonth.add("September");
        listMonth.add("October");
        listMonth.add("November");
        listMonth.add("December");

        ArrayAdapter<String> adapterMonth = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listMonth);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_Month.setAdapter(adapterMonth);
        int defaultItemMonth = adapterMonth.getPosition("October");
        spn_Month.setSelection(defaultItemMonth);
        spn_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                String selectedItem = (String) parentView.getItemAtPosition(position);
                // Thực hiện các tác vụ cần thiết dựa trên lựa chọn của người dùng
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        List<String> listYear = new ArrayList<>();
        listYear.add("2020");
        listYear.add("2021");
        listYear.add("2022");
        listYear.add("2023");

        ArrayAdapter<String> adapterYear = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listYear);
        adapterYear.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_Year.setAdapter(adapterYear);
        int defaultItemYear = adapterYear.getPosition("2023");
        spn_Year.setSelection(defaultItemYear);
        spn_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Xử lý sự kiện khi người dùng chọn một mục từ Spinner
                String selectedItem = (String) parentView.getItemAtPosition(position);
                // Thực hiện các tác vụ cần thiết dựa trên lựa chọn của người dùng
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có mục nào được chọn
            }
        });
    }
}