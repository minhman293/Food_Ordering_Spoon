package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.models.ProductHistory;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.views.activities.UpdateProductActivity;

import java.util.ArrayList;
import java.lang.ref.WeakReference;

public class ViewHistoryDetailsAdapter extends ArrayAdapter<ProductHistory> {

    private ArrayList<ProductHistory> product_item;
    private WeakReference<ViewHistoryDetailsAdapter.IOnCheckListener> listener;


    public ViewHistoryDetailsAdapter(@NonNull Context context,@NonNull ArrayList<ProductHistory> products) {
        super(context, R.layout.history_card,  products);
        this.product_item = products;
    }

    public ViewHistoryDetailsAdapter setOnCheckListener(ViewHistoryDetailsAdapter.IOnCheckListener listener) {
        this.listener = new WeakReference<>(listener);
        return this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductHistory product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_card, parent, false);
        }

        Context context = getContext();
        ShapeableImageView imageView = convertView.findViewById(R.id.image_history1);
        String url = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, product.getImageSrc());
        Glide.with(context).load(url).into(imageView);

        ((TextView) convertView.findViewById(R.id.name_product1)).setText(product.getName());
        ((TextView) convertView.findViewById(R.id.price_number_product1)).setText(CurrencyUtils.format(product.getPrice()));
        ((TextView) convertView.findViewById(R.id.qtt1)).setText(product.getQtt());

        return convertView;
    }

    public interface IOnCheckListener {
        void callback(boolean isChecked, String id);
    }
}
