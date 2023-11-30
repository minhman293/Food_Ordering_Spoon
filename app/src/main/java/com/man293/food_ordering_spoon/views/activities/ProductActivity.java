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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CartInteractiveTasks;
import com.man293.food_ordering_spoon.models.HomeProduct;
import com.man293.food_ordering_spoon.models.User;
import com.squareup.picasso.Picasso;

/**TODO: LE NGOC HAO + Truong Minh Man */
public class ProductActivity extends AppCompatActivity {

    Button button;
    AppCompatButton btnBack;
    ImageView imgviewDetailProduct;
    TextView tvDetailProdName, tvDetailProdDesc, tvDetailProdPrice;
    Intent intent;
    EditText qtyEdit;
    AppCompatButton incBtn, decBtn;

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
                User currentUser = User.getCurrentUser(ProductActivity.this);
                if(currentUser == null) {
                    Toast.makeText(ProductActivity.this, "Login required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                HomeProduct product = (HomeProduct) intent.getSerializableExtra("product");
                int qty = Integer.parseInt(String.valueOf(qtyEdit.getText()));
                CartInteractiveTasks.AddTask task = new CartInteractiveTasks.AddTask(product.getHomeProductId(), qty);
                task.setOnAddedListener(isAdded -> {
                    if(isAdded) {
                        showToastMessage();
                    } else {
                        Toast.makeText(ProductActivity.this, "Something went wont!", Toast.LENGTH_SHORT).show();
                    }
                });
                task.execute(getString(R.string.BASE_URL) + getString(R.string.API_ADD_TO_CART__POST, currentUser.getId() ));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        incBtn.setOnClickListener(v -> changeQuantity(1));
        decBtn.setOnClickListener(v-> changeQuantity(-1));

    }
    private void showToastMessage() {
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

    private void changeQuantity(int n) {
        try {
            int currentQty = Integer.parseInt(String.valueOf(qtyEdit.getText()));
            qtyEdit.setText(String.valueOf(currentQty + n > 0 ? currentQty + n : 1));
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialize() {
        button = (Button) findViewById(R.id.button);
        btnBack = (AppCompatButton) findViewById(R.id.btn_back);
        imgviewDetailProduct = (ImageView) findViewById(R.id.imgview_detail_product);
        tvDetailProdName = (TextView) findViewById(R.id.tv_detail_product_name);
        tvDetailProdDesc = (TextView) findViewById(R.id.tv_detail_product_desc);
        tvDetailProdPrice = (TextView) findViewById(R.id.tv_detail_product_price);
        qtyEdit = findViewById(R.id.edit_text_qty);
        incBtn = findViewById(R.id.btn_add);
        decBtn = findViewById(R.id.btn_minus);
        intent = getIntent();
    }

}