package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.man293.food_ordering_spoon.R;

/**TODO: TRUONG MINH MAN */

public class Intro1Activity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME = 5000;

    Animation topAnima, bottomAnima;
    ImageView logo, brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // full screen at intro
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro1);

        // load animation
        topAnima = AnimationUtils.loadAnimation(this, R.anim.top_logo_animation);
        bottomAnima = AnimationUtils.loadAnimation(this, R.anim.bottom_logo_animation);

        // set animation
        logo = findViewById(R.id.img_logo_intro);
        brand = findViewById(R.id.img_spoon_intro);
        logo.setAnimation(topAnima);
        brand.setAnimation(bottomAnima);

        // next to intro2
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Intro1Activity.this, Intro2Activity.class));
                finish();
            }
        }, SPLASH_SCREEN_TIME);
    }
}