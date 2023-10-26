package com.man293.food_ordering_spoon.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.components.DialogComponent;
import com.man293.food_ordering_spoon.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.Currency;
import com.man293.food_ordering_spoon.models.CartItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private CartAdapter.OnItemChangedListener onItemChangedListener;
    private ListViewComponent parent;
    private ArrayList<CartItem> cartItems;


    public CartAdapter(@NonNull Context context, @NonNull ArrayList<CartItem> items) {
        super(context, R.layout.cart_item, items);
        this.cartItems = items;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.parent = (ListViewComponent) parent;
        CartItem product = getItem(position);
         if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent,false);
         }

        ((ShapeableImageView) convertView.findViewById(R.id.productImageSrc)).setImageResource(
                getContext().getResources().getIdentifier(product.getImageSrc(), "drawable", getContext().getPackageName())
        );
        ((TextView) convertView.findViewById(R.id.productName)).setText(product.getName());
        ((TextView) convertView.findViewById(R.id.productDesc)).setText(product.getDesc());
        ((TextView) convertView.findViewById(R.id.quantity)).setText(String.valueOf(product.getQuantity()));
        ((TextView) convertView.findViewById(R.id.productPrice)).setText(Currency.format(product.getPrice()));

        convertView.findViewById(R.id.delete_button).setOnClickListener(v -> {
            confirmDelete(position);
        });

        convertView.findViewById(R.id.decButton).setOnClickListener(v -> {
            changeQuantity(position, -1);
        });

        convertView.findViewById(R.id.incButton).setOnClickListener(v -> {
            changeQuantity(position, 1);
        });
        return convertView;
    }

    @Override
    public void remove(@Nullable CartItem object) {
        super.remove(object);
        this.parent.setFullHeight();
        notifyDataSetChanged();
    }

    public ArrayList<CartItem> getCartItems() {
        return  this.cartItems;
    }

    public void setOnItemChangedListener(CartAdapter.OnItemChangedListener listener) {
        this.onItemChangedListener = listener;
    }

    private void changeQuantity(int position, int n) {
        try {
            int currentQuantity = getItem(position).getQuantity();
            currentQuantity = currentQuantity + n > 0 ? currentQuantity + n : 1;
            getItem(position).setQuantity(currentQuantity);

            /** for update ui in cart fragment */
            if(onItemChangedListener != null) {
                onItemChangedListener.onItemChanged(position);
            }

            notifyDataSetChanged();
        } catch (Exception ex) {
            Log.d("CHANGE QUANTITY", ex.getMessage());
        };
    }
    private void confirmDelete(int position ) {
        DialogComponent deleteDialog = new DialogComponent(
                getContext(),
                R.style.bottom_sheet_dialog_theme,
                "Are you sure you want to delete this item from your cart?"
        );

        deleteDialog.setOnConfirmListener(() -> {
            remove(getItem(position));
            /** for update ui in cart fragment */
            if(onItemChangedListener != null) {
                onItemChangedListener.onItemChanged(position);
            }
        });
        deleteDialog.show();
    }
    public interface OnItemChangedListener {
        void onItemChanged(int position);
    }
}
