package com.man293.food_ordering_spoon.views.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetHomeProductJSON;
import com.man293.food_ordering_spoon.views.adapters.HomeProductAdapter;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.HomeProduct;

import java.util.ArrayList;

/**TODO: TRUONG MINH MAN */

public class HomeFragment extends Fragment {

    AppCompatButton btnCatAll, btnCatBeef, btnCatPizza, btnCatNoodle;
    TextView txtDetail;
    public ListViewComponent lvHomeProduct;
    public ArrayList<HomeProduct> arrHomeProduct;
    public HomeProductAdapter homeProductAdapter;

    View view;
    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        // initialize
        initialize();

        // handle category button click
        handleAllCategoryClick();

        // new GetProductJson().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,getString(R.string.BASE_URL) + getString(R.string.API_GET_PRODUCTS__GET));
        new GetHomeProductJSON(this).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_PRODUCTS__GET));

        return view;
    }

    private void initialize() {
        lvHomeProduct = (ListViewComponent) view.findViewById(R.id.lvHomeProduct);
        // array to contain list product
        arrHomeProduct = new ArrayList<>();

        // Create the adapter
        homeProductAdapter = new HomeProductAdapter(getContext(), R.layout.single_home_product, arrHomeProduct);

        btnCatAll = view.findViewById(R.id.btn_category_all);
        btnCatBeef = view.findViewById(R.id.btn_category_beef);
        btnCatNoodle = view.findViewById(R.id.btn_category_noodles);
        btnCatPizza = view.findViewById(R.id.btn_category_pizza);

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


