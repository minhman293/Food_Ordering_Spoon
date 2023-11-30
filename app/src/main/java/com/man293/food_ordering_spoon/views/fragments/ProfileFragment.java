package com.man293.food_ordering_spoon.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.views.activities.EditProfileActivity;
import com.man293.food_ordering_spoon.views.activities.LoginActivity;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {  }

    private Button bt_edit_profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Button bt_edit = view.findViewById(R.id.button_edit);
        bt_edit.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), EditProfileActivity.class));
        });

        /** Logout */
        view.findViewById(R.id.button_logout).setOnClickListener(view1 -> {
            if(User.removeCurrentUser(getContext())) {
                Toast.makeText(getContext(), "logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), LoginActivity.class));
            } else  {
                Toast.makeText(getContext(), "Error when logout!", Toast.LENGTH_SHORT).show();
            }
        });
        return  view;
    }
}