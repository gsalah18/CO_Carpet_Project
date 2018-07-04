package com.example.gs.co_carpet_project.Activites.Activites.Others;


import android.provider.ContactsContract;

public class Employee_class {
    String name,phone,address;
    double salary;
    String date;

    public Employee_class(String name, String phone, String address, double salary, String date) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.date = date;
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

    public double getSalary() {
        return salary;
    }

    public String getDate() {
        return date;
    }
}
