package com.man293.food_ordering_spoon.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.views.adapters.CategoryAdapter;
import com.man293.food_ordering_spoon.views.adapters.ManageAdapter;
import com.man293.food_ordering_spoon.views.components.DialogComponent;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.data.FakeData;
import com.man293.food_ordering_spoon.models.Categories;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class AdminProductActivity extends AppCompatActivity {
    private Spinner spncategory;
    private CategoryAdapter categoryAdapter;
    private ListViewComponent listView;
    private ManageAdapter manageAdapter;
    private Button btnAdd, btnRemove ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_product);
        spncategory = findViewById(R.id.spinner_category);
        btnAdd = findViewById(R.id.btn_addNew);
        btnRemove = findViewById(R.id.btn_remove);
        listView = findViewById(R.id.ad_list_item);

        // Spinner
        categoryAdapter = new CategoryAdapter(AdminProductActivity.this,R.layout.item_category_selected,getlistcategory());
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
        initListView();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminProductActivity.this, CreateProductActivity.class));
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogComponent dialog = new DialogComponent(
                        AdminProductActivity.this,
                        R.style.bottom_sheet_dialog_theme,
                        "Are you sure you want to delete selected items?"
                );

                dialog.show();
            }
        });

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
