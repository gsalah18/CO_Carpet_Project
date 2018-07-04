package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.*;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Fragment_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Fragment_Adapter_Buy;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Buy_Frag;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Store_Frag;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Buys_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;
import java.util.Scanner;

public class Buys extends AppCompatActivity {
    LocalDatabase DB;
    Context cc=this;
    ImageButton image;
    Bitmap IMG=null;
    ViewPager viewPager;
    TabLayout tabLayout;
    LinkedList<Buy_Frag>frags;
    Fragment_Adapter_Buy adapter;
    LinkedList<String>DD=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buys);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);DB=new LocalDatabase(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);


        frags=new LinkedList<>();
        frags.add(new Buy_Frag());
        frags.add(new Buy_Frag());
        LinkedList<String>titles=new LinkedList<>();
        titles.add("سجاد");
        titles.add("ستائر");
        adapter=new Fragment_Adapter_Buy(getSupportFragmentManager(),frags,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    int carpet;
    int abroad;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.add){
            IMG = null;
            AlertDialog.Builder addDialog=new AlertDialog.Builder(cc);
            addDialog.setItems(new String[]{"سجاد","ستائر"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    carpet=which;
                    AlertDialog.Builder a1=new AlertDialog.Builder(cc);
                    a1.setItems(new String[]{"داخليه", "خارجيه"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            abroad=which;
                            final AlertDialog.Builder add=new AlertDialog.Builder(cc);
                            LayoutInflater inflater=LayoutInflater.from(cc);
                            View v=inflater.inflate(R.layout.add_buy,null);
                            add.setView(v);
                            TextView title=(TextView)v.findViewById(R.id.title);
                            if(carpet==0){
                                if(abroad==0)
                                    title.setText("سجاد"+"\n"+"داخليه");
                                else title.setText("سجاد"+"\n"+"خارجيه");
                            }else{
                                if(abroad==0)
                                    title.setText("ستائر"+"\n"+"داخليه");
                                else title.setText("ستائر"+"\n"+"خارجيه");
                            }

                            final Spinner country=(Spinner) v.findViewById(R.id.countries);
                            final Spinner cities=(Spinner) v.findViewById(R.id.cities);
                            if(abroad==0) {
                                ((ViewGroup)country.getParent()).removeView(country);
                                cities.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item,DB.getLocalCities()));

                            }else{
                                DD=DB.getCountry();
                                DD.remove(0);
                                country.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item,DD));
                                country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        cities.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item,DB.getCities(parent.getSelectedItem().toString())));
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }

                            final EditText address=(EditText) v.findViewById(R.id.address);
                            final EditText type=(EditText) v.findViewById(R.id.type);
                            final EditText size=(EditText) v.findViewById(R.id.size);
                            if(carpet==1)
                                ((ViewGroup)size.getParent()).removeView(size);

                            final EditText color=(EditText) v.findViewById(R.id.color);
                            final EditText amount=(EditText) v.findViewById(R.id.amount);
                            final EditText price=(EditText) v.findViewById(R.id.price);
                            image=(ImageButton)v.findViewById(R.id.image);
                            image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (takePictureIntent.resolveActivity(cc.getPackageManager()) != null) {
                                        startActivityForResult(takePictureIntent, 1);
                                    }
                                }
                            });
                            final AlertDialog a=add.create();
                            Button save=(Button)v.findViewById(R.id.save);
                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(cities.getChildCount()==0||(abroad==1&&DD.size()==0))
                                        DB.Message("يجب عليك اضافه المدن والدول من الاعدادات");
                                    else{
                                    if(NotEmpty(type)&&(carpet==1||NotEmpty(size))&&NotEmpty(color)&&NotEmpty(amount)&&NotEmpty(price)&&NotEmpty(address)&&IMG!=null) {

                                        String Country;
                                        if (abroad == 0)
                                            Country = "";
                                        else Country = country.getSelectedItem().toString();
                                        String City = cities.getSelectedItem().toString();
                                        String Address = address.getText().toString();
                                        String Type = type.getText().toString();
                                        String Size;
                                        if (carpet == 0)
                                            Size = size.getText().toString();
                                        else Size = "";
                                        String Color = color.getText().toString();
                                        int Amount = Integer.parseInt(amount.getText().toString());
                                        double Price = Double.parseDouble(price.getText().toString());
                                        final Buys_class buy = new Buys_class(carpet, Type, Size, Color, Amount, Price, IMG, Address, City, Country);
                                        DB.AddBuy(buy);
                                        //DB.Message("تم الاضافه");
                                        adapter.R();

                                        a.cancel();
                                        AlertDialog.Builder adb2= new AlertDialog.Builder(cc);
                                        adb2.setTitle("اضافه المشتريه الا المخزن");
                                        adb2.setMessage("هل تريد النقل المحزن");
                                        adb2.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Buys_class b=buy;
                                                Store_class store=new Store_class(1,carpet,b.getType(),b.getSize(),b.getColor(),b.getAmount(),b.getPrice(),b.getPicture());
                                                DB.AddStore(store);
                                                DB.Message("تمت الاضافه للمخزن");
                                            }
                                        });
                                        adb2.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();

                                            }
                                        });
                                        adb2.show();
                                    }else DB.Message("يجب ملئ جميع الخانات");
                                }
                            }});
                            ImageButton exit=(ImageButton)v.findViewById(R.id.exit);
                            exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    a.cancel();
                                }
                            });
                            a.show();

                        }
                    });
                    a1.show();
                }
            });
            addDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            IMG = (Bitmap) extras.get("data");
            image.setImageBitmap(IMG);
        }
    }
    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }


}
