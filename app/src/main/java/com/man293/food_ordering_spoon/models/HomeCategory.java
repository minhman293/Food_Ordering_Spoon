package com.man293.food_ordering_spoon.models;

public class HomeCategory {
    private String id, name;
    private boolean isSelected;

    public HomeCategory(String id, String name) {
        this.id = id;
        this.name = name;
        this.isSelected = false; // Initialize isSelected to false
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}