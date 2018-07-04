package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Sells_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GSalah on 3/12/2017.
 */

public class List_Sell_Items_Adapter extends ArrayAdapter<Sells_class> {
    Context context;
    LinkedList<Sells_class>Data;
    public List_Sell_Items_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Sells_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.Data= (LinkedList<Sells_class>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_sell,parent,false);
        TextView id=(TextView)v.findViewById(R.id.id);
        TextView date=(TextView)v.findViewById(R.id.date);
        id.setText(Data.get(position).getId()+"");
        date.setText(Data.get(position).getDate());
        return v;
    }
}
