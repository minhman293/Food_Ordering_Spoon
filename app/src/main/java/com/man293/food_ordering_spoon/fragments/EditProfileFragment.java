package com.man293.food_ordering_spoon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.ProductActivity;

public class EditProfileFragment extends Fragment{
    public EditProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_edit_profile, container, false);
        return view;
    }
}
