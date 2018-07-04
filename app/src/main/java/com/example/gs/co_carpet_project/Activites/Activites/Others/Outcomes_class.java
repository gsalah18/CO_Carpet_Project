package com.example.gs.co_carpet_project.Activites.Activites.Others;

/**
 * Created by GS on 2/2/2017.
 */

public class Outcomes_class {
    String outcome;
    double price;
    String notes,date;

    public Outcomes_class(String outcome, double price, String notes,String date) {
        this.outcome = outcome;
        this.price = price;
        this.notes = notes;
        this.date=date;
    }

    public String getOutcome() {
        return outcome;
    }

    public double getPrice() {
        return price;
    }

    public String getNotes() { return notes; }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Outcomes_class{" +
                "outcome='" + outcome + '\'' +
                ", price=" + price +
                ", notes='" + notes + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
