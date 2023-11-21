package com.man293.food_ordering_spoon.utils;

public class CurrencyUtils {
    private static String symbol = "$";

    public CurrencyUtils() {
    }

    public static String getSymbol() {
        return symbol;
    }

    public static void setSymbol(String symbol) {
        CurrencyUtils.symbol = symbol;
    }
//    @SuppressLint("DefaultLocale")
    public  static String format(double price) {
        return symbol + String.format( "%.2f",price);
    }
}
