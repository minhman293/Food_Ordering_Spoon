package com.man293.food_ordering_spoon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.man293.food_ordering_spoon.R;

/**TODO: LE KIM NAM */
public class ProfileFragment extends Fragment {
    public ProfileFragment() {  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        return  view;
    }
}