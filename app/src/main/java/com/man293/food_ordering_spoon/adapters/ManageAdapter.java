package com.man293.food_ordering_spoon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.AdminProductActivity;
import com.man293.food_ordering_spoon.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.Currency;
import com.man293.food_ordering_spoon.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ManageAdapter extends ArrayAdapter<Product> {


    private ListViewComponent parent;
    private ArrayList<Product> product_item;

    public ManageAdapter(@NonNull Context context,@NonNull ArrayList<Product> products) {
        super(context, R.layout.ad_product_item,  products);
        this.product_item = products;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        this.parent = (ListViewComponent) parent;
        Product product = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ad_product_item,parent,false);
        }
        ((ShapeableImageView) convertView.findViewById(R.id.productImageSrc)).setImageResource(
                getContext().getResources().getIdentifier(product.getImageSrc(), "drawable", getContext().getPackageName())
        );
        ((TextView) convertView.findViewById(R.id.productName)).setText(product.getName());
        ((TextView) convertView.findViewById(R.id.productDesc)).setText(product.getDesc());
        ((TextView) convertView.findViewById(R.id.productPrice)).setText(Currency.format(product.getPrice()));
        return convertView;
    }
}
