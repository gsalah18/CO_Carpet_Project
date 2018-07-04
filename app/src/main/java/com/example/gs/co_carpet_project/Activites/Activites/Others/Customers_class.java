package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GS on 2/2/2017.
 */

public class Customers_class {
    String name,phone,address;

    public Customers_class(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return name;
    }
}
