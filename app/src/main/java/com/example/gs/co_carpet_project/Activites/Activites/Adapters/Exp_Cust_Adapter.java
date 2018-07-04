package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Activites.Customers;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Customers_class;
import com.example.gs.co_carpet_project.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Exp_Cust_Adapter extends ArrayAdapter<Customers_class> {
    Context context;
    LinkedList<Customers_class>Data;

    public Exp_Cust_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Customers_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.Data= (LinkedList<Customers_class>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v=LayoutInflater.from(context).inflate(R.layout.details_customer,parent,false);
        Customers_class customer=Data.get(position);
        TextView name=(TextView)v.findViewById(R.id.name);
        name.setText(customer.getName());

        TextView phone=(TextView)v.findViewById(R.id.phone);
        phone.setText(customer.getPhone());

        TextView address=(TextView)v.findViewById(R.id.address);
        address.setText(customer.getAddress());
        return v;
    }
}
