package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Fragment_Adapter_Store;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Store_Frag;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;

public class Stores extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    LocalDatabase DB;
    Context cc=this;
    LinkedList<Store_Frag>frags;



    ImageButton image;
    Bitmap IMG;

    Fragment_Adapter_Store adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DB=new LocalDatabase(this);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);

        frags=new LinkedList<>();
        frags.add(new Store_Frag());
        frags.add(new Store_Frag());
        LinkedList<String>titles=new LinkedList<>();
        titles.add("سجاد");
        titles.add("ستائر");
        adapter =new Fragment_Adapter_Store(getSupportFragmentManager(),frags,titles);
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
    int exported;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add) {
            IMG = null;
            AlertDialog.Builder addDialog=new AlertDialog.Builder(cc);
            addDialog.setItems(new String[]{"سجاد","ستائر"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    carpet=which;
                    AlertDialog.Builder a1=new AlertDialog.Builder(cc);
                    a1.setItems(new String[]{"صادر", "وارد"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            exported =which;
                            final AlertDialog.Builder add=new AlertDialog.Builder(cc);
                            LayoutInflater inflater=LayoutInflater.from(cc);
                            View v=inflater.inflate(R.layout.add_store,null);
                            add.setView(v);
                            TextView title=(TextView)v.findViewById(R.id.title);
                            if(carpet==0){
                                if(exported ==0)
                                    title.setText("سجاد"+"\n"+"صادر");
                                else title.setText("سجاد"+"\n"+"وارد");
                            }else{
                                if(exported ==0)
                                    title.setText("ستائر"+"\n"+"صادر");
                                else title.setText("ستائر"+"\n"+"وارد");
                            }

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
                                    if(carpet==0){
                                        if(NotEmpty(type)&&NotEmpty(size)&&NotEmpty(color)&&NotEmpty(amount)&&NotEmpty(price)&&IMG!=null) {

                                            String Type = type.getText().toString();
                                            String Size=size.getText().toString();
                                            String Color = color.getText().toString();
                                            int Amount = Integer.parseInt(amount.getText().toString());
                                            double Price = Double.parseDouble(price.getText().toString());
                                            Store_class buy = new Store_class(exported,carpet, Type, Size, Color, Amount, Price, IMG);
                                            DB.AddStore(buy);
                                            DB.Message("تم الاضافه");
                                            adapter.R();
                                            a.cancel();
                                        }else DB.Message("يجب ملئ جميع الخانات");
                                    }
                                    else{
                                        if(NotEmpty(type)&&NotEmpty(color)&&NotEmpty(amount)&&NotEmpty(price)&&IMG!=null) {

                                            String Type = type.getText().toString();
                                            String Size="";
                                            String Color = color.getText().toString();
                                            int Amount = Integer.parseInt(amount.getText().toString());
                                            double Price = Double.parseDouble(price.getText().toString());
                                            Store_class buy = new Store_class(exported,carpet, Type, Size, Color, Amount, Price, IMG);
                                            DB.AddStore(buy);
                                            DB.Message("تم الاضافه");
                                            adapter.R();
                                            a.cancel();
                                        }else DB.Message("يجب ملئ جميع الخانات");
                                    }

                                }
                            });
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




    private boolean NotEmpty(EditText txt){
        return txt.getText().toString().length()>0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            IMG = (Bitmap) extras.get("data");
            image.setImageBitmap(IMG);
        }
    }
}
