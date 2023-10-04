package com.man293.food_ordering_spoon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.ProductActivity;

/**TODO: TRUONG MINH MAN & LE NGOC HAO  */

public class HomeFragment extends Fragment {
    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        /** START PRODUCT ACTIVITY  */
        RelativeLayout productCard1 = view.findViewById(R.id.product1);
        productCard1.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ProductActivity.class));
        });

        return view;
    }
}