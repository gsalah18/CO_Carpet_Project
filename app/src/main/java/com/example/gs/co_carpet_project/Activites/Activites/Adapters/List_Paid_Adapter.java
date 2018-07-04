package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Paids_class;
import com.example.gs.co_carpet_project.R;

import java.util.List;

/**
 * Created by GS on 2/11/2017.
 */

public class List_Paid_Adapter extends ArrayAdapter<Paids_class> {
    List<Paids_class>payments;
    Context context;
    public List_Paid_Adapter(Context context, int resource, List<Paids_class> objects) {
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
        employee.setText(payments.get(position).getPrice()+"");
        TextView money=(TextView)v.findViewById(R.id.money);
        money.setText(""+payments.get(position).getDate());
        return v;
    }
}
