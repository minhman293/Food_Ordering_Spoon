package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.HomeProduct;
import com.man293.food_ordering_spoon.views.fragments.HomeFragment;
import com.squareup.picasso.Picasso;

/**TODO: LE NGOC HAO + Truong Minh Man */
public class ProductActivity extends AppCompatActivity {

    Button button;
    AppCompatButton btnBack;
    ImageView imgviewDetailProduct;
    TextView tvDetailProdName, tvDetailProdDesc, tvDetailProdPrice;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initialize();

        // get product object from home and set data to view
        if (intent != null && intent.hasExtra("product")) {
            HomeProduct product = (HomeProduct) intent.getSerializableExtra("product");

            String prod_name = product.getHomeProductName();
            String prod_desc = product.getHomeProductDescription();
            String prod_price = String.valueOf(product.getHomeProductPrice());

            // get img file and change to url to get from internet
            String prod_img = product.getHomeProductImg();
            String imgPath = getString(R.string.PUBLIC_IMAGES, prod_img);
            String baseUrl = getString(R.string.BASE_URL);

            baseUrl = baseUrl.replaceAll("/$", ""); // Remove trailing slash from the base URL
            imgPath = imgPath.replaceAll("^/", ""); // Remove leading slash from the image path

            // Concatenate base URL with image path
            String fullImageUrl = baseUrl + "/" + imgPath;

            Picasso.get().load(fullImageUrl).into(imgviewDetailProduct);

            tvDetailProdName.setText(prod_name);
            tvDetailProdDesc.setText(prod_desc);
            tvDetailProdPrice.setText("$" + prod_price);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the LayoutInflater instance
                LayoutInflater li = getLayoutInflater();
                //Getting the View object as defined in the customtoast.xml file
                View layout = li.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.custom)); // id của LinearLayout trong custom_toast.xml

                //set a textview at runtime to the custom toast textview
                TextView text =(TextView)layout.findViewById(R.id.text);

                //Creating the Toast object
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP |Gravity.FILL_HORIZONTAL,0,20); // chỉnh
                toast.setView(layout);
                toast.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductActivity.this, HomeFragment.class));
            }
        });
    }

    private void initialize() {
        button = (Button) findViewById(R.id.button);
        btnBack = (AppCompatButton) findViewById(R.id.btn_back);
        imgviewDetailProduct = (ImageView) findViewById(R.id.imgview_detail_product);
        tvDetailProdName = (TextView) findViewById(R.id.tv_detail_product_name);
        tvDetailProdDesc = (TextView) findViewById(R.id.tv_detail_product_desc);
        tvDetailProdPrice = (TextView) findViewById(R.id.tv_detail_product_price);
        intent = getIntent();
    }

}