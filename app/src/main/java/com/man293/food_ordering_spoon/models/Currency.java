package com.man293.food_ordering_spoon.models;

public class Currency {
    private static String symbol = "$";

    public Currency() {
    }

    public static String getSymbol() {
        return symbol;
    }

    public static void setSymbol(String symbol) {
        Currency.symbol = symbol;
    }
    public  static String format(double price) {
        return symbol + String.format( "%.2f",price);
    }
}
