package com.man293.food_ordering_spoon.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.GetHomeCategoryTask;
import com.man293.food_ordering_spoon.asynctasks.GetHomeProductTask;
import com.man293.food_ordering_spoon.models.HomeCategory;
import com.man293.food_ordering_spoon.views.adapters.HomeCategoryAdapter;
import com.man293.food_ordering_spoon.views.adapters.HomeProductAdapter;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.HomeProduct;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import java.util.ArrayList;

/**TODO: TRUONG MINH MAN */
public class HomeFragment extends Fragment {

    TextView txtDetail;
    public ListViewComponent lvHomeProduct;
    public RecyclerView rvHomeCategory;
    public ArrayList<HomeProduct> arrHomeProduct, originalArrHomeProduct;
    public ArrayList<HomeCategory> arrHomeCategory;
    public HomeProductAdapter homeProductAdapter;
    public HomeCategoryAdapter homeCategoryAdapter;

    public LoaderComponent loader;

    View view;
    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        // initialize
        initialize();

        // handle category button click
        homeCategoryAdapter.setOnItemClickListener(new HomeCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                handleCategoryClick(position);
            }
        });

        new GetHomeProductTask(this).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_PRODUCTS__GET));
        new GetHomeCategoryTask(this).execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CATEGORIES__GET));

        return view;
    }

    private void initialize() {
        lvHomeProduct = (ListViewComponent) view.findViewById(R.id.lvHomeProduct);
        rvHomeCategory = (RecyclerView) view.findViewById(R.id.rvHomeCategory);
        rvHomeCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // array to contain list product
        arrHomeProduct = new ArrayList<>();
        originalArrHomeProduct = new ArrayList<>();
        arrHomeCategory = new ArrayList<>();
        loader = new LoaderComponent(view);
        loader.start();

        // Create the adapter
        homeProductAdapter = new HomeProductAdapter(getContext(), R.layout.single_home_product, arrHomeProduct);
        homeCategoryAdapter = new HomeCategoryAdapter(getContext(), arrHomeCategory);

    }

    private void handleCategoryClick(int position) {
        HomeCategory selectedCategory = arrHomeCategory.get(position);

        // Reset all categories
        for (int i = 0; i < arrHomeCategory.size(); i++) {
            arrHomeCategory.get(i).setSelected(false);
        }

        // Set the selected category
        selectedCategory.setSelected(true);

        // Notify the adapter that the data has changed
        homeCategoryAdapter.notifyDataSetChanged();

        // Update the detail text based on the selected category
        updateDetailText(selectedCategory.getName());

        // update product when click category
        filterProductsByCategory(selectedCategory.getId());
    }

    private void filterProductsByCategory(String categoryId) {
        ArrayList<HomeProduct> filteredProducts = new ArrayList<>();
        for (HomeProduct product : originalArrHomeProduct) {
            if (product.getHomeProductCateId().equals(categoryId)) {
                filteredProducts.add(product);
            }
        }
        // Update the product adapter with the filtered products
        homeProductAdapter.updateData(filteredProducts);
        lvHomeProduct.setFullHeight();
    }

    private void updateDetailText(String category) {
        txtDetail = view.findViewById(R.id.txt_detail);
        txtDetail.setText("Results for " + category);
    }

}