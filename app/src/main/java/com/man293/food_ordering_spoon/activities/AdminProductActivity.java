package com.man293.food_ordering_spoon.activities;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.adapters.CartAdapter;
import com.man293.food_ordering_spoon.adapters.CategoryAdapter;
import com.man293.food_ordering_spoon.adapters.ManageAdapter;
import com.man293.food_ordering_spoon.components.ListViewComponent;
import com.man293.food_ordering_spoon.data.FakeData;
import com.man293.food_ordering_spoon.models.Categories;

import java.util.ArrayList;
import java.util.List;


public class AdminProductActivity extends AppActivity{
    private Spinner spncategory;
    private CategoryAdapter categoryAdapter;
    private ListViewComponent listView;
    private ManageAdapter manageAdapter;
    private Button btnedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_product);
        // Spinner
        spncategory = findViewById(R.id.spinner_category);

        categoryAdapter = new CategoryAdapter(this,R.layout.item_category_selected,getlistcategory());
        spncategory.setAdapter(categoryAdapter);
        spncategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminProductActivity.this,categoryAdapter.getItem(position).getNamecategory(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //ListViewComponent

        listView = findViewById(R.id.ad_list_item);
        initListView();


    }

    private List<Categories> getlistcategory() {
        List<Categories> list = new ArrayList<>();
        list.add(new Categories("All"));
        list.add(new Categories("Pizza"));
        list.add(new Categories("Noodles"));
        list.add(new Categories("Beef"));
        return list;

    }
    private void initListView() {
        manageAdapter = new ManageAdapter(AdminProductActivity.this, FakeData.getProductItems());
        listView.setAdapter(manageAdapter);
        listView.setFullHeight();

    }
}
