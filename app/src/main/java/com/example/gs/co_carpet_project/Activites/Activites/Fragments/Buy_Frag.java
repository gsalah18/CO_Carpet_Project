package com.example.gs.co_carpet_project.Activites.Activites.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Buy_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Store_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Buys_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;

public class Buy_Frag extends Fragment {
    ExpandableListView listView;
    Exp_Buy_Adapter adapter;
    LocalDatabase DB;
    LinkedList<Buys_class>Data;
    View v;
    int carpet;
    com.github.clans.fab.FloatingActionButton abroad;
    com.github.clans.fab.FloatingActionButton inside;
    com.github.clans.fab.FloatingActionButton all;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.fragment_buy,container,false);

        Bundle b=getArguments();
        carpet=b.getInt("msg");
        Refresh();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                adb.setItems(new String[]{"اضافه الا المخزن", "حذف من المشتريات"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Buys_class b=Data.get(position);
                            Store_class store=new Store_class(1,b.getCarpet(),b.getType(),b.getSize(),b.getColor(),b.getAmount(),b.getPrice(),b.getPicture());
                            DB.AddStore(store);
                            DB.Message("تم اضافتها الا المخزن");
                        }
                        else{
                            AlertDialog.Builder adb1=new AlertDialog.Builder(getActivity()) ;
                            String s;
                            if(carpet==0) s="سجاده";else s="ستائر";
                            adb1.setTitle("حذف"+s);
                            adb1.setMessage("هل تريد حذف "+s+"؟");
                            adb1.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            adb1.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DB.DeleteBuy(Data.get(position));
                                    Refresh();
                                    dialog.cancel();
                                }
                            });
                            adb1.show();
                        }
                    }
                });
                adb.show();
                return true;
            }
        });
        abroad=(com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.abroad_btn);

        abroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedList<Buys_class> abroad_arr=new LinkedList<Buys_class>();
                abroad_arr=(LinkedList)Data.clone();
                for(int i=0;i<abroad_arr.size();i++)
                    if(abroad_arr.get(i).getCountry().length()==0)
                        abroad_arr.remove(i);
                adapter=new Exp_Buy_Adapter(getActivity(),abroad_arr);
                listView.setAdapter(adapter);
            }
        });
        inside=(com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.inside_btn);
        inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<Buys_class>inside_arr=new LinkedList<>();
                inside_arr=(LinkedList)Data.clone();
                for(int i=0;i<inside_arr.size();i++)
                    if(inside_arr.get(i).getCountry().length()>0)
                        inside_arr.remove(i);
                adapter=new Exp_Buy_Adapter(getActivity(),inside_arr);
                listView.setAdapter(adapter);
            }
        });
        all=(com.github.clans.fab.FloatingActionButton)v.findViewById(R.id.all_btn);
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
        listView=(ExpandableListView)v.findViewById(R.id.listview);
        DB=new LocalDatabase(getActivity());
        Data=DB.getBuy(carpet);
        adapter=new Exp_Buy_Adapter(getActivity(),Data);
        listView.setAdapter(adapter);
    }


}
