package com.man293.food_ordering_spoon.views.fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.asynctasks.CartInteractiveTasks;
import com.man293.food_ordering_spoon.views.activities.PaymentActivity;
import com.man293.food_ordering_spoon.views.activities.ViewHistory;
import com.man293.food_ordering_spoon.views.adapters.CartAdapter;
import com.man293.food_ordering_spoon.views.components.ButtonComponent;
import com.man293.food_ordering_spoon.views.components.ListViewComponent;
import com.man293.food_ordering_spoon.models.CartItem;
import com.man293.food_ordering_spoon.utils.CurrencyUtils;

public class CartFragment extends Fragment {
    private ListViewComponent listViewProduct;
    private TextView selectedItemTextView, deliveryFeeTextView, totalPriceTextView, subtotalPriceTextView;
    private ButtonComponent checkoutButton;
    private CartAdapter cartAdapter;

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
        ImageButton btnHistory = view.findViewById(R.id.btn_history);

        // go to History page
        btnHistory.setOnClickListener(v -> startActivity(new Intent(getContext(), ViewHistory.class)));
        initListView();
        changeToPayment();
        return view;
    }
    private void initListView() {
        // TODO : REPLACE USER ID
        final String userId = "655a3582cd47699385f49e81";
        CartInteractiveTasks.GetTask task = new CartInteractiveTasks.GetTask();
        task.setOnCartLoadedListener((cart) -> {
            if(cart != null) {
                cartAdapter = new CartAdapter(getContext(), cart);
                listViewProduct.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                listViewProduct.setFullHeight();

                cartAdapter.setOnItemChangedListener(position -> {
                    prepareCheckout();
                });
                prepareCheckout();
            }
        });
        task.execute(getString(R.string.BASE_URL) + getString(R.string.API_GET_CART_BY_USER__GET, userId ));
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
            deliveryFee = 10;
            checkoutButton.setEnabled(true);
        } else {
            checkoutButton.setEnabled(false);
        }
        selectedItemTextView.setText("Selected items (" + selectedItems + ")");
        deliveryFeeTextView.setText(CurrencyUtils.format(deliveryFee));
        totalPriceTextView.setText(CurrencyUtils.format(totalPrice));
        subtotalPriceTextView.setText(CurrencyUtils.format(deliveryFee + totalPrice));
    }
    private void changeToPayment() {
        checkoutButton.setOnClickListener(v -> {
            Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
            paymentIntent.putExtra("TOTAL_PRICE", subtotalPriceTextView.getText().toString());
            startActivity(paymentIntent);
        });
    }
}