package com.man293.food_ordering_spoon.views.components;

import android.content.Context;
import android.util.AttributeSet;

import com.man293.food_ordering_spoon.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class ButtonComponent extends AppCompatButton {
    public ButtonComponent(@NonNull Context context) {
        super(context);
    }

    public ButtonComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled) {
            setBackground(getResources().getDrawable(R.drawable.background_orange_round));
            setTextColor(getResources().getColor(R.color.white));
        } else {
            setBackground(getResources().getDrawable(R.drawable.background_disable_button));
            setTextColor(getResources().getColor(R.color.gray));
        }
    }
}
