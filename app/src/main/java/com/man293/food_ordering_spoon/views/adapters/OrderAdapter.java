package com.man293.food_ordering_spoon.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.OrderItem;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;

import org.threeten.bp.LocalDateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OrderAdapter extends ArrayAdapter<OrderItem> {
    public OrderAdapter(@NonNull Context context, @NonNull ArrayList<OrderItem> objects) {
        super(context, R.layout.order_item, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_item, parent, false);
            holder.imageView = convertView.findViewById(R.id.icon_money_bill);
            holder.titleView = convertView.findViewById(R.id.title_message_bill);
            holder.dateView = convertView.findViewById(R.id.time_message_bill);
            holder.idView = convertView.findViewById(R.id.code_message_bill);
            holder.priceView = convertView.findViewById(R.id.price_message_bill);

            convertView.setTag(holder);
        }else  {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.titleView.setText("From: " + getItem(position).getTitle());
        holder.dateView.setText("Date: " + formatDate(getItem(position).getOrderDate()));
        holder.idView.setText("Code: " + getItem(position).getId().substring(getItem(position).getId().length() / 2));
        holder.priceView.setText(CurrencyUtils.format(getItem(position).getPrice()));

        return convertView;
    }
    private String formatDate(LocalDateTime date) {
        return date.getHour() + ":" + date.getMinute() + "\t" + date.getDayOfMonth() + "-" + date.getMonthValue() +  "-" + date.getYear();
    }
    private class ViewHolder {
        ImageView imageView;
        TextView titleView, idView, priceView, dateView;
        public ViewHolder() {}
    }
}
