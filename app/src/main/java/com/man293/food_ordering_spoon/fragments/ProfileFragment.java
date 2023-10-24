package com.man293.food_ordering_spoon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.EditProfile;

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

        return  view;
    }
}