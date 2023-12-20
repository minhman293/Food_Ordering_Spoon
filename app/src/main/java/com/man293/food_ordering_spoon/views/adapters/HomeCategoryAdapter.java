package com.man293.food_ordering_spoon.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.models.HomeCategory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HomeCategory> homeCategories;
    private OnItemClickListener onItemClickListener;

    public HomeCategoryAdapter(Context context, ArrayList<HomeCategory> homeCategories) {
        this.context = context;
        this.homeCategories = homeCategories;
    }

    @Nullable
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_home_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {
        HomeCategory homeCategory = homeCategories.get(position);
        holder.btn.setText(homeCategory.getName());

        if (homeCategory.isSelected()) {
            // handle selected state
            holder.btn.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.btn.setBackground(ContextCompat.getDrawable(context, R.drawable.bounder_btn_category_hover));
        } else {
            // handle unselected state
            holder.btn.setTextColor(ContextCompat.getColor(context, R.color.jungle_green));
            holder.btn.setBackground(ContextCompat.getDrawable(context, R.drawable.bounder_btn_green));
        }
    }

    @Override
    public int getItemCount() {
        return homeCategories.size();
    }

    // handle click event
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatButton btn;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn_home_category);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}

