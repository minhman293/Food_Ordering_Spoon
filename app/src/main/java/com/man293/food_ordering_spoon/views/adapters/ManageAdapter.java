package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.views.activities.UpdateProductActivity;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.models.Product;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ManageAdapter extends ArrayAdapter<Product> {


//    private ListViewComponent parent;
    private ArrayList<Product> product_item;
    private WeakReference<IOnCheckListener> listener;


    public ManageAdapter(@NonNull Context context,@NonNull ArrayList<Product> products) {
        super(context, R.layout.ad_product_item,  products);
        this.product_item = products;
    }

    public ManageAdapter setOnCheckListener(IOnCheckListener listener) {
        this.listener = new WeakReference<>(listener);
        return this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        this.parent = (ListViewComponent) parent;
        Product product = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ad_product_item,parent,false);
        }
//        ((ShapeableImageView) convertView.findViewById(R.id.productImageSrc)).setImageResource(
//                getContext().getResources().getIdentifier(product.getImageSrc(), "drawable", getContext().getPackageName())
//        );
        Context context = getContext();
        ShapeableImageView imageView =  convertView.findViewById(R.id.productImageSrc);
        String url = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, getItem(position).getImageSrc());
        Glide.with(context).load( url).into(imageView);
        ((TextView) convertView.findViewById(R.id.productName)).setText(product.getName());
        ((TextView) convertView.findViewById(R.id.productDesc)).setText(product.getDesc());
        ((TextView) convertView.findViewById(R.id.productPrice)).setText(CurrencyUtils.format(product.getPrice()));

        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Log.d("CLICKED", getItem(position).getName());
            IOnCheckListener cb = listener.get();
            if(cb != null) {
                cb.callback(isChecked, getItem(position).getId());
            } else {
                Log.d("CALLBACK", "NULL");
            }
        });

        // Go to Update Product Activity
         convertView.findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 getContext().startActivity(new Intent(getContext(), UpdateProductActivity.class));
             }
         });
        return convertView;
    }

    public interface IOnCheckListener {
        void callback(boolean isChecked, String id);
    }
}
