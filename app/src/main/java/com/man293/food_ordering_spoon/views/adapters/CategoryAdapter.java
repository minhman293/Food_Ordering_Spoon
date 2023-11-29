package com.man293.food_ordering_spoon.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_selected,parent,false);
        TextView tvselected = convertView.findViewById(R.id.tv_selected);

        Category categories = this.getItem(position);
        if(categories != null)
        {
            tvselected.setText(categories.getName());
        }
        return convertView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        TextView tvcategory = convertView.findViewById(R.id.tv_category);

        Category categories = this.getItem(position);
        if(categories != null)
        {
            tvcategory.setText(categories.getName());
        }
        return convertView;
    }
}
