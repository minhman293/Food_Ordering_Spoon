package com.man293.food_ordering_spoon.data;

import com.man293.food_ordering_spoon.models.CartItem;
import com.man293.food_ordering_spoon.models.Product;

import java.util.ArrayList;

public class FakeData {
    public FakeData() {}
    public static ArrayList<CartItem> getCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem(1, "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 3, 1, 1));
        cartItems.add(new CartItem(2, "product_2", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 4, 2, 2));
        cartItems.add(new CartItem(3, "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 5, 3, 3));
        cartItems.add(new CartItem(4, "product_4", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 7, 2, 2));
        cartItems.add(new CartItem(5, "product_5", "Chicken", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 10, 3, 2));
        return cartItems;
    }
    public static ArrayList<Product> getProductItems() {
        ArrayList<Product> productItems = new ArrayList<>();
        productItems.add(new Product(1, "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 3, 1));
        productItems.add(new Product(2, "product_2", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 4, 2));
        productItems.add(new Product(3, "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 5, 3));
        productItems.add(new Product(4, "product_4", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 7, 2));
        productItems.add(new Product(5, "product_5", "Chicken", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 10, 3));
        return productItems;
    }

}
