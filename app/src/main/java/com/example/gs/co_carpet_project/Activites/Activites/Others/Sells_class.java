package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GS on 2/17/2017.
 */

public class Sells_class {
    int id;
    String carpet,type,color,size;
    int amount;
    double price;
    String date;

    public Sells_class(int id, String carpet, String type, String color, String size, int amount, double price) {
        this.id = id;
        this.carpet = carpet;
        this.type = type;
        this.color = color;
        this.size = size;
        this.amount = amount;
        this.price = price;

    }

    public Sells_class(int id, String date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCarpet() {
        return carpet;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return getType();
    }
}
