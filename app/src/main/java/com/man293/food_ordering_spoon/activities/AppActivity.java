package com.man293.food_ordering_spoon.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.fragments.CartFragment;
import com.man293.food_ordering_spoon.fragments.HomeFragment;
import com.man293.food_ordering_spoon.fragments.ProfileFragment;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        // INIT BOTTOM NAVIGATION
        initBottomNavigation();
    }

    private void initBottomNavigation() {

        final int HOME = 1;
        final int CART = 2;
        final int PROFILE = 3;

        MeowBottomNavigation navigation = findViewById(R.id.MeowBottomNavigation);

        navigation.add(new MeowBottomNavigation.Model(HOME, R.drawable.bottom_nav_home_icon));
        navigation.add(new MeowBottomNavigation.Model(CART, R.drawable.bottom_nav__shopping_cart_icon));
        navigation.add(new MeowBottomNavigation.Model(PROFILE, R.drawable.bottom_nav_persion_icon));

        navigation.setOnClickMenuListener(item -> { });

        navigation.setOnShowListener(item -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.App,
                        item.getId() == HOME ? HomeFragment.class
                                : (item.getId() == CART ? CartFragment.class : ProfileFragment.class)
                        , null)
                .commit());

        navigation.setOnReselectListener(item -> {  });

        navigation.show(HOME, false);
    }
}