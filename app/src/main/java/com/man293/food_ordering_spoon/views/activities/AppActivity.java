package com.man293.food_ordering_spoon.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.views.fragments.AdminFragment;
import com.man293.food_ordering_spoon.views.fragments.CartFragment;
import com.man293.food_ordering_spoon.views.fragments.HomeFragment;
import com.man293.food_ordering_spoon.views.fragments.ProfileFragment;
import com.man293.food_ordering_spoon.models.User;

/**TODO: DIEP VAN TY */

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
        final int ADMIN = 4;

        MeowBottomNavigation navigation = findViewById(R.id.MeowBottomNavigation);

        navigation.add(new MeowBottomNavigation.Model(HOME, R.drawable.bottom_nav_home_icon));
        navigation.add(new MeowBottomNavigation.Model(CART, R.drawable.bottom_nav__shopping_cart_icon));
        navigation.add(new MeowBottomNavigation.Model(PROFILE, R.drawable.bottom_nav_persion_icon));

        /** Check is an administrator */
        SharedPreferences sharedPreferences = getSharedPreferences("auth_info", MODE_PRIVATE );
        String userJson = sharedPreferences.getString("current_user", null );

        if(userJson != null) {
            Gson gson = new Gson();
            User currentUser = gson.fromJson(userJson, User.class);
            if(currentUser != null && currentUser.isAdmin()) {
                navigation.add(new MeowBottomNavigation.Model(ADMIN, R.drawable.bottom_nav_admin_icon));
            }
        }

        navigation.setOnClickMenuListener(item -> { });

        navigation.setOnShowListener(item -> getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.App,
                        item.getId() == HOME ? HomeFragment.class
                                : (item.getId() == CART ? CartFragment.class
                                    : item.getId() == PROFILE ? ProfileFragment.class
                                        : AdminFragment.class)
                        , null)
                .commit());

        navigation.setOnReselectListener(item -> {  });

        navigation.show(HOME, false);
    }
}