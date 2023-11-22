package com.man293.food_ordering_spoon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.ProductActivity;
import com.man293.food_ordering_spoon.adapters.HomeProductAdapter;
import com.man293.food_ordering_spoon.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.HomeProduct;

import java.io.Serializable;
import java.util.ArrayList;

/**TODO: TRUONG MINH MAN */

public class HomeFragment extends Fragment {

    AppCompatButton btnCatAll, btnCatBeef, btnCatPizza, btnCatNoodle;
    TextView txtDetail;
    ListViewComponent lvHomeProduct;
    ArrayList<HomeProduct> arrHomeProduct;
    HomeProductAdapter homeProductAdapter;
    View view;
    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        // initialize
        initialize();

        // add data to array product
        arrHomeProduct.add(new HomeProduct("Spaghetti", "It is a established fact a established that a..", 24, R.drawable.product_1));
        arrHomeProduct.add(new HomeProduct("Spaghetti", "It is a established fact a established that a..", 24, R.drawable.product_2));
        arrHomeProduct.add(new HomeProduct("Spaghetti", "It is a established fact a established that a..", 24, R.drawable.product_3));
        arrHomeProduct.add(new HomeProduct("Spaghetti", "It is a established fact a established that a..", 24, R.drawable.product_4));

        // set adapter for listview
        lvHomeProduct.setAdapter(homeProductAdapter);
        lvHomeProduct.setFullHeight();

        // handle category button click
        handleAllCategoryClick();

        return view;
    }

    private void initialize() {
        lvHomeProduct = (ListViewComponent) view.findViewById(R.id.lvHomeProduct);

        btnCatAll = view.findViewById(R.id.btn_category_all);
        btnCatBeef = view.findViewById(R.id.btn_category_beef);
        btnCatNoodle = view.findViewById(R.id.btn_category_noodles);
        btnCatPizza = view.findViewById(R.id.btn_category_pizza);

        // array to contain list product
        arrHomeProduct = new ArrayList<>();

        // set adapter for list view product
        homeProductAdapter = new HomeProductAdapter(getContext(), R.layout.single_home_product, arrHomeProduct);
    }

    private void handleAllCategoryClick() {
        btnCatAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCategory(v);

                Unclick(btnCatBeef);
                Unclick(btnCatNoodle);
                Unclick(btnCatPizza);
            }
        });

        btnCatPizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCategory(v);
                txtDetail = view.findViewById(R.id.txt_detail);
                txtDetail.setText("Results for pizza");

                Unclick(btnCatBeef);
                Unclick(btnCatAll);
                Unclick(btnCatNoodle);
            }
        });

        btnCatBeef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCategory(v);
                txtDetail = view.findViewById(R.id.txt_detail);
                txtDetail.setText("Results for beef");

                Unclick(btnCatPizza);
                Unclick(btnCatAll);
                Unclick(btnCatNoodle);
            }
        });

        btnCatNoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClickCategory(v);
                txtDetail = view.findViewById(R.id.txt_detail);
                txtDetail.setText("Results for noodle");

                Unclick(btnCatBeef);
                Unclick(btnCatAll);
                Unclick(btnCatPizza);
            }

        });
    }

    public void Unclick(View v){
        Button btn = (Button) v;
        int textColor = ContextCompat.getColor(v.getContext(), R.color.jungle_green);
        btn.setTextColor(textColor);
        btn.setBackground(getResources().getDrawable(R.drawable.bounder_btn_green));
    }

    public void handleClickCategory(View v){
        Button btn = (Button) v;
        int textColor = ContextCompat.getColor(v.getContext(), R.color.white);
        btn.setTextColor(textColor);
        btn.setBackground(getResources().getDrawable(R.drawable.bounder_btn_category_hover));
    };
}