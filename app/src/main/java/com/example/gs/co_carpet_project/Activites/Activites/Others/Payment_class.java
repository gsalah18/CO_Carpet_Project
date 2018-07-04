package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GS on 2/11/2017.
 */

public class Payment_class {
    String employee;
    double money;
    String date;

    public Payment_class(String employee, double money,String date) {
        this.employee = employee;
        this.money = money;
        this.date=date;
    }

    public String getEmployee() {
        return employee;
    }

    public double getMoney() {
        return money;
    }

    public String getDate() {
        return date;
    }
}
