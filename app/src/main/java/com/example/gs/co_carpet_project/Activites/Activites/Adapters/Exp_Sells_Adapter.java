package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Sells_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;


public class Exp_Sells_Adapter extends BaseExpandableListAdapter {

    private Context context;
    private LinkedList<Sells_class>data;
    public Exp_Sells_Adapter(Context context, LinkedList<Sells_class>data) {
        super();
        this.context=context;
        this.data=data;

    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition).getCarpet();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.exp_parent_layout,parent,false);
        TextView txt=(TextView)convertView.findViewById(R.id.txt);
        txt.setText(""+getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.details_sells,parent,false);
        Sells_class Data=(Sells_class) getChild(groupPosition,childPosition);

        TextView type=(TextView)convertView.findViewById(R.id.type);
        type.setText("الصنف:"+Data.getType());

        TextView color=(TextView)convertView.findViewById(R.id.color);
        color.setText("اللون:"+Data.getColor());

        TextView size=(TextView)convertView.findViewById(R.id.size);
        if(Data.getSize().equals(""))
            ((ViewGroup)size.getParent()).removeView(size);
        else
        size.setText("الحجم:"+Data.getSize());

        TextView amount=(TextView)convertView.findViewById(R.id.amount);
        amount.setText("الكميه:"+Data.getAmount());

        TextView price=(TextView)convertView.findViewById(R.id.price);
        price.setText("السعر:"+Data.getPrice());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
