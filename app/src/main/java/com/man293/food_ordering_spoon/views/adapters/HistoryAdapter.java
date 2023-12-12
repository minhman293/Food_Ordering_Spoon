package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.History;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<History> historyList;

    public HistoryAdapter(Context context, List<History> orderList) {
        this.context = context;
        this.historyList = orderList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public Object getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tạo và gán dữ liệu cho mỗi item trong ListView
        View itemView = LayoutInflater.from(context).inflate(R.layout.view_history_card, parent, false);

        TextView tvOrderId = itemView.findViewById(R.id.tv_order1);
        TextView tvOrderDate = itemView.findViewById(R.id.tv_date_order1);
        TextView tvProductName1 = itemView.findViewById(R.id.name_product1);
        TextView tvProductName2 = itemView.findViewById(R.id.name_product2);
        TextView tvProductPrice1 = itemView.findViewById(R.id.price_number_product1);
        TextView tvProductPrice2 = itemView.findViewById(R.id.price_number_product2);
        TextView tvQuantity1 = itemView.findViewById(R.id.qtt1);
        TextView tvQuantity2 = itemView.findViewById(R.id.qtt2);
        TextView tvDeliveryPrice = itemView.findViewById(R.id.delivery_price_product1);
        TextView tvTotalPrice = itemView.findViewById(R.id.total);
        ShapeableImageView imageView1 =  convertView.findViewById(R.id.image_history1);
        ShapeableImageView imageView2 =  convertView.findViewById(R.id.image_history2);

        History history = historyList.get(position);

        tvOrderId.setText("Order ID: " + history.getOrderID());
        tvOrderDate.setText("Date: " + history.getOrderDate());
        tvProductName1.setText(history.getListProduct().get(position).getName());
        tvProductName2.setText(history.getListProduct().get(position).getName());
        tvProductPrice1.setText("$" + history.getListProduct().get(position).getPrice());
        tvProductPrice2.setText("$" + history.getListProduct().get(position).getPrice());
        tvQuantity1.setText(String.valueOf(history.getListProduct().get(position).getQtt()));
        tvQuantity2.setText(String.valueOf(history.getListProduct().get(position).getQtt()));
        tvDeliveryPrice.setText("$0.00");
        tvTotalPrice.setText("$" + history.getPrice());
        String url1 = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, history.getListProduct().get(position).getImageSrc());
        Glide.with(context).load( url1).into(imageView1);
        String url2 = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, history.getListProduct().get(position).getImageSrc());
        Glide.with(context).load( url2).into(imageView2);

        return itemView;
    }
}