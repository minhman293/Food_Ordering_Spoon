package com.man293.food_ordering_spoon.views.components;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.man293.food_ordering_spoon.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class DialogComponent extends BottomSheetDialog {

    OnChangeListener deleteListener;
    private TextView messageTextView;
    private AppCompatButton deleteButton, cancelButton;
    private ImageButton closeButton;
    private LottieAnimationView animationRecycle;


    public DialogComponent(@NonNull Context context, int theme, String message) {
        super(context, theme);
        setContentView(R.layout.dialog_delete);
        initComponent(message);
    }

    public void setOnConfirmListener(DialogComponent.OnChangeListener listener) {
        this.deleteListener = listener;
    }

    private void initComponent(String message) {
        animationRecycle = this.findViewById(R.id.animationRecycle);
        deleteButton = this.findViewById(R.id.cofirm_delete_button);
        cancelButton = this.findViewById(R.id.cofirm_cancel_button);
        closeButton = this.findViewById(R.id.close_dialog_button);
        messageTextView = this.findViewById(R.id.confirm_dialog_msg);

        messageTextView.setText(message);

        deleteButton.setOnClickListener( v -> {
            deleteButton.setEnabled(false);
            cancelButton.setEnabled(false);
           if(deleteListener != null) {
               this.deleteListener.OnChange();
           }

            animationRecycle.playAnimation();

            /** HIDE DIALOG AFTER 1.8s */
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                if(deleteButton.isShown()) {
                    this.dismiss();
                }
            }, 1800);
        });

        cancelButton.setOnClickListener(v -> {
            this.dismiss();
        });

        closeButton.setOnClickListener(v -> {
            this.dismiss();
        });
    }
    public interface OnChangeListener {
        void OnChange();
    }
}
