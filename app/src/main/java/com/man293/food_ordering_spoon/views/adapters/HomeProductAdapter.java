package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.man293.food_ordering_spoon.asynctasks.CartInteractiveTasks;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.views.activities.ProductActivity;
import com.man293.food_ordering_spoon.models.HomeProduct;
import com.man293.food_ordering_spoon.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeProductAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<HomeProduct> homeProducts;
    ViewHolder holder;

    public HomeProductAdapter(Context context, int layout, ArrayList<HomeProduct> homeProducts) {
        this.context = context;
        this.layout = layout;
        this.homeProducts = homeProducts;
    }

    @Override
    public int getCount() {
        return homeProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ShapeableImageView img;
        TextView name, description, price;
        ImageButton addToCartBtn;

        public ViewHolder() {
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.tv_home_single_product_name);
            holder.description = (TextView) convertView.findViewById(R.id.tv_home_single_product_desc);
            holder.price = (TextView) convertView.findViewById(R.id.tv_home_single_product_price);
            holder.img = (ShapeableImageView) convertView.findViewById(R.id.img_home_single_product);
            holder.addToCartBtn = convertView.findViewById(R.id.btn_add_home_single_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeProduct homeProduct = homeProducts.get(position);
        holder.name.setText(homeProduct.getHomeProductName());
        holder.description.setText(homeProduct.getHomeProductDescription());
        holder.price.setText("$" + Double.toString(homeProduct.getHomeProductPrice()));

        String imgName = homeProduct.getHomeProductImg();
        String imgPath = context.getString(R.string.PUBLIC_IMAGES, imgName);
        String baseUrl = context.getString(R.string.BASE_URL);

        baseUrl = baseUrl.replaceAll("/$", ""); // Remove trailing slash from the base URL
        imgPath = imgPath.replaceAll("^/", ""); // Remove leading slash from the image path


        // Concatenate base URL with image path
        String fullImageUrl = baseUrl + "/" + imgPath;

        Picasso.get().load(fullImageUrl).into(holder.img);

        holder.addToCartBtn.setOnClickListener(v -> {
            User currentUser = User.getCurrentUser(context);
            if(currentUser == null) {
                Toast.makeText(context, "Login required!", Toast.LENGTH_SHORT).show();
                return;
            };
            CartInteractiveTasks.AddTask task = new CartInteractiveTasks.AddTask(homeProduct.getHomeProductId(), 1);
            task.setOnAddedListener((isAdded) -> {
                if(isAdded) showToastMessage(context);
                else Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
            });
            task.execute(context.getString(R.string.BASE_URL) + context.getString(R.string.API_ADD_TO_CART__POST, currentUser.getId()));
        });

        convertView.findViewById(R.id.layoutHomeProductItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("product", (Serializable) homeProduct);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public void showToastMessage(Context context) {
        if(context == null) {
            Log.d("ADD_TO_CART", "FAIL");
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
//        TextView text = view.findViewById(R.id.text);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP |Gravity.FILL_HORIZONTAL,0,20);
        toast.setView(view);
        toast.show();
        Log.d("ADD_TO_CART", "SUCCESS");
    }

    public void updateData(ArrayList<HomeProduct> newData) {
        homeProducts.clear();
        homeProducts.addAll(newData);
        notifyDataSetChanged();
    }
}
