package com.man293.food_ordering_spoon.views.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.views.activities.AdminProductActivity;
import com.man293.food_ordering_spoon.views.activities.CreateProductActivity;
import com.man293.food_ordering_spoon.views.activities.StatisticalData;

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
            startActivity(new Intent(getContext(), StatisticalData.class));
        });

        tvFoods.setOnClickListener(v-> {
            startActivity(new Intent(getContext(), AdminProductActivity.class));
        });

        btnAddNew.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CreateProductActivity.class));
        });

        return view ;
    }
}