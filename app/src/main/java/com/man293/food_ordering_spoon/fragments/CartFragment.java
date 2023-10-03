package com.man293.food_ordering_spoon.fragments;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.PaymentActivity;

public class CartFragment extends Fragment {
    public CartFragment() { }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
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
        AppCompatButton deleteButton, cancelButton;
        ImageButton closeButton;
        LottieAnimationView animationRecycle;

        BottomSheetDialog deleteDialog  = new BottomSheetDialog(getContext() , R.style.bottom_sheet_dialog_theme);
        deleteDialog.setContentView(R.layout.dialog_confirm);

        animationRecycle = deleteDialog.findViewById(R.id.animationRecycle);
        deleteButton = deleteDialog.findViewById(R.id.cofirm_delete_button);
        cancelButton = deleteDialog.findViewById(R.id.cofirm_cancel_button);
        closeButton = deleteDialog.findViewById(R.id.close_dialog_button);

        cancelButton.setOnClickListener(v -> {
            deleteDialog.dismiss();
        });

        deleteButton.setOnClickListener( v -> {
            animationRecycle.playAnimation();

            /** HIDE DELETE DIALOG AFTER 1.8s */
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if(deleteButton.isShown()) {
                    deleteDialog.dismiss();
                }
            }, 1800);
        });

        closeButton.setOnClickListener(v -> {
            deleteDialog.dismiss();
        });
        deleteDialog.show();
    }
}