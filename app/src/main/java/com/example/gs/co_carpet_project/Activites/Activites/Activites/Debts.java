package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Bill_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Bills_Frag;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Bills_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.items_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;

public class Debts extends AppCompatActivity {
    Context cc=this;
    LocalDatabase DB;
    ListView listView;
    FrameLayout frame;
    LinearLayout titleLayout;
    List_Bill_Adapter adapter;
    LinkedList<String>Data;
    AlertDialog A;
    Bills_Frag Bill_Frag;
    double sum;
    boolean t;
    LinkedList<items_class>Items=new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bill_Frag=null;
        listView=(ListView)findViewById(R.id.listview);
        frame=(FrameLayout)findViewById(R.id.frame);
        titleLayout=(LinearLayout)findViewById(R.id.title_layout);
        DB=new LocalDatabase(this);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc);
                final LayoutInflater inflater=LayoutInflater.from(cc);
                View v=inflater.inflate(R.layout.add_bills,null);
                adb.setView(v);
                A=adb.create();
                final EditText id=(EditText)v.findViewById(R.id.id);
                ArrayAdapter<String>Spinner_adapter=new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item,DB.getCustomerName());
                final Spinner customers=(Spinner)v.findViewById(R.id.cust_names);
                customers.setAdapter(Spinner_adapter);
                final EditText mainprice=(EditText)v.findViewById(R.id.price);
                final EditText paid=(EditText)v.findViewById(R.id.paid);
                Button AddItems=(Button)v.findViewById(R.id.add_item);
                t=true;
                sum=0;
                AddItems.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                            AlertDialog.Builder AddItemDialog = new AlertDialog.Builder(cc);
                            LayoutInflater inflater1 = LayoutInflater.from(cc);
                            View itemView = inflater1.inflate(R.layout.add_items, null);
                            AddItemDialog.setView(itemView);
                            final AlertDialog itemAlert=AddItemDialog.create();
                            final EditText item=(EditText)itemView.findViewById(R.id.item);
                            final EditText amount=(EditText)itemView.findViewById(R.id.amount);
                            final EditText price=(EditText)itemView.findViewById(R.id.price);
                            Button AddItemBtn=(Button)itemView.findViewById(R.id.add_item_btn);
                            AddItemBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(customers.getChildCount()>0) {
                                        if (NotEmpty(id) && !DB.CheckBill(Integer.parseInt(id.getText().toString()), customers.getSelectedItem().toString())
                                                && NotEmpty(item) && NotEmpty(amount) && NotEmpty(price)) {
                                            Items.add(new items_class(Integer.parseInt(id.getText().toString()), customers.getSelectedItem().toString(),
                                                    item.getText().toString(), Integer.parseInt(amount.getText().toString()),
                                                    Double.parseDouble(price.getText().toString())));
                                            sum += Double.parseDouble(price.getText().toString());
                                            mainprice.setText("المبلغ الكامل" + sum);
                                            AlertDialog.Builder YESNO = new AlertDialog.Builder(cc);
                                            YESNO.setMessage("هل تريد اضافه غرض اخر");
                                            YESNO.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                    item.setText("");
                                                    amount.setText("");
                                                    price.setText("");
                                                    item.requestFocus();
                                                }
                                            });
                                            YESNO.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    t = false;
                                                    dialog.cancel();
                                                    itemAlert.cancel();
                                                }
                                            });
                                            YESNO.show();
                                        }else DB.Message("يجب اضافه الرقم الفاتوره قبل اضافه البضاعه" + "\n" + "وايضا تاكد من عدم تكرر رقم الفاتوره");
                                    }else DB.Message("لا يوجد اسم زبون"+"" +"\n"+ "يجب اضافه زبون ثم اضافه ديون ");
                                }
                            });
                            ImageButton exit=(ImageButton)itemView.findViewById(R.id.exit);
                            exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    itemAlert.cancel();
                                }
                            });
                            itemAlert.show();

                    }
                });

                Button save=(Button) v.findViewById(R.id.save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(NotEmpty(id)&&NotEmpty(paid)&&NotEmpty(mainprice)){

                                Bills_class bill = new Bills_class(Integer.parseInt(id.getText().toString()), customers.getSelectedItem().toString(), sum
                                        , 0);
                                DB.AddBill(bill);
                                DB.AddPaid(Integer.parseInt(id.getText().toString()), customers.getSelectedItem().toString(), Double.parseDouble(paid.getText().toString()));
                                for (items_class item : Items)
                                    DB.AddItem(item);

                                DB.Message("تم اضافه الفاتوره");
                                Refresh();
                                A.cancel();

                        }else DB.Message("يجب ملئ جميع الخانات");
                    }
                });
                ImageButton exit=(ImageButton)v.findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        A.cancel();
                    }
                });
                A.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bill_Frag=new Bills_Frag();
                Bundle b=new Bundle();
                b.putString("customer",Data.get(position));
                Bill_Frag.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,Bill_Frag).commit();
                frame.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                titleLayout.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void Refresh(){
        Data=DB.getCustomerWithBill();
        LinkedList<Double>allprices=new LinkedList<>();
        for(String name:Data)
            allprices.add(DB.getAllBillsSum(name));
        adapter=new List_Bill_Adapter(this,R.layout.list_payments,Data,allprices);
        listView.setAdapter(adapter);
    }
    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }


}
