package com.man293.food_ordering_spoon.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;


import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetAdminCategoryTask;
import com.man293.food_ordering_spoon.asynctasks.GetAdminProductTask;
import com.man293.food_ordering_spoon.asynctasks.DeleteProductTask;
import com.man293.food_ordering_spoon.models.Category;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.views.adapters.CategoryAdapter;
import com.man293.food_ordering_spoon.views.adapters.ManageAdapter;
import com.man293.food_ordering_spoon.views.components.DialogComponent;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;



public class AdminProductActivity extends AppCompatActivity {
    public Spinner spncategory;
    public CategoryAdapter categoryAdapter;
    public ListViewComponent listView;
    public ArrayList<Product> arrayProduct;
    public ManageAdapter manageAdapter;
    private Button btnAdd, btnRemove ;
    private ImageButton goBackButton;
    public ArrayList <Category> categories;
    private ArrayList<String> productIds;
    public LoaderComponent loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list_product);
        spncategory = findViewById(R.id.spinner_category);
        btnAdd = findViewById(R.id.btn_addNew);
        btnRemove = findViewById(R.id.btn_remove);
        listView = findViewById(R.id.ad_list_item);
        goBackButton = findViewById(R.id.goBackButton);
        loader = new LoaderComponent(this);
        productIds = new ArrayList<>();
        categories = new ArrayList<>();

        goBackButton.setOnClickListener(v-> onBackPressed());

        // Spinner
        getListCategory();
         spncategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loader.start();
                String categoryId = categoryAdapter.getItem(position).getId();
                if(categoryId.equals("ALL")) {
                    getProducts(getString(R.string.API_GET_PRODUCTS__GET));
                } else {
                    getProducts(getString(R.string.API_GET_PRODUCT_BY_CATEGORY__GET, categoryId));
                }
        }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AdminProductActivity.this, CreateProductActivity.class), 0);
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
                dialog.setOnConfirmListener(() -> {
                    DeleteProductTask deleteTask = new DeleteProductTask(productIds);

                    deleteTask.execute(getString(R.string.BASE_URL) + getString(R.string.API_DELETE_PRODUCT__POST));
                    deleteTask.setOnDeleteListener(isDeleted -> {


                        for (Product c: new ArrayList<>(arrayProduct)) {
                            if(productIds.contains(c.getId())) {
                                arrayProduct.remove(c);
                                Log.d("REMOVE", c.getId());
                            } else {
                                Log.d("NO REMOVE", c.getId());
                            }
                        }
                        manageAdapter.notifyDataSetChanged();
                        listView.setAdapter(manageAdapter);
                        listView.setFullHeight();
                    });

                });
                dialog.show();

            }
        });
    }
    public void getListCategory()  {
//        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(
                AdminProductActivity.this,
                R.layout.item_category_selected,
                categories);
        spncategory.setAdapter(categoryAdapter);
        new GetAdminCategoryTask(this).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CATEGORIES__GET));

    }
    private void getProducts(String url) {
        arrayProduct = new ArrayList<>();
        productIds.clear();
        manageAdapter = new ManageAdapter(AdminProductActivity.this,arrayProduct);
        manageAdapter.setOnCheckListener((isChecked, id) -> {
            if(isChecked) {
                productIds.add(id);
            } else {
                productIds.remove(id);
            }
            Log.d("PRODUCT_IDS", productIds.toString());
        });
        manageAdapter.setOnEditButtonClickListener(product -> {
            Intent intent = new Intent(AdminProductActivity.this,UpdateProductActivity.class);
            intent.putExtra("product",(Serializable) product);
            startActivityForResult(intent, 1);
        });
        listView.setAdapter(manageAdapter);
        new GetAdminProductTask(this).execute(getString(R.string.BASE_URL) + url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            getProducts(getString(R.string.API_GET_PRODUCTS__GET));
        }
    }
}
