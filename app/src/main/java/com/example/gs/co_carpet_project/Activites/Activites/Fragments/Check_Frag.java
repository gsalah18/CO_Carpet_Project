package com.example.gs.co_carpet_project.Activites.Activites.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Activites.Employees;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Check_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Check_class;
import com.example.gs.co_carpet_project.R;

import java.util.Calendar;
import java.util.LinkedList;

import static android.app.Activity.RESULT_OK;

public class Check_Frag extends Fragment {
    Boolean export;
    ExpandableListView listView;
    EditText no,owner,price,account,bank;
    ImageButton image;
    Button save;
    Bitmap IMG;
    LocalDatabase DB;
    Exp_Check_Adapter adapter;
    LinkedList<Check_class>Data;
    View V;
    View v;
    public static String Date="";
    public static Button date;
    DatePickerFragment newFragment;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        V=inflater.inflate(R.layout.fragment_check_, container, false);
        Bundle b=getArguments();
        DB=new LocalDatabase(getActivity());
        int position =b.getInt("msg");
        if(position==0)
            export=true;
        else export=false;

        listView=(ExpandableListView)V.findViewById(R.id.listview);
        Refresh();


        FloatingActionButton fab=(FloatingActionButton)V.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vv) {
                IMG=null;
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                LayoutInflater inflater=LayoutInflater.from(getActivity());
                v=inflater.inflate(R.layout.add_check,null);
                adb.setView(v);
                Initialize();
                TextView title=(TextView)v.findViewById(R.id.title);
                if(export) title.setText("الصادر");
                else title.setText("الوارد");
                final AlertDialog alert=adb.create();
                alert.show();
                ///Image/////////////////
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, 1);
                    }

                    }
                });

                //////////////////////////////////////////////////////////////////////////////////////////////////////

                ////////Save///////////////////////
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newFragment = new DatePickerFragment();
                        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(NotEmpty(no)&&NotEmpty(owner)&&NotEmpty(price)&&NotEmpty(account)&&NotEmpty(bank)&&Date.length()>0&&IMG!=null){
                            String ex;
                            if(export)
                                ex="exported";
                            else ex="";
                            Check_class check=new Check_class(ex,no.getText().toString(),owner.getText().toString(),Double.parseDouble(price.getText().toString())
                            ,account.getText().toString(),bank.getText().toString(),Date,IMG);
                            DB.AddCheck(check);
                            DB.Message("تم اضافه الشك");
                            Refresh();
                            alert.cancel();
                        }else DB.Message("يجب ملئ جميع الخانات");
                    }
                });
                ImageButton exit=(ImageButton)v.findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });
                /////////////////////////////////

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(getActivity()) ;
                adb.setTitle("حذف الشيك");
                adb.setMessage("هل تريد حذف الشيك؟");
                adb.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                adb.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeleteCheck(Data.get(position).getNo());
                        Refresh();
                        dialog.cancel();
                    }
                });
                adb.show();
                return true;
            }
        });


        return V;
    }

    private void Initialize() {
        no=(EditText)v.findViewById(R.id.no);
        owner=(EditText)v.findViewById(R.id.owner);
        price=(EditText)v.findViewById(R.id.price);
        account=(EditText)v.findViewById(R.id.account);
        bank=(EditText)v.findViewById(R.id.bank);
        date=(Button) v.findViewById(R.id.date);
        image=(ImageButton)v.findViewById(R.id.image);
        save=(Button)v.findViewById(R.id.save);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            IMG = (Bitmap) extras.get("data");
            image.setImageBitmap(IMG);
        }
    }
    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }

    private void Refresh(){
        Data=DB.getChecks(export);
        adapter=new Exp_Check_Adapter(getActivity(),Data,export);
        listView.setAdapter(adapter);
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

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }



        public void onDateSet(DatePicker view, int year, int month, int day) {
            Date=year+"/"+(month+1)+"/"+day;
            date.setText(Date);
        }
    }
}
