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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Emp_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Employee_class;
import com.example.gs.co_carpet_project.R;

import java.util.Calendar;
import java.util.LinkedList;

public class Employees extends AppCompatActivity {
    LocalDatabase DB;
    ExpandableListView listview;
    Context cc=this;
    LinkedList<Employee_class>Data;
    Exp_Emp_Adapter adapter;
    public static String Date="";
    public static Button date;
    DatePickerFragment newFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DB=new LocalDatabase(this);
        listview=(ExpandableListView)findViewById(R.id.listview);
        Refresh();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc);
                LayoutInflater inflater=LayoutInflater.from(cc);
                View v=inflater.inflate(R.layout.add_employee,null);
                adb.setView(v);
                final EditText name=(EditText)v.findViewById(R.id.name);
                final EditText phone=(EditText)v.findViewById(R.id.phone);
                final EditText address=(EditText)v.findViewById(R.id.address);
                final EditText salary=(EditText)v.findViewById(R.id.salary);
                date=(Button) v.findViewById(R.id.date);
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
                        if(NotEmpty(name)&&NotEmpty(phone)&&NotEmpty(address)&&NotEmpty(salary)&&!Date.equals("")) {

                            Employee_class employee = new Employee_class(name.getText().toString(), phone.getText().toString(),
                                    address.getText().toString(), Double.parseDouble(salary.getText().toString()),Date);
                            DB.AddEmployee(employee);
                            DB.Message("تم اضافه العامل");
                            Refresh();
                            alert.cancel();
                        }
                        else DB.Message("يجب ملئ جميع الخانات");
                    }
                });

                alert.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(cc) ;
                adb.setTitle("حذف العامل");
                adb.setMessage("هل تريد حذف العامل؟");
                adb.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adb.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeleteEmployee(Data.get(position).getName());
                        Refresh();
                        dialog.cancel();
                    }
                });
                adb.show();
                return true;
            }
        });

        EditText search=(EditText)findViewById(R.id.search);
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

    private void Refresh() {
        Data=DB.getEmployee();
        adapter=new Exp_Emp_Adapter(this,Data);
        listview.setAdapter(adapter);
    }

    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }
    private void Filter(String s){
        LinkedList<Employee_class>Filtered=new LinkedList<>();
        if(s.length()==0)
            Filtered= (LinkedList) Data.clone();
        else {
            for (Employee_class e : Data)
                if (e.getName().toLowerCase().contains(s.toLowerCase()))
                    Filtered.add(e);
        }
        adapter=new Exp_Emp_Adapter(cc,Filtered);
        listview.setAdapter(adapter);
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


            return new DatePickerDialog(getActivity(), this, year, month, day);
        }



        public void onDateSet(DatePicker view, int year, int month, int day) {
            Date=year+"/"+(month+1)+"/"+day;
            date.setText(Date);
        }
    }
}
