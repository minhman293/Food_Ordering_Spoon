package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.man293.food_ordering_spoon.R;

/**TODO: DIEP VAN TY */

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        /** GO BACK WHEN CLICK ARROW LEFT BUTTON */
        ((ImageButton) findViewById(R.id.goBackButton)).setOnClickListener( v -> {
            super.onBackPressed();
            onBackPressed();
        });

        /** SELECT PAYMENT METHOD */
        handleSelectPaymentMethod();
        handleUnselectOtherPaymentMethod();

        /** COMPLETE ORDER  */
        AppCompatButton buyNowButton =  findViewById(R.id.buyNowButton);
        buyNowButton.setOnClickListener( v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(PaymentActivity.this, R.style.bottom_sheet_dialog_theme);
            dialog.setContentView(R.layout.dialog_thanks);
            dialog.show();

            ((ImageButton) dialog.findViewById(R.id.close_dialog_button)).setOnClickListener( view -> {
                dialog.hide();
            });
        });
    }

    /**
     * CLICK LINEARLAYOUT TO CHECK RADIO BUTTON
     * */
    private void handleSelectPaymentMethod() {
        LinearLayout[] paymentMethods = {
                findViewById(R.id.visa),
                findViewById(R.id.mastercard),
                findViewById(R.id.paypal),
                findViewById(R.id.delivery)
        };

        for (LinearLayout method:  paymentMethods  ) {
            method.setOnClickListener(v -> {
                int currentRadioId = getResources().getIdentifier(
                        getResources().getResourceEntryName(v.getId()) + "RadioButton",
                        "id",
                        getPackageName());
                RadioButton radioButton =  findViewById(currentRadioId);
                 // radioButton.setChecked(true);
                radioButton.performClick(); // auto invoke handlePaymentSelect()
            });
        }
    }

    /** UNCHECK RADIO BUTTON
     * BECAUSE THE RADIO GROUP CONTAINS LINEARLAYOUTS */
    private void handleUnselectOtherPaymentMethod() {
        RadioButton[] paymentMethods =  {
                findViewById(R.id.visaRadioButton),
                findViewById(R.id.mastercardRadioButton),
                findViewById(R.id.paypalRadioButton),
                findViewById(R.id.deliveryRadioButton),
        }; 
        for(RadioButton currentMethod : paymentMethods) {
            currentMethod.setOnClickListener(v-> {
                for(RadioButton p : paymentMethods ){
                    if(p != currentMethod) {
                        p.setChecked(false);
                    }
                }
            });
        }
    }
}