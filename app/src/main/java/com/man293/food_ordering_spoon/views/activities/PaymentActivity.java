package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CartInteractiveTasks;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    private String paymentSelected;
    private TextView priceTextView, orderDateTextView;
    private EditText addressEdit;
    private ArrayList<RadioButton> paymentRadioButtons ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        priceTextView = findViewById(R.id.totalPrice);
        orderDateTextView = findViewById(R.id.orderDate);
        addressEdit = findViewById(R.id.address_edit);
        paymentRadioButtons = new ArrayList<>();
        paymentRadioButtons.add(findViewById(R.id.visaRadioButton));
        paymentRadioButtons.add(findViewById(R.id.mastercardRadioButton));
        paymentRadioButtons.add(findViewById(R.id.paypalRadioButton));
        paymentRadioButtons.add(findViewById(R.id.deliveryRadioButton));

        priceTextView.setText( CurrencyUtils.format(getIntent().getDoubleExtra("TOTAL_PRICE",0)) );

        /** ORDER DATE */
        SimpleDateFormat dfm = new SimpleDateFormat("dd-MM-yyyy");
        orderDateTextView.setText(dfm.format(new Date()));


        /** GO BACK WHEN CLICK ARROW LEFT BUTTON */
        ((ImageButton) findViewById(R.id.goBackButton)).setOnClickListener( v -> {
            super.onBackPressed();
        });

        /** SELECT PAYMENT METHOD */
        handleSelectPaymentMethod();
        handleUnselectOtherPaymentMethod();
        paymentRadioButtons.get(0).performClick();

        /** BUY  */
        AppCompatButton buyNowButton =  findViewById(R.id.buyNowButton);
        buyNowButton.setOnClickListener( v -> {

            User currentUser = User.getCurrentUser(PaymentActivity.this);
            if(currentUser == null) {
                Toast.makeText(PaymentActivity.this, "LOGIN REQUIRED!", Toast.LENGTH_SHORT).show();
                return;
            }
//            final String userId = "655a3582cd47699385f49e81";
            final String address = addressEdit.getText().toString().trim().isEmpty() ? currentUser.getAddress() : addressEdit.getText().toString().trim();

            final double price = getIntent().getDoubleExtra("TOTAL_PRICE",0);
            final String itemIds = getIntent().getStringExtra("CART_ITEM_IDS");

            String url = getString(R.string.BASE_URL) + getString(R.string.API_CREATE_BILL__POST, currentUser.getId());
            new CartInteractiveTasks.CreateBillTask(itemIds, price, address, "A new order", "A new order needs to be delivered to " + address ).setOnBillCreated(isCreated -> {
                BottomSheetDialog dialog = new BottomSheetDialog(PaymentActivity.this, R.style.bottom_sheet_dialog_theme);
                dialog.setContentView(R.layout.dialog_thanks);
                dialog.show();

                dialog.findViewById(R.id.close_dialog_button).setOnClickListener(view -> {
                    dialog.dismiss();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("isPaid", true);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                });
            }).execute(url);


        });
    }

    private void handleSelectPaymentMethod() {
        LinearLayout[] paymentMethods = {
                findViewById(R.id.visa),
                findViewById(R.id.mastercard),
                findViewById(R.id.paypal),
                findViewById(R.id.delivery)
        };

        for (LinearLayout method:  paymentMethods  ) {
            /** CLICK LINEARLAYOUT TO CHECK RADIO BUTTON  */
            method.setOnClickListener(v -> {
                int currentRadioId = getResources().getIdentifier(
                        getResources().getResourceEntryName(v.getId()) + "RadioButton",
                        "id",
                        getPackageName());
                RadioButton radioButton =  findViewById(currentRadioId);
                 // radioButton.setChecked(true);
                radioButton.performClick(); // auto invoke handlePaymentSelect()
                String value = getResources().getResourceEntryName(currentRadioId);
                paymentSelected = value.substring(0, value.indexOf("RadioButton"));
//                Toast.makeText(PaymentActivity.this, paymentSelected, Toast.LENGTH_SHORT).show();
            });
        }
    }

    /** UNCHECK RADIO BUTTON */
    private void handleUnselectOtherPaymentMethod() {
        for(RadioButton current : paymentRadioButtons) {
            current.setOnClickListener(v-> {
                for(RadioButton p : paymentRadioButtons){
                    p.setChecked(false);
                }
                current.setChecked(true);
            });
        }
    }
}