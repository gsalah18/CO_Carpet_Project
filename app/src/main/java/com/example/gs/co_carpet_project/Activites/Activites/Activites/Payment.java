package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Pay_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Payment_class;
import com.example.gs.co_carpet_project.R;

import java.util.Calendar;
import java.util.LinkedList;

public class Payment extends AppCompatActivity {
    LocalDatabase DB;
    ListView listview;
    LinkedList<Payment_class>Data;
    List_Pay_Adapter adapter;
    Context cc=this;
    public static Button date;
    public static String Date="";
    DatePickerFragment newFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DB=new LocalDatabase(this);
        listview=(ListView)findViewById(R.id.listview);
        Refresh();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc);
                LayoutInflater inflater=LayoutInflater.from(cc);
                View v=inflater.inflate(R.layout.add_payment,null);
                adb.setView(v);
                final Spinner names=(Spinner)v.findViewById(R.id.emp_names);
                LinkedList<String>n=DB.getEmployeesNames();
                ArrayAdapter<String>a=new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item,n);
                names.setAdapter(a);

                final EditText money=(EditText)v.findViewById(R.id.money);
                date=(Button)v.findViewById(R.id.date);
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newFragment = new DatePickerFragment();
                        newFragment.show(getSupportFragmentManager(), "datePicker");
                    }
                });
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
                        if(names.getChildCount()>0) {
                            if (NotEmpty(money) && Date.length() > 0) {
                                Payment_class payment = new Payment_class(names.getSelectedItem().toString(), Double.parseDouble(money.getText().toString()), date.getText().toString());
                                DB.AddPayment(payment);
                                DB.Message("تم اضافه سفله");
                                Refresh();
                                alert.cancel();
                            } else DB.Message("يجب ملئ جميع الخانات");
                        }else DB.Message("لا يوجد عمال"+"\n"+"يرجى اضافه عمال لاضافه السلف");

                    }
                });

                alert.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc);
                adb.setTitle("تاريخ السفله");
                adb.setMessage(Data.get(position).getDate());
                adb.show();
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc) ;
                adb.setTitle("حذف السلفه");
                adb.setMessage("هل تريد حذف السفله؟");
                adb.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adb.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeletePayment(Data.get(position).getEmployee());
                        Refresh();
                        dialog.cancel();
                    }
                });
                adb.show();
                return true;
            }
        });
    }

    private void Refresh() {
        Data=DB.getPayments();
        adapter=new List_Pay_Adapter(this,R.layout.list_payments,Data);
        listview.setAdapter(adapter);
    }

    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        int year,month,day;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            // Create adapterfinal new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }



        public void onDateSet(DatePicker view, int year, int month, int day) {
            Date=year+"/"+(month+1)+"/"+day;
            date.setText(Date);
        }
    }
}
