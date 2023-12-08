package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetCategoryTask;
import com.man293.food_ordering_spoon.models.Category;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;
import com.squareup.picasso.Picasso;


public class UpdateProductActivity extends AppCompatActivity {

    ImageView imageView;
    Spinner spinnerCategory;
    EditText editTextProName,editTextProPrice,editTextProDes;
    Intent intent;

    Button btnUpdateProduct;
    ImageButton btnBackInUpdateProduct;
    ArrayAdapter<Category> categoryAdapter;
    LoaderComponent loader;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        initialize();


        if (intent != null && intent.hasExtra("product")){
            product = (Product) intent.getSerializableExtra("product");

            String name = product.getName();
            String desc = product.getDesc();
            String price = String.valueOf(product.getPrice());
            String category = product.getCategoryId();
            String image = product.getImageSrc();

            String imgPath = getString(R.string.PUBLIC_IMAGES, image);
            String baseUrl = getString(R.string.BASE_URL);

            baseUrl = baseUrl.replaceAll("/$", ""); // Remove trailing slash from the base URL
            imgPath = imgPath.replaceAll("^/", ""); // Remove leading slash from the image path


            String fullImageUrl = baseUrl + "/" + imgPath;
            Picasso.get().load(fullImageUrl).into(imageView);

            editTextProName.setText(name);
            editTextProDes.setText(desc);
            editTextProPrice.setText(price);
            spinnerCategory.getId();
        }

        btnUpdateProduct = (Button) findViewById(R.id.btnUpdateDish);
        btnBackInUpdateProduct = (ImageButton) findViewById(R.id.btnBackInUpdateProduct);

        btnBackInUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the LayoutInflater instance
                LayoutInflater li = getLayoutInflater();
                //Getting the View object as defined in the update_product_toast.xml
                // toastUpdateProduct: id of LinearLayout in update_product_toast.xml
                View layout = li.inflate(R.layout.update_product_toast,(ViewGroup) findViewById(R.id.toastUpdateProduct));

                //set a textview at runtime to the custom toast textview
                TextView text =(TextView)layout.findViewById(R.id.txt_update_product);

                //Creating the Toast object
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP |Gravity.FILL_HORIZONTAL,0,20);
                toast.setView(layout);
                toast.show();
            }
        });
    }
    private void initialize(){
        imageView = (ImageView) findViewById(R.id.imgUpdateFood);
        spinnerCategory = findViewById(R.id.spinner_update_product);
        editTextProName = findViewById(R.id.updateDishName);
        editTextProPrice = findViewById(R.id.updateDishPrice);
        editTextProDes = findViewById(R.id.updateDishDescr);
        intent = getIntent();
        loader = new LoaderComponent(UpdateProductActivity.this);
        loader.start();
        initSpinnerCategory();
    }

    private void initSpinnerCategory() {
        GetCategoryTask task = new GetCategoryTask();
        task.setOnLoadedListener(list -> {
            spinnerCategory.setVisibility(View.VISIBLE);
            categoryAdapter = new ArrayAdapter<>(UpdateProductActivity.this, android.R.layout.simple_spinner_item, list);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(categoryAdapter);
            loader.end();
            if(product != null) {
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getId().equals(product.getCategoryId())) {
                        spinnerCategory.setSelection(i);
                        break;
                    }
                }
            }
        });
        task.execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CATEGORIES__GET));
    }
}