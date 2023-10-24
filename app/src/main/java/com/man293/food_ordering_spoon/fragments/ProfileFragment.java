package com.man293.food_ordering_spoon.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.EditProfile;
import com.man293.food_ordering_spoon.activities.Intro2Activity;
import com.man293.food_ordering_spoon.activities.LoginActivity;

/**TODO: LE KIM NAM */
public class ProfileFragment extends Fragment {
    public ProfileFragment() {  }

    private Button bt_edit_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Button bt_edit = view.findViewById(R.id.button_edit);
        bt_edit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), EditProfile.class));
        });

        /** Logout */
        view.findViewById(R.id.button_logout).setOnClickListener(view1 -> {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("auth_info", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("current_user");
            editor.apply();
            Log.i("CURRENT_USER", sharedPreferences.getString("current_user", "Nothing!"));
            startActivity(new Intent(getContext(), LoginActivity.class));
        });

        return  view;
    }
}