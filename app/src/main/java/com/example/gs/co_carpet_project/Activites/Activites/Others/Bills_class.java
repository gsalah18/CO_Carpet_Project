package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GS on 2/15/2017.
 */

public class Bills_class {
    int id;
    String customer;
    double price;
    double paid;

    public Bills_class(int id, String customer, double price,double paid) {
        this.id = id;
        this.customer = customer;
        this.price = price;
        this.paid=paid;

    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public double getPrice() {
        return price;
    }

    public double getPaid() {
        return paid;
    }
}
