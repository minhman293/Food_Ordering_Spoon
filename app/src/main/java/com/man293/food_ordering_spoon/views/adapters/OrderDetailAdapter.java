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
import com.man293.food_ordering_spoon.models.OrderDetailItem;
import com.man293.food_ordering_spoon.models.Product;
import com.man293.food_ordering_spoon.models.ProductHistory;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.views.activities.UpdateProductActivity;

import java.util.ArrayList;
import java.lang.ref.WeakReference;

public class OrderDetailAdapter extends ArrayAdapter<OrderDetailItem> {

    private ArrayList<OrderDetailItem> product_item;


    public OrderDetailAdapter(@NonNull Context context,@NonNull ArrayList<OrderDetailItem> products) {
        super(context, R.layout.order_detail_admin_item,  products);
        this.product_item = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OrderDetailItem item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_detail_admin_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.name)).setText(item.getTitle());
        ((TextView) convertView.findViewById(R.id.price)).setText(CurrencyUtils.format(item.getPrice()));
        ((TextView) convertView.findViewById(R.id.qtt)).setText(item.getQtt());
        double qtt = Double.parseDouble(item.getQtt());
        ((TextView) convertView.findViewById(R.id.total)).setText(CurrencyUtils.format((item.getPrice()) * qtt));

        return convertView;
    }

    private static String truncateString(String input, int maxLength) {
        if (input.length() > maxLength) {
            // Nếu chuỗi có độ dài lớn hơn maxLength, cắt và thêm dấu "..."
            return input.substring(0, maxLength - 3) + "...";
        } else {
            // Nếu chuỗi có độ dài bé hơn hoặc bằng maxLength, giữ nguyên
            return input;
        }
    }
}
