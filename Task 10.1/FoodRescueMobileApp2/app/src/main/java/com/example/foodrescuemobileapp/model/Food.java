package com.example.foodrescuemobileapp.model;

public class Food {
    private int food_id;
    private int user_id;
    private String title;
    private String desc;
    private String location;
    private String date;
    private String pick_up_times;
    private int quantity;
    private byte[] image;

    public Food(int userid, String title, String desc, String date, String pickup_time, int quantity, String location, byte[] image) {
        this.user_id = userid;
        this.title = title;
        this.desc = desc;
        this.location = location;
        this.date = date;
        this.pick_up_times = pickup_time;
        this.quantity = quantity;
        this.image = image;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPick_up_times() {
        return pick_up_times;
    }

    public void setPick_up_times(String pick_up_times) {
        this.pick_up_times = pick_up_times;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Food() {
    }
}
