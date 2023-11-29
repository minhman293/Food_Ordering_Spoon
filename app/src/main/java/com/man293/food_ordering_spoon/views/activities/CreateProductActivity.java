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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CreateProductTask;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.utils.FileUtils;

import java.io.File;

public class CreateProductActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private File selectedFile;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        btnAddProduct = (Button) findViewById(R.id.btnAddNewDish);
        ImageButton selectImageButton = findViewById(R.id.imgAddFood);

        // lấy đường dẫn file từ thiết bị
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnAddProduct.setOnClickListener(v -> {
            if(selectedFile == null) {
                Toast.makeText(this, "Select picture before submit!", Toast.LENGTH_LONG).show();
                return;
            }
            CreateProductTask createProductTask =  new CreateProductTask(CreateProductActivity.this, selectedFile,
                    new Product("1", "filename.png", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 3, "1")
            );
            createProductTask.setOnProductCreated(product -> {
                if(product != null) {
                    showMessage("Add this dish successfully!");
                } else {
                    showMessage("Something went wrong!");
                }
            });
            createProductTask.execute( getString(R.string.BASE_URL) + getString(R.string.API_CREATE_PRODUCT__POST ));
        });
    }


    //tạo lấy file sau khi có đường dẫn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            selectedFile = FileUtils.uriToFile(CreateProductActivity.this,selectedImageUri);
        }
    }

    private void showMessage(String message) {
        //Creating the LayoutInflater instance
        LayoutInflater li = getLayoutInflater();
        //Getting the View object as defined in the add_product_toast.xml
        // toastAddProduct: id of LinearLayout in add_product_toast.xml
        View layout = li.inflate(R.layout.add_product_toast,(ViewGroup) findViewById(R.id.toastAddProduct));

        //set a textview at runtime to the custom toast textview
        TextView text =(TextView)layout.findViewById(R.id.txt_add_product);
        text.setText(message);
        //Creating the Toast object
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP |Gravity.FILL_HORIZONTAL,0,20);
        toast.setView(layout);
        toast.show();
    }
}