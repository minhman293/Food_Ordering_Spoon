package com.man293.food_ordering_spoon.views.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CreateProductTask;
import com.man293.food_ordering_spoon.asynctasks.GetCategoryTask;
import com.man293.food_ordering_spoon.models.Category;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.utils.FileUtils;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import java.io.File;
import java.util.ArrayList;

public class CreateProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private File selectedFile;
    private Button btnAddProduct;
    private ShapeableImageView selectImageButton;
    private Spinner spinnerCategory;
    private EditText  editName, editPrice, editDesc;

    private ArrayAdapter<Category> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        btnAddProduct = (Button) findViewById(R.id.btnAddNewDish);
        selectImageButton = findViewById(R.id.imgAddFood);
        spinnerCategory = findViewById(R.id.inputDishCategory);
        editName = findViewById(R.id.inputDishName);
        editPrice = findViewById(R.id.inputDishPrice);
        editDesc = findViewById(R.id.inputDishDescr);

        initSpinnerCategory();
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnAddProduct.setOnClickListener(v -> {
            try {
                if(selectedFile == null) {
                    Toast.makeText(this, "Select picture before submit!", Toast.LENGTH_LONG).show();
                    return;
                }
                String productName = $(editName);
                double productPrice = Double.parseDouble($(editPrice));
                String productDesc = $(editDesc);
                String categoryId = ((Category) spinnerCategory.getSelectedItem()).getId();
                if(productName.isEmpty() || productDesc.isEmpty()) {
                    Toast.makeText(CreateProductActivity.this,"Enter name and description", Toast.LENGTH_SHORT).show();
                    return;
                }

                CreateProductTask createProductTask =  new CreateProductTask(CreateProductActivity.this, selectedFile,
                        // automatically generate id and filename
                        new Product("1", "filename.png", productName, productDesc, productPrice, categoryId )
                );
                createProductTask.setOnProductCreated(product -> {
                    if(product != null) {
                        showMessage("Add this dish successfully!");
                    } else {
                        Toast.makeText(CreateProductActivity.this,"Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
                createProductTask.execute( getString(R.string.BASE_URL) + getString(R.string.API_CREATE_PRODUCT__POST ));
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                Toast.makeText(CreateProductActivity.this, "Price must be a number!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(CreateProductActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedFile = FileUtils.uriToFile(CreateProductActivity.this,selectedImageUri);
            selectImageButton.setImageBitmap(FileUtils.getBitmapFromFile(CreateProductActivity.this, selectedImageUri));
        }
    }
    private void initSpinnerCategory() {
        LoaderComponent loader = new LoaderComponent(CreateProductActivity.this);
        loader.start();
        spinnerCategory.setVisibility(View.INVISIBLE);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CreateProductActivity.this, categoryAdapter.getItem(i).getId(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {  }
        });

        GetCategoryTask task = new GetCategoryTask();
        task.setOnLoadedListener(list -> {
            spinnerCategory.setVisibility(View.VISIBLE);
            categoryAdapter = new ArrayAdapter<>(CreateProductActivity.this, android.R.layout.simple_spinner_item, list);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(categoryAdapter);
            loader.end();
        });
        task.execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CATEGORIES__GET));
    }
    private String $(EditText editText) {
        String text = String.valueOf(editText.getText()).trim();
        return text.equals("null") ? "" : text;
    }

    private void showMessage(String message) {
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.add_product_toast,(ViewGroup) findViewById(R.id.toastAddProduct));
        TextView text = layout.findViewById(R.id.txt_add_product);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP |Gravity.FILL_HORIZONTAL,0,20);
        toast.setView(layout);
        toast.show();
    }
}