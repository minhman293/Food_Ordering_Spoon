package com.man293.food_ordering_spoon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;

import android.widget.Button;
import android.widget.TextView;


import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.ProductActivity;

/**TODO: TRUONG MINH MAN & LE NGOC HAO  */

public class HomeFragment extends Fragment {

    AppCompatButton btnCatAll, btnCatBeef, btnCatPizza, btnCatNoodle;
    TextView txtDetail;
    RelativeLayout rltLayProd1;
    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        rltLayProd1 = view.findViewById(R.id.product1);
        rltLayProd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductActivity.class);
                startActivity(intent);
            }
        });


        btnCatAll = view.findViewById(R.id.btn_category_all);
        btnCatBeef = view.findViewById(R.id.btn_category_beef);
        btnCatNoodle = view.findViewById(R.id.btn_category_noodles);
        btnCatPizza = view.findViewById(R.id.btn_category_pizza);

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

        return view;
    }

    public void Unclick(View v){
        Button btn = (Button) v;
        int textColor = ContextCompat.getColor(v.getContext(), R.color.green);
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