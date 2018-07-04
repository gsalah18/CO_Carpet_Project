package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Buys_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;


public class Exp_Store_Adapter extends BaseExpandableListAdapter {

    private Context context;
    private LinkedList<Store_class>data;
    public Exp_Store_Adapter(Context context, LinkedList<Store_class>data) {
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
        return data.get(groupPosition).getType();
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
        txt.setText((String)getGroup(groupPosition));
        return convertView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.details_buys,parent,false);
        final Store_class Data=(Store_class) getChild(groupPosition,childPosition);
        final ImageButton picture=(ImageButton)convertView.findViewById(R.id.picture);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(context.getResources(),Data.getPicture());
        picture.setBackground(bitmapDrawable);
        TextView type=(TextView)convertView.findViewById(R.id.type);
        type.setText("الصنف:"+Data.getType());
        TextView size=(TextView)convertView.findViewById(R.id.size);
        size.setText("الحجم:"+Data.getSize());
        if(Data.getCarpet()==1) ((ViewGroup)size.getParent()).removeView(size);



        TextView color=(TextView)convertView.findViewById(R.id.color);
        color.setText("اللون:"+Data.getColor());
        TextView amount=(TextView)convertView.findViewById(R.id.amount);
        amount.setText("الكميه:"+Data.getAmount());
        TextView price=(TextView)convertView.findViewById(R.id.price);
        price.setText("السعر:"+Data.getPrice());

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb=new AlertDialog.Builder(context);
                LayoutInflater inflater1=LayoutInflater.from(context);
                View vv=inflater1.inflate(R.layout.big_image,null);
                adb.setView(vv);
                ImageView image=(ImageView) vv.findViewById(R.id.image);
                BitmapDrawable bitmapDrawable=new BitmapDrawable(context.getResources(),Data.getPicture());
                image.setBackground(bitmapDrawable);
                adb.show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
