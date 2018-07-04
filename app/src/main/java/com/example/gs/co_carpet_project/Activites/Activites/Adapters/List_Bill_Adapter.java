package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Bills_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GS on 2/15/2017.
 */

public class List_Bill_Adapter extends ArrayAdapter<String> {

    Context context;
    LinkedList<String>Data;
    LinkedList<Double>Prices;
    public List_Bill_Adapter(Context context, int resource, List<String> objects,LinkedList<Double>Prices) {
        super(context, resource, objects);
        this.context=context;
        this.Data= (LinkedList) objects;
        this.Prices=Prices;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_payments,parent,false);
        TextView customer=(TextView)v.findViewById(R.id.employee);
        customer.setText(Data.get(position));
        TextView AllPrice=(TextView)v.findViewById(R.id.money);

        AllPrice.setText(""+Prices.get(position));
        return v;
    }
}
