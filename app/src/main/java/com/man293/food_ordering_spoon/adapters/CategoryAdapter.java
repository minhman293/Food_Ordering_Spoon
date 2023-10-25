package com.man293.food_ordering_spoon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.Categories;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Categories> {

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Categories> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_selected,parent,false);
        TextView tvselected = convertView.findViewById(R.id.tv_selected);

        Categories categories = this.getItem(position);
        if(categories != null)
        {
            tvselected.setText(categories.getNamecategory());
        }
        return convertView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        TextView tvcategory = convertView.findViewById(R.id.tv_category);

        Categories categories = this.getItem(position);
        if(categories != null)
        {
            tvcategory.setText(categories.getNamecategory());
        }
        return convertView;
    }
}
