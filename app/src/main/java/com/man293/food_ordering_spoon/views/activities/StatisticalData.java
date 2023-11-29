package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetOrderItemTask;
import com.man293.food_ordering_spoon.models.OrderItem;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.views.adapters.OrderAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticalData extends AppCompatActivity {
    private Spinner spn_Month;
    private Spinner spn_Year;
    private ListView orderListView;
    private OrderAdapter orderAdapter;
    private ArrayList<OrderItem> items;
    private TextView revenueYear, revenueMonth, monthStatistical, yearStatistical ;
    private boolean isFirstLoad = true;
    private Map<String, Integer> months;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical_data);
        orderListView = findViewById(R.id.order_list_view);
        spn_Month = findViewById(R.id.spinner_month);
        spn_Year = findViewById(R.id.spinner_year);
        revenueMonth = findViewById(R.id.money_month_statictical);
        revenueYear = findViewById(R.id.money_year_statictical);
        monthStatistical = findViewById(R.id.month_statictical);
        yearStatistical  = findViewById(R.id.year_statictical);

        months = new LinkedHashMap<>();
        String[] monthNames = { "January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"  };
        for(int i = 0; i < monthNames.length; i++) {
            months.put(monthNames[i], i+ 1);
        }

        initYearSpinner();
        initMonthSpinner();

        orderListView.setOnItemClickListener((adapterView, view, i, l) -> {
            try {
                Intent intent = new Intent(StatisticalData.this, OrderDetailAdminActivity.class);
                intent.putExtra("ORDER_ID", ((OrderItem)adapterView.getItemAtPosition(i)).getId());
                startActivity(intent);
            } catch (Exception ex) {
                Toast.makeText(StatisticalData.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initYearSpinner() {
        List<Integer> listYear = new ArrayList<>();
        int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2021; i <= CURRENT_YEAR; i++) {
            listYear.add(i);
        }
        ArrayAdapter<Integer> adapterYear = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listYear);
        adapterYear.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_Year.setAdapter(adapterYear);
        spn_Year.setSelection(adapterYear.getPosition(CURRENT_YEAR));
        spn_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int selectedYear = (int) parentView.getItemAtPosition(position);
                yearStatistical.setText(String.valueOf(selectedYear));
                if(!isFirstLoad) {
                    loadOrderData(StatisticalData.this, months.get(spn_Month.getSelectedItem()), selectedYear);
//                    Toast.makeText(StatisticalData.this, "LOAD DATA FOR " + selectedYear, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {  }
        });
    }

    private void initMonthSpinner() {
        ArrayAdapter<String> adapterMonth = new ArrayAdapter(this,android.R.layout.simple_spinner_item,new ArrayList<>(months.keySet()));
        adapterMonth.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_Month.setAdapter(adapterMonth);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        for (Map.Entry<String, Integer> entry : months.entrySet()) {
            if(entry.getValue() == currentMonth) {
                spn_Month.setSelection(adapterMonth.getPosition(entry.getKey()) + 1);
            }
        }
        spn_Month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItem = (String) parentView.getItemAtPosition(position);
                monthStatistical.setText( selectedItem);
                loadOrderData(StatisticalData.this, months.get(selectedItem), (int) spn_Year.getSelectedItem());
//                Toast.makeText(StatisticalData.this, "LOAD DATA FOR " + months.get(selectedItem), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {  }
        });
    }

    private void loadOrderData(Context context, int month, int year) {
        String url = context.getString(R.string.BASE_URL) + context.getString(R.string.API_GET_ORDERS__POST);
        new GetOrderItemTask(month, year).setOnGotListener(data -> {
            if(data == null) return;
            this.revenueMonth.setText(CurrencyUtils.format(Double.parseDouble(String.valueOf(data.get("revenueMonth")))));
            this.revenueYear.setText(CurrencyUtils.format(Double.parseDouble(String.valueOf(data.get("revenueYear")))));
            try {
                this.isFirstLoad = false;
                this.items = (ArrayList<OrderItem>) data.get("orders");
                // todo : change to use this.orderAdapter.notifyDataSetChanged();
                this.orderAdapter = new OrderAdapter(context, this.items);
                this.orderListView.setAdapter(this.orderAdapter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).execute(url);
    }
}