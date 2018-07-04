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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Bills_class;
import com.example.gs.co_carpet_project.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class List_Bill_Frag_Adapter extends ArrayAdapter<Bills_class> {

    private Context context;
    private LinkedList<Bills_class>data;


    public List_Bill_Frag_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Bills_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.data= (LinkedList<Bills_class>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.details_bills,parent,false);
        TextView id=(TextView)v.findViewById(R.id.id);
        TextView price=(TextView)v.findViewById(R.id.price);
        TextView left=(TextView)v.findViewById(R.id.left);
        id.setText(""+data.get(position).getId());
        price.setText(""+data.get(position).getPrice());
        left.setText(""+(data.get(position).getPrice()-data.get(position).getPaid()));

        return v;
    }
}
