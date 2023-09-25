package com.man293.food_ordering_spoon.components;

import android.content.Context;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;

public class SpoonDialog extends BottomSheetDialog {
    public SpoonDialog(@NonNull Context context) {
        super(context);
    }

    public SpoonDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected SpoonDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
