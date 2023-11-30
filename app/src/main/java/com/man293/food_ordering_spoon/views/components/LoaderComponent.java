package com.man293.food_ordering_spoon.views.components;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.man293.food_ordering_spoon.R;
import com.airbnb.lottie.LottieAnimationView;

import androidx.appcompat.app.AppCompatActivity;

public class LoaderComponent {
    private LottieAnimationView loader;
    public LoaderComponent(View v) {
        this.loader = v.findViewById(R.id.loader);
    }
    public LoaderComponent(AppCompatActivity activity) { this.loader = activity.findViewById(R.id.loader);}
    public LoaderComponent(Activity activity) {this.loader = activity.findViewById(R.id.loader);}
    public void start() {
        if(loader != null) {
            loader.setVisibility(View.VISIBLE);
            if(!loader.isAnimating()) {
                loader.playAnimation();
            }
        }
    }
    public void end() {
        if(loader != null) {
            if(loader.isAnimating()) {
                loader.pauseAnimation();
            }
            loader.setVisibility(View.GONE);
        }
    }
}
