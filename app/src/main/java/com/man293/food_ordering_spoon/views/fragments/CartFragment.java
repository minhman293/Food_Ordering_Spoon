package com.man293.food_ordering_spoon.views.fragments;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CartInteractiveTasks;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.views.activities.PaymentActivity;
import com.man293.food_ordering_spoon.views.activities.ViewHistory;
import com.man293.food_ordering_spoon.views.adapters.CartAdapter;
import com.man293.food_ordering_spoon.views.components.ButtonComponent;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.CartItem;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;
import com.man293.food_ordering_spoon.views.components.LoaderComponent;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private ListViewComponent listViewProduct;
    private TextView selectedItemTextView, deliveryFeeTextView, totalPriceTextView, subtotalPriceTextView;
    private ButtonComponent checkoutButton;
    private CartAdapter cartAdapter;
    private LoaderComponent loader;
    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        listViewProduct = view.findViewById(R.id.cartItems);
        selectedItemTextView = view.findViewById(R.id.selectedItems);
        deliveryFeeTextView = view.findViewById(R.id.deliveryFee);
        totalPriceTextView = view.findViewById(R.id.totalPrice);
        subtotalPriceTextView = view.findViewById(R.id.subtotalPrice);
        checkoutButton = view.findViewById(R.id.cartCheckoutBtn);
        loader = new LoaderComponent(view);
        loader.start();
        ImageButton btnHistory = view.findViewById(R.id.btn_history);

        // go to History page
        btnHistory.setOnClickListener(v -> startActivity(new Intent(getContext(), ViewHistory.class)));
        initListView();
        changeToPayment();
        return view;
    }
    private void initListView() {
        // TODO : !REPLACE USER ID
        User currentUser = User.getCurrentUser(getContext());
        if(currentUser == null) {
            Toast.makeText(getContext(), "LOGIN REQUIRED!", Toast.LENGTH_SHORT).show();
            return;
        }
        CartInteractiveTasks.GetTask task = new CartInteractiveTasks.GetTask();
        task.setOnCartLoadedListener((cart) -> {
            Context context = getContext();
            if(cart != null && context != null) {
                cartAdapter = new CartAdapter(context, cart);
                listViewProduct.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                listViewProduct.setFullHeight();
                loader.end();
                cartAdapter.setOnItemChangedListener(position -> {
                    prepareCheckout();
                });
                prepareCheckout();
            }
        });
        task.execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CART_BY_USER__GET, currentUser.getId() ));
    }
    @SuppressLint("SetTextI18n")
    private void prepareCheckout() {
        int selectedItems = 0;
        double deliveryFee = 0;
        double totalPrice = 0;

        if (cartAdapter != null && cartAdapter.getCount() > 0) {
            for (CartItem item : cartAdapter.getCartItems()) {
                selectedItems += item.getQuantity();
                totalPrice += item.getQuantity() * item.getPrice();
            }
            deliveryFee = 1;
            checkoutButton.setEnabled(true);
        } else {
            checkoutButton.setEnabled(false);
        }
        selectedItemTextView.setText("Selected items (" + selectedItems + ")");
        deliveryFeeTextView.setText(CurrencyUtils.format(deliveryFee));
        totalPriceTextView.setText(CurrencyUtils.format(totalPrice));
        subtotalPriceTextView.setText(CurrencyUtils.format(deliveryFee + totalPrice));
        subtotalPriceTextView.setTag( deliveryFee + totalPrice);
    }
    private void changeToPayment() {
        checkoutButton.setOnClickListener(v -> {
            try {
                if(cartAdapter == null || cartAdapter.getCartItems().size() == 0) return;
                ArrayList<String> ids  = new ArrayList<>();
                for(CartItem item: cartAdapter.getCartItems()) {
                    ids.add(item.getId());
                }
                Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
                paymentIntent.putExtra("TOTAL_PRICE", Double.parseDouble(String.valueOf(subtotalPriceTextView.getTag())));
                paymentIntent.putExtra("CART_ITEM_IDS", ids.toString());
                startActivityForResult(paymentIntent, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getBooleanExtra("isPaid", false)) {
                cartAdapter.clear();
                cartAdapter.notifyDataSetChanged();
                listViewProduct.setFullHeight();
                prepareCheckout();
            }
        }
    }
}