package com.example.gs.co_carpet_project.Activites.Activites.Others;

import android.graphics.Bitmap;

/**
 * Created by GS on 2/14/2017.
 */

public class Check_class {
    String export;
    String no,owner;
    double price;
    String account,bank,date;
    Bitmap picture;

    public Check_class(String export, String no, String owner, double price, String account, String bank, String date, Bitmap picture) {
        this.export = export;
        this.no = no;
        this.owner = owner;
        this.price = price;
        this.account = account;
        this.bank = bank;
        this.date = date;
        this.picture = picture;
    }

    public String getExport() {
        return export;
    }

    public String getNo() {
        return no;
    }

    public String getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }

    public String getAccount() {
        return account;
    }

    public String getBank() {
        return bank;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getPicture() {
        return picture;
    }
}
