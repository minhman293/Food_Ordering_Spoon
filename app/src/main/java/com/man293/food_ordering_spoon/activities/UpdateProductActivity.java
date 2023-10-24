package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;

/** todo: truong minh man*/

public class UpdateProductActivity extends AppCompatActivity {

    Button btnUpdateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        btnUpdateProduct = (Button) findViewById(R.id.btnUpdateDish);
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
}