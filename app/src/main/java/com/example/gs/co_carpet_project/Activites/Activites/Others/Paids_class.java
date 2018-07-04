package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GS on 2/17/2017.
 */

public class Paids_class {
    double price;
    String date;

    public Paids_class(double price, String date) {
        this.price = price;
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
