package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.man293.food_ordering_spoon.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    private String paymentSelected;
    private TextView priceTextView, orderDateTextView;
    private ArrayList<RadioButton> paymentRadioButtons ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        priceTextView = findViewById(R.id.totalPrice);
        orderDateTextView = findViewById(R.id.orderDate);
        paymentRadioButtons = new ArrayList<>();
        paymentRadioButtons.add(findViewById(R.id.visaRadioButton));
        paymentRadioButtons.add(findViewById(R.id.mastercardRadioButton));
        paymentRadioButtons.add(findViewById(R.id.paypalRadioButton));
        paymentRadioButtons.add(findViewById(R.id.deliveryRadioButton));

        /** TOTAL PRICE */
        priceTextView.setText(getIntent().getStringExtra("TOTAL_PRICE"));

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
            BottomSheetDialog dialog = new BottomSheetDialog(PaymentActivity.this, R.style.bottom_sheet_dialog_theme);
            dialog.setContentView(R.layout.dialog_thanks);
            dialog.show();

            dialog.findViewById(R.id.close_dialog_button).setOnClickListener(view -> {
                dialog.hide();
            });
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