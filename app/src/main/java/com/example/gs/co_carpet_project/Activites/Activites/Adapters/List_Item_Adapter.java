package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.items_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by GSalah on 3/7/2017.
 */

public class List_Item_Adapter extends ArrayAdapter<items_class> {
    Context context;
    LinkedList<items_class>Data;
    public List_Item_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<items_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.Data= (LinkedList<items_class>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.details_items,null);
        TextView item=(TextView)v.findViewById(R.id.item);
        TextView amount=(TextView)v.findViewById(R.id.amount);
        TextView price=(TextView)v.findViewById(R.id.price);

        item.setText(Data.get(position).getItem());
        amount.setText(""+Data.get(position).getAmount());
        price.setText(""+Data.get(position).getPrice());

        return v;
    }
}
