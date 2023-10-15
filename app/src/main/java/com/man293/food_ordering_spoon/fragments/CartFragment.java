package com.man293.food_ordering_spoon.fragments;
import android.content.Intent;
import android.os.Bundle;
//import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.man293.food_ordering_spoon.R;
import com.man293.food_ordering_spoon.activities.PaymentActivity;
import com.man293.food_ordering_spoon.adapters.CartAdapter;
import com.man293.food_ordering_spoon.components.ButtonComponent;
import com.man293.food_ordering_spoon.components.ListViewComponent;
import com.man293.food_ordering_spoon.data.FakeData;
import com.man293.food_ordering_spoon.models.CartItem;
import com.man293.food_ordering_spoon.models.Currency;

import java.util.ArrayList;

/**TODO: DIEP VAN TY */

public class CartFragment extends Fragment {
    private ListViewComponent listViewProduct;
    private TextView selectedItemTextView, deliveryFeeTextView, totalPriceTextView, subtotalPriceTextView;
    private ButtonComponent checkoutButton;
    private CartAdapter cartAdapter;

    public CartFragment() {
    }

    // Man: "error about NonNull, i can't fix"
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }

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

        initListView();

        changeToPayment();

        /* update ui when change quantity or delete */
        cartAdapter.setOnItemChangedListener(new CartAdapter.OnItemChangedListener() {
            @Override
            public void onItemChanged(int position) {
//                Toast.makeText(getContext(),"UPDATED", Toast.LENGTH_SHORT).show();
                prepareCheckout();
            }
        });

        prepareCheckout();

        return view;
    }

    private void changeToPayment() {
        checkoutButton.setOnClickListener(v -> {
            Intent paymentIntent = new Intent(getContext(), PaymentActivity.class);
            paymentIntent.putExtra("TOTAL_PRICE", subtotalPriceTextView.getText().toString());
            startActivity(paymentIntent);
        });
    }

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

        selectedItemTextView.setText("Selected items (" + String.valueOf(selectedItems) + ")");
        deliveryFeeTextView.setText(Currency.format(deliveryFee));
        totalPriceTextView.setText(Currency.format(totalPrice));
        subtotalPriceTextView.setText(Currency.format(deliveryFee + totalPrice));

    }

    private void initListView() {
        cartAdapter = new CartAdapter(getContext(), FakeData.getCartItems());
        listViewProduct.setAdapter(cartAdapter);
        listViewProduct.setFullHeight();
    }
}