package com.example.gs.co_carpet_project.Activites.Activites.Others;

import android.graphics.Bitmap;

/**
 * Created by GS on 2/26/2017.
 */

public class Store_class {
    int exported,carpet;
    String type,size,color;
    int amount;
    double price;
    Bitmap picture;

    public Store_class(int exported, int carpet, String type, String size, String color, int amount, double price, Bitmap picture) {
        this.exported = exported;
        this.carpet = carpet;
        this.type = type;
        this.size = size;
        this.color = color;
        this.amount = amount;
        this.price = price;
        this.picture = picture;
    }

    public Store_class(int carpet, String type, String size, String color, int amount, double price) {
        this.carpet = carpet;
        this.type = type;
        this.size = size;
        this.color = color;
        this.amount = amount;
        this.price = price;
    }

    public int getExported() {
        return exported;
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

    @Override
    public String toString() {
        return getType();
    }
}
