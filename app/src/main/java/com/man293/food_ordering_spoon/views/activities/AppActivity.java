package com.man293.food_ordering_spoon.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.SendTokenToServerTask;
import com.man293.food_ordering_spoon.views.fragments.AdminFragment;
import com.man293.food_ordering_spoon.views.fragments.CartFragment;
import com.man293.food_ordering_spoon.views.fragments.HomeFragment;
import com.man293.food_ordering_spoon.views.fragments.ProfileFragment;
import com.man293.food_ordering_spoon.models.User;

import java.util.HashMap;
import java.util.Map;

public class AppActivity extends AppCompatActivity {
    private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        this.currentUser = User.getCurrentUser(AppActivity.this);
        Log.d("CURRENT_USER", currentUser.toString());
        // INIT BOTTOM NAVIGATION
        initBottomNavigation();

        /* FIREBASE TOKEN */
        if(currentUser != null && currentUser.isAdmin()) {
            FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("REGIS_TOKEN_FAILED", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Log.d("_TOKEN", token);
                    sendRegistrationToServer(token);
//                    Toast.makeText(AppActivity.this, token, Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("firebase_token", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("fb_token", token);
                    editor.apply();
                });
        }
    }
    private void sendRegistrationToServer(String token) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("_token", token);
            String json = new Gson().toJson(payload);
            String userId = currentUser.getId();
            String url = getString(R.string.BASE_URL) + getString(R.string.API_SEND_TOKEN__POST, userId);
            new SendTokenToServerTask(json).execute(url);
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
        if(currentUser != null && currentUser.isAdmin()) {
            navigation.add(new MeowBottomNavigation.Model(ADMIN, R.drawable.bottom_nav_admin_icon));
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