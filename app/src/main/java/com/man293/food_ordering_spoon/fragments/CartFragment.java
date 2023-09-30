package com.man293.food_ordering_spoon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.PaymentActivity;

public class CartFragment extends Fragment {
    public CartFragment() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ImageButton deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
           showDialog();
        });


        AppCompatButton cartCheckoutBtn = view.findViewById(R.id.cartCheckoutBtn);
        cartCheckoutBtn.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PaymentActivity.class));
        });


        return view;
    }

    private void showDialog( ) {
        BottomSheetDialog deleteDialog  = new BottomSheetDialog(getContext() , R.style.bottom_sheet_dialog_theme);
        deleteDialog.setContentView(R.layout.dialog_confirm);
        ImageButton closeButton = deleteDialog.findViewById(R.id.close_dialog_button);
        closeButton.setOnClickListener(v -> {
            deleteDialog.hide();
        });
        deleteDialog.show();
    }
}