package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.History;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ManageHistoryAdapter extends ArrayAdapter<History> {

    private static final int TYPE_SINGLE_PRODUCT = 0;
    private static final int TYPE_MULTIPLE_PRODUCTS = 1;

    private ArrayList<History> historyItems;

    public ManageHistoryAdapter(@NonNull Context context, @NonNull ArrayList<History> histories) {
        super(context, 0, histories);
        this.historyItems = histories;
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        History history = getItem(position);
        return (history != null && history.getListProduct().size() > 1) ? TYPE_MULTIPLE_PRODUCTS : TYPE_SINGLE_PRODUCT;
    }

    @Override
    public int getViewTypeCount() {
        return 2; // Số loại layout
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int viewType = getItemViewType(position);
        History history = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            switch (viewType) {
                case TYPE_SINGLE_PRODUCT:
                    convertView = inflater.inflate(R.layout.view_history_card_1, parent, false);
                    break;
                case TYPE_MULTIPLE_PRODUCTS:
                    convertView = inflater.inflate(R.layout.view_history_card, parent, false);
                    break;
            }
        }

        if (history != null) {
            Context context = getContext();
            ShapeableImageView imageView1 = convertView.findViewById(R.id.image_history1);
            ShapeableImageView imageView2 = convertView.findViewById(R.id.image_history2);

            ((TextView) convertView.findViewById(R.id.tv_order1)).setText(history.getOrderID());
            ((TextView) convertView.findViewById(R.id.tv_date_order1)).setText(history.getOrderDate());
            ((TextView) convertView.findViewById(R.id.add_order)).setText(history.getOrderAdd());
            ((TextView) convertView.findViewById(R.id.total)).setText(CurrencyUtils.format(history.getPrice()));

            // Tránh lỗi nếu danh sách sản phẩm có ít hơn 2 phần tử
            if (history.getListProduct().size() > 0) {
                String url = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, history.getListProduct().get(0).getImageSrc());
                Glide.with(context).load(url).into(imageView1);

                // Tránh lỗi nếu danh sách sản phẩm có ít hơn 2 phần tử
                if (history.getListProduct().size() > 1) {
                    String url2 = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, history.getListProduct().get(1).getImageSrc());
                    Glide.with(context).load(url2).into(imageView2);
                }
            }

            // Tránh lỗi nếu danh sách sản phẩm có ít hơn 2 phần tử
            if (history.getListProduct().size() > 0) {
                ((TextView) convertView.findViewById(R.id.name_product1)).setText(history.getListProduct().get(0).getName());
                ((TextView) convertView.findViewById(R.id.qtt1)).setText(history.getListProduct().get(0).getQtt());
                ((TextView) convertView.findViewById(R.id.price_number_product1)).setText(CurrencyUtils.format(history.getListProduct().get(0).getPrice()));
            }

            // Tránh lỗi nếu danh sách sản phẩm có ít hơn 2 phần tử
            if (history.getListProduct().size() > 1) {
                ((TextView) convertView.findViewById(R.id.name_product2)).setText(history.getListProduct().get(1).getName());
                ((TextView) convertView.findViewById(R.id.qtt2)).setText(history.getListProduct().get(1).getQtt());
                ((TextView) convertView.findViewById(R.id.price_number_product2)).setText(CurrencyUtils.format(history.getListProduct().get(1).getPrice()));
            }

        }

        convertView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(history.getOrderID());
            }
        });

        return convertView;
    }

    public interface ItemClickListener {
        void onItemClick(String orderID);
    }
}
