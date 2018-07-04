package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Cust_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Customers_class;
import com.example.gs.co_carpet_project.R;


import java.util.LinkedList;


public class Customers extends AppCompatActivity {
    Context cc=this;
    LinkedList<Customers_class>Data;
    LocalDatabase DB;
    ListView listview;
    Exp_Cust_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DB=new LocalDatabase(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder adb=new AlertDialog.Builder(cc);
                LayoutInflater inflater=LayoutInflater.from(cc);
                View v=inflater.inflate(R.layout.add_customers,null);
                adb.setView(v);
                final EditText name=(EditText)v.findViewById(R.id.name);
                final EditText phone=(EditText)v.findViewById(R.id.phone);
                final EditText address=(EditText)v.findViewById(R.id.address);

                Button save=(Button)v.findViewById(R.id.save);

                final AlertDialog alert=adb.create();
                ImageButton exit=(ImageButton)v.findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(NotEmpty(name)&&NotEmpty(phone)&&NotEmpty(address)) {
                            try {
                                Customers_class customer = new Customers_class(name.getText().toString(), phone.getText().toString(),
                                        address.getText().toString());
                                DB.AddCustomer(customer);
                                DB.Message("تم اضافه الزبون");
                                Refresh();
                                alert.cancel();
                            }catch (Exception e){
                                DB.Message("يوجد زبون بنفس الاسم");
                            }
                        }else DB.Message("يجب ملئ جميع الخانات");
                    }
                });

                alert.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Refresh();
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc) ;
                adb.setTitle("حذف زبون");
                adb.setMessage("هل تريد حذف الزبون؟");
                adb.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adb.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeleteCustomer(Data.get(position).getName());
                        Refresh();
                        dialog.cancel();
                    }
                });
                adb.show();
                return true;
            }
        });

        final EditText search=(EditText)findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Filter(""+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void Refresh(){
        Data=DB.getCustomers();
        listview=(ListView) findViewById(R.id.listview);
        adapter=new Exp_Cust_Adapter(this,R.layout.details_customer,Data);
        listview.setAdapter(adapter);
    }



    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }

    private void Filter(String s){
        LinkedList<Customers_class>Filtered=new LinkedList<>();
        if(s.length()==0)
            Filtered= (LinkedList) Data.clone();
        else {
            for (int i=0;i<Data.size();i++)
                if (Data.get(i).getName().toLowerCase().contains(s.toLowerCase()))
                    Filtered.add(Data.get(i));
        }
        adapter=new Exp_Cust_Adapter(cc,R.layout.details_customer,Filtered);
        listview.setAdapter(adapter);
    }



}
