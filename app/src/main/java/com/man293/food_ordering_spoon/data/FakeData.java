package com.man293.food_ordering_spoon.data;

import com.man293.food_ordering_spoon.models.CartItem;
import com.man293.food_ordering_spoon.models.User;
import com.man293.food_ordering_spoon.models.Product;

import java.util.ArrayList;
import java.util.Hashtable;

public class FakeData {
    public FakeData() {}
    public static ArrayList<CartItem> getCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("1", "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 3, "1", 1));
        cartItems.add(new CartItem("2", "product_2", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 4, "2", 2));
        cartItems.add(new CartItem("3", "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 5, "3", 3));
        cartItems.add(new CartItem("4", "product_4", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 7, "2", 2));
        cartItems.add(new CartItem("5", "product_5", "Chicken", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 10, "3", 2));
        return cartItems;
    }
    public static User login(User user) throws Exception {

        Hashtable<String, User> accounts = new Hashtable<String, User>();
        accounts.put("0123456781", new User("655a3582cd47699385f49e81", "Minh Man", "Truong", "0123456781", "Da Nang", "12345" ,1));
        accounts.put("0123456782", new User("2", "Ngoc Hao", "Le", "0123456782", "Quang Nam", "12345" ,0));
        accounts.put("0123456783", new User("3", "Kim Nam", "Le", "0123456783", "Da Nang", "12345" ,0));
        accounts.put("0123456784", new User("4", "Ha Binh", "Le", "0123456784", "Da Nang", "12345" ,0));
        accounts.put("0123456785", new User("5", "Van Ty", "Diep", "0123456785", "Phu Yen", "12345" ,0));
        try {
            if(!accounts.containsKey(user.getPhoneNum()))
                throw new Exception("User does not exist!");
            if(user.getPassword().equals(accounts.get(user.getPhoneNum()).getPassword())) {
                return accounts.get(user.getPhoneNum());
            }  else {
                throw new Exception("Wrong password!");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
    public static ArrayList<Product> getProductItems() {
        ArrayList<Product> productItems = new ArrayList<>();
        productItems.add(new Product("1", "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 3, "1"));
        productItems.add(new Product("2", "product_2", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 4, "2"));
        productItems.add(new Product("3", "product_1", "Noodles", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 5, "3"));
        productItems.add(new Product("4", "product_4", "Beef", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 7, "2"));
        productItems.add(new Product("5", "product_5", "Chicken", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC", 10, "3"));
        return productItems;
    }
}
