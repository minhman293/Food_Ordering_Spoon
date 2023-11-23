package com.man293.food_ordering_spoon.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CartInteractiveTasks;
import com.man293.food_ordering_spoon.views.components.DialogComponent;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.models.CartItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        ViewHolder holder;
        CartItem product = getItem(position);
         if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent,false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.productImageSrc);
            holder.nameView = convertView.findViewById(R.id.productName);
            holder.descView = convertView.findViewById(R.id.productDesc);
            holder.quantityView = convertView.findViewById(R.id.quantity);
            holder.priceView = convertView.findViewById(R.id.productPrice);
            holder.btnDelete = convertView.findViewById(R.id.delete_button);
            holder.btnDec = convertView.findViewById(R.id.decButton);
            holder.btnInc =convertView.findViewById(R.id.incButton);
            convertView.setTag(holder);
         } else  {
             holder = (ViewHolder) convertView.getTag();
         }
         Context context = getContext();
         String imgUrl = context.getString(R.string.BASE_URL) + context.getString(R.string.PUBLIC_IMAGES, product.getImageSrc());
         Glide.with(context).load(imgUrl).into(holder.imageView);

//        holder.imageView.setImageResource(
//                getContext().getResources().getIdentifier(product.getImageSrc(), "drawable", getContext().getPackageName())
//        );
        holder.nameView.setText(product.getName());
        holder.descView.setText(product.getDesc());
        holder.quantityView.setText(String.valueOf(product.getQuantity()));
        holder.priceView.setText(CurrencyUtils.format(product.getPrice()));
        holder.btnDelete.setOnClickListener(v -> {confirmDelete(position);});
        holder.btnDec.setOnClickListener(v -> { changeQuantity(position, -1);  });
        holder.btnInc.setOnClickListener(v -> { changeQuantity(position, 1); });
        return convertView;
    }

    private class ViewHolder {
        ShapeableImageView imageView;
        TextView nameView, descView, quantityView, priceView,btnDec, btnInc;
        ImageButton btnDelete;

        public ViewHolder(){}

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
            if(currentQuantity == 1) return;
            CartInteractiveTasks.AddTask addTask = new CartInteractiveTasks.AddTask(getItem(position).getId(), currentQuantity);
            int finalCurrentQuantity = currentQuantity;
            addTask.setOnAddedListener(isAdded -> {
                if(isAdded) {
                    getItem(position).setQuantity(finalCurrentQuantity);
                    /* for update ui in cart fragment */
                    if(onItemChangedListener != null) {
                        onItemChangedListener.onItemChanged(position);
                    }
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
            // TODO : REPLACE USER ID
            final String userId = "655a3582cd47699385f49e81";
            addTask.execute(getContext().getString(R.string.BASE_URL)+ getContext().getString(R.string.API_ADD_TO_CART__POST, userId));
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
            // add 0 === remove
            CartInteractiveTasks.AddTask removeTask = new CartInteractiveTasks.AddTask(getItem(position).getId(), 0);
            removeTask.setOnAddedListener(isRemove -> {
                if(isRemove) {
                    remove(getItem(position));
                    /** for update ui in cart fragment */
                    if(onItemChangedListener != null) {
                        onItemChangedListener.onItemChanged(position);
                    }
                } else {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
            Context context = getContext();
            // TODO : REPLACE USER ID
            final String userId = "655a3582cd47699385f49e81";
            removeTask.execute(context.getString(R.string.BASE_URL) + context.getString(R.string.API_ADD_TO_CART__POST, userId ));
        });
        deleteDialog.show();
    }
    public interface OnItemChangedListener {
        void onItemChanged(int position);
    }
}
