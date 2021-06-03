package com.example.foodrescuemobileapp.model;

public class Cart {
    private int cart_id;
    private int user_id;
    private int food_id;
    private String food_title;


    public Cart() {
    }

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Cart(int user_id, int food_id, String food_title) {
        this.user_id = user_id;
        this.food_id = food_id;
        this.food_title = food_title;
    }

    public String getFood_title() {
        return food_title;
    }

    public void setFood_title(String food_title) {
        this.food_title = food_title;
    }
}
