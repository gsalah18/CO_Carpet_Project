package com.example.gs.co_carpet_project.Activites.Activites.Others;

import android.graphics.Bitmap;

/**
 * Created by GS on 2/2/2017.
 */

public class Buys_class {
    int carpet;
    String type,size,color;
    int amount;
    double price;
    Bitmap picture;
    String address,city,country;

    public Buys_class(int carpet, String type, String size, String color, int amount, double price, Bitmap picture, String address, String city, String country) {
        this.carpet = carpet;
        this.type = type;
        this.size = size;
        this.color = color;
        this.amount = amount;
        this.price = price;
        this.picture = picture;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    public int getCarpet() {
        return carpet;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
