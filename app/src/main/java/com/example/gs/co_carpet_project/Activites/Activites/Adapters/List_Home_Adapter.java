package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Home;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.List;


public class List_Home_Adapter extends ArrayAdapter<Home> {
    LinkedList<Home>Data;
    Context context;
    public List_Home_Adapter(Context context, int resource, List<Home> objects) {
        super(context, resource, objects);
        Data= (LinkedList<Home>) objects;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View v=inflater.inflate(R.layout.list_home_layout,parent,false);
        ImageView image=(ImageView)v.findViewById(R.id.image);
        image.setBackgroundResource(Data.get(position).getPicture());
        TextView activity=(TextView)v.findViewById(R.id.activity);
        activity.setText(Data.get(position).getActivity());

        return v;
    }
}
