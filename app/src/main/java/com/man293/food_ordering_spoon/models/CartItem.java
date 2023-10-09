package com.man293.food_ordering_spoon.models;

public class CartItem extends Product {
    private int quantity;
    public CartItem() {
        super();
    }

    public CartItem(int id, int imageSrc, String name, String desc, double price, int categoryId, int quantity) {
        super(id, imageSrc, name, desc, price, categoryId);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
