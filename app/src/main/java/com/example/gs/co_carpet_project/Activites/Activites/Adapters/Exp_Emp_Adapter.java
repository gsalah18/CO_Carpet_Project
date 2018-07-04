package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Employee_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.List;


public class Exp_Emp_Adapter extends BaseExpandableListAdapter {
    private List<Employee_class> arrayListNames;
    private List<Employee_class> list;
    private Context context;
    private LinkedList<Employee_class>data;
    public Exp_Emp_Adapter(Context context, LinkedList<Employee_class>data) {
        super();
        this.context=context;
        arrayListNames=new LinkedList<>();
        arrayListNames.addAll(data);
        this.data=data;
        list=new LinkedList<>();
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
        return data.get(groupPosition).getName();
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

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.details_employee,parent,false);
        Employee_class Data=(Employee_class) getChild(groupPosition,childPosition);
        TextView phone=(TextView)convertView.findViewById(R.id.phone);
        phone.setText("رقم الهاتف:"+Data.getPhone());
        TextView address=(TextView)convertView.findViewById(R.id.address);
        address.setText("العنوان:"+Data.getAddress());
        TextView salary=(TextView)convertView.findViewById(R.id.salary);
        salary.setText("الراتب:"+Data.getSalary());
        TextView date=(TextView)convertView.findViewById(R.id.date);
        date.setText("تاريخ بدايه العمل:"+Data.getDate());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
