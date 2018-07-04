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
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;


import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Out_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Outcomes_class;
import com.example.gs.co_carpet_project.R;


import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Outcomes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Context cc=this;
    LocalDatabase DB;
    Exp_Out_Adapter adapter;
    LinkedList<Outcomes_class>Data;
    ListView listView;
    Spinner spin_months;
    Spinner spin_year;
    Spinner spin_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outcomes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            DB = new LocalDatabase(this);
            listView = (ListView) findViewById(R.id.listview);

            Refresh();

            spin_months = (Spinner) findViewById(R.id.spin_months);
            spin_months.setOnItemSelectedListener(this);
            spin_year = (Spinner) findViewById(R.id.spin_years);
            LinkedList<String> years = new LinkedList<>();
            years.add("none");
            for (int i = 2005; i <= new Date().getYear() + 1900; i++)
                years.add(i + "");
            spin_year.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years));
            spin_year.setOnItemSelectedListener(this);

            spin_type = (Spinner) findViewById(R.id.spin_types);
            LinkedList<String> TypeList = new LinkedList<>();
            TypeList.add("none");
            TypeList.addAll(DB.getGoods());
            spin_type.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, TypeList));
            spin_type.setOnItemSelectedListener(this);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(cc);
                    LayoutInflater inflater = LayoutInflater.from(cc);
                    View v = inflater.inflate(R.layout.add_outcomes, null);
                    adb.setView(v);
                    final Spinner outcome = (Spinner) v.findViewById(R.id.outcome);
                    outcome.setAdapter(new ArrayAdapter<String>(cc, android.R.layout.simple_spinner_item, DB.getGoods()));
                    final EditText price = (EditText) v.findViewById(R.id.price);
                    final EditText notes = (EditText) v.findViewById(R.id.notes);

                    final AlertDialog alert = adb.create();

                    Button save = (Button) v.findViewById(R.id.save);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {

                                if (outcome.getChildCount() == 0)
                                    DB.Message("يجب عليك اضافه الاصنفه من الاعدادات");
                                else {
                                    if (outcome.getSelectedItem().toString().length() > 0 && price.getText().toString().length() > 0) {
                                        String date = new Date().toString();
                                        //DB.Message(date);
                                        Outcomes_class out = new Outcomes_class(outcome.getSelectedItem().toString(),
                                                Double.parseDouble(price.getText().toString()), notes.getText().toString(), date);
                                        DB.AddOutcome(out);
                                        DB.Message("تم اضافه مصروف بنجاح");
                                        Refresh();
                                        alert.cancel();
                                    } else DB.Message("يجب ملئ جميع الخانات");
                                }
                            } catch (NumberFormatException e) {
                                DB.Message("ادخل سعر حقيقي");
                            }
                        }
                    });

                    ImageButton exit = (ImageButton) v.findViewById(R.id.exit);
                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.cancel();
                        }
                    });

                    alert.show();
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DB.Message("ملاحظه" + "\n" + Data.get(position).getNotes());
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(cc);
                    adb.setTitle("حذف المصروف");
                    adb.setMessage("هل تريد حذف المصروف؟");
                    adb.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    adb.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DB.DeleteOutcome(Data.get(position));
                            Refresh();
                            dialog.cancel();
                        }
                    });
                    adb.show();
                    return true;
                }
            });
            //Refresh();
    }
    private void Refresh(){
            Data = DB.getOutComes();
            adapter = new Exp_Out_Adapter(this, R.layout.details_outcomes, Data);
            listView.setAdapter(adapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            Data = DB.FilterOutComes(spin_type.getSelectedItem().toString(),spin_year.getSelectedItem().toString(), spin_months.getSelectedItem().toString());
            adapter = new Exp_Out_Adapter(this,R.layout.details_outcomes,Data);
            listView.setAdapter(adapter);
        }catch (Exception e){
            DB.Message(e+"");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
