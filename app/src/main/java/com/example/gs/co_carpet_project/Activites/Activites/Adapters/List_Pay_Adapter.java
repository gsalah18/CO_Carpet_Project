package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Activites.Payment;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Payment_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GS on 2/11/2017.
 */

public class List_Pay_Adapter extends ArrayAdapter<Payment_class> {
    List<Payment_class>payments;
    Context context;
    public List_Pay_Adapter(Context context, int resource, List<Payment_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.payments=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_payments,parent,false);
        TextView employee=(TextView)v.findViewById(R.id.employee);
        employee.setText(payments.get(position).getEmployee());
        TextView money=(TextView)v.findViewById(R.id.money);
        money.setText(""+payments.get(position).getMoney());
        return v;
    }
}
