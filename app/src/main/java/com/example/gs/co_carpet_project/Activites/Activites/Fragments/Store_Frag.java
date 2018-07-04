package com.example.gs.co_carpet_project.Activites.Activites.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.github.clans.fab.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Store_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;

import com.example.gs.co_carpet_project.R;

import java.util.Arrays;
import java.util.LinkedList;



public class Store_Frag extends Fragment {
    ExpandableListView listView;
    Exp_Store_Adapter adapter;
    LocalDatabase DB;
    LinkedList<Store_class>Data;
    int carpet;
    FloatingActionButton abroad;
    FloatingActionButton inside;
    FloatingActionButton all;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_store,container,false);
        listView=(ExpandableListView)v.findViewById(R.id.listview);
        Bundle b=getArguments();
        carpet =b.getInt("msg");

        Refresh();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity()) ;
                String s;
                if(carpet==0) s="سجاده";else s="ستائر";
                adb.setTitle("حذف"+s);
                adb.setMessage("هل تريد حذف "+s+"؟");
                adb.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adb.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeleteStore(Data.get(position));
                        Refresh();
                        dialog.cancel();
                    }
                });
                adb.show();
                return true;
            }
        });
        abroad=(FloatingActionButton)v.findViewById(R.id.abroad_btn);

        abroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<Store_class>abroad_arr=DB.getStore(carpet);
                for(int i=0;i<abroad_arr.size();i++)
                    if(abroad_arr.get(i).getExported()==1)
                        abroad_arr.remove(i);
                adapter=new Exp_Store_Adapter(getActivity(),abroad_arr);
                listView.setAdapter(adapter);
            }
        });
        inside=(FloatingActionButton)v.findViewById(R.id.inside_btn);
        inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<Store_class>inside_arr=DB.getStore(carpet);
                for(int i=0;i<inside_arr.size();i++)
                    if(inside_arr.get(i).getExported()==0)
                        inside_arr.remove(i);
                adapter=new Exp_Store_Adapter(getActivity(),inside_arr);
                listView.setAdapter(adapter);
            }
        });
        all=(FloatingActionButton)v.findViewById(R.id.all_btn);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh();
            }
        });

        return v;
    }
    public void Refresh(){
        DB=new LocalDatabase(getActivity());
        Data=DB.getStore(carpet);
        adapter=new Exp_Store_Adapter(getActivity(),Data);
        listView.setAdapter(adapter);
    }


}
