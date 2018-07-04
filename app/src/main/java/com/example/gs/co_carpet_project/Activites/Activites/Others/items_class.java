package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GSalah on 3/7/2017.
 */

public class items_class {

    int id;
    String customer;
    String item;
    int amount;
    double price;

    public items_class(int id,String customer, String item, int amount, double price) {
        this.id=id;
        this.customer=customer;
        this.item = item;
        this.amount = amount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }
}
