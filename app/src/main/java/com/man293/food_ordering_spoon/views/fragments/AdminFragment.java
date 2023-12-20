package com.man293.food_ordering_spoon.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.utils.GoogleSignManager;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;
import com.man293.food_ordering_spoon.views.activities.CreateProductActivity;
import com.man293.food_ordering_spoon.views.activities.LoginActivity;
import com.man293.food_ordering_spoon.views.activities.StatisticalActivity;

public class AdminFragment extends Fragment {
    private TextView tvStatistics, tvFoods;
    private RelativeLayout btnAddNew;
    public AdminFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        tvStatistics = view.findViewById(R.id.tvStatistics);
        tvFoods = view.findViewById(R.id.tvFoods);
        btnAddNew = view.findViewById(R.id.rlAddNew);

        tvStatistics.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), StatisticalActivity.class));
        });

        tvFoods.setOnClickListener(v-> {
            startActivity(new Intent(getContext(), AdminProductActivity.class));
        });

        btnAddNew.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), CreateProductActivity.class));
            startActivityForResult(new Intent(getContext(), CreateProductActivity.class), 0);
        });

        /** Logout */
        view.findViewById(R.id.button_logout).setOnClickListener(view1 -> {
            if(User.removeCurrentUser(getContext())) { // delete data from getSharedPreferences when logging out
                GoogleSignInClient client=  GoogleSignManager.getInstance(getContext()).getClient();
                if(client != null) {
                    client.signOut(); // logout if logged in with google
                }
                Toast.makeText(getContext(), "logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else  {
                Toast.makeText(getContext(), "Error when logout!", Toast.LENGTH_SHORT).show();
            }
        });
        return view ;
    }
}