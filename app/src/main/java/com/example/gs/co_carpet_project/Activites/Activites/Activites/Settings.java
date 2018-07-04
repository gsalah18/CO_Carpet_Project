package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.ServerDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Constants;
import com.example.gs.co_carpet_project.Activites.Activites.Others.KeyValue;
import com.example.gs.co_carpet_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Settings extends AppCompatActivity {
    Button AddGoods,AddCountry,AddCity;
    Context cc=this;
    ServerDatabase serverDB;
    TextView title;
    EditText data;
    Spinner city_country;
    LocalDatabase DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //serverDB=new ServerDatabase(this);
        DB=new LocalDatabase(this);


        AddGoods=(Button)findViewById(R.id.add_good);
        AddGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a1=new AlertDialog.Builder(cc);
                LayoutInflater inflater1=LayoutInflater.from(cc);
                View v1=inflater1.inflate(R.layout.add_setting,null);
                a1.setView(v1);
                title=(TextView)v1.findViewById(R.id.title);
                data=(EditText)v1.findViewById(R.id.data);
                city_country=(Spinner)v1.findViewById(R.id.city_country);

                title.setText("اضافه صنف");
                a1.setPositiveButton("اضف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (data.getText().toString().length() > 0) {
                            final String d = data.getText().toString();
                            DB.AddGood(d);
                            DB.Message("تم اضافه الغرض");
                            dialog.cancel();
                        }else DB.Message("يجب ملئ الخانه");
                    }
                });
                a1.show();
            }
        });

        AddCountry=(Button)findViewById(R.id.add_country);
        AddCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a2=new AlertDialog.Builder(cc);
                LayoutInflater inflater2=LayoutInflater.from(cc);
                View v2=inflater2.inflate(R.layout.add_setting,null);
                a2.setView(v2);
                title=(TextView)v2.findViewById(R.id.title);
                data=(EditText)v2.findViewById(R.id.data);
                city_country=(Spinner)v2.findViewById(R.id.city_country);

                title.setText("اضافه دوله");
                a2.setPositiveButton("اضف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (data.getText().toString().length() > 0) {
                            String d=data.getText().toString();
                            DB.AddCountry(d);
                            DB.Message("تم اضافه الدوله");
                            dialog.cancel();
                        }else DB.Message("يجب ملئ الخانه");
                    }
                });
                a2.show();
            }
        });

        AddCity=(Button)findViewById(R.id.add_city);
        AddCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a3=new AlertDialog.Builder(cc);
                LayoutInflater inflater3=LayoutInflater.from(cc);
                View v3=inflater3.inflate(R.layout.add_setting,null);
                a3.setView(v3);
                title=(TextView)v3.findViewById(R.id.title);
                data=(EditText)v3.findViewById(R.id.data);
                city_country=(Spinner)v3.findViewById(R.id.city_country);
                city_country.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item, DB.getCountry()));
                title.setText("اضافه مدينه");
                city_country.setVisibility(View.VISIBLE);
                a3.setPositiveButton("اضف", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            if (data.getText().toString().length() > 0) {
                                String d = data.getText().toString();
                                String country = city_country.getSelectedItem().toString();
                                DB.AddCity(d, country);
                                DB.Message("تم اضافه المدينه");
                                dialog.cancel();
                            } else DB.Message("يجب ملئ الخانه");

                    }
                });
                a3.show();
            }
        });
    }

    LinkedList<String>dd=new LinkedList<>();
    public void Show(View v){
        Button b1=(Button)findViewById(R.id.show_good);
        Button b2=(Button)findViewById(R.id.show_county);
        if(v==b1){
            AlertDialog.Builder ShowGoods=new AlertDialog.Builder(cc);
            LayoutInflater inflater=LayoutInflater.from(cc);
            View vv=inflater.inflate(R.layout.list_settings,null);
            ShowGoods.setView(vv);
            Spinner countries=(Spinner)vv.findViewById(R.id.countries);
            countries.setVisibility(View.INVISIBLE);
            final ListView listView=(ListView)vv.findViewById(R.id.listview);
            listView.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_list_item_1,DB.getGoods()));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder YesOrNo=new AlertDialog.Builder(cc);
                    YesOrNo.setMessage("هل تريد حذف المدينه؟");
                    YesOrNo.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DB.DeleteGood(parent.getItemAtPosition(position).toString());
                            listView.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_list_item_1,DB.getGoods()));
                            DB.DeleteCity(parent.getItemAtPosition(position).toString());
                            dialog.cancel();
                        }
                    });
                    YesOrNo.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    YesOrNo.show();
                }
            });
            final AlertDialog a=ShowGoods.create();
            ImageButton exit=(ImageButton)vv.findViewById(R.id.exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.cancel();
                }
            });
            a.show();


        }else if(v==b2){
            AlertDialog.Builder ShowCountry=new AlertDialog.Builder(cc);
            LayoutInflater inflater=LayoutInflater.from(cc);
            View vv=inflater.inflate(R.layout.list_settings,null);
            ShowCountry.setView(vv);
            Spinner countries=(Spinner)vv.findViewById(R.id.countries);
            countries.setVisibility(View.INVISIBLE);
            final ListView listView=(ListView)vv.findViewById(R.id.listview);
            dd=DB.getCountry();
            dd.remove(0);
            listView.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_list_item_1,dd));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder YesOrNo=new AlertDialog.Builder(cc);
                    YesOrNo.setMessage("هل تريد حذف المدينه؟");
                    YesOrNo.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                DB.DeleteCountry(parent.getItemAtPosition(position).toString());
                                dd = DB.getCountry();
                                dd.remove(0);
                                listView.setAdapter(new ArrayAdapter<String>(cc, android.R.layout.simple_list_item_1, dd));
                            }catch (Exception e){
                                DB.Message(e+"");
                            }
                            dialog.cancel();
                        }
                    });
                    YesOrNo.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    YesOrNo.show();
                }
            });
            final AlertDialog a=ShowCountry.create();
            ImageButton exit=(ImageButton)vv.findViewById(R.id.exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.cancel();
                }
            });
            a.show();



        }else{
            AlertDialog.Builder ShowCity=new AlertDialog.Builder(cc);
            LayoutInflater inflater=LayoutInflater.from(cc);
            View vv=inflater.inflate(R.layout.list_settings,null);
            ShowCity.setView(vv);
            Spinner countries=(Spinner)vv.findViewById(R.id.countries);
            countries.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_spinner_item,DB.getCountry()));
            countries.setVisibility(View.VISIBLE);
            final ListView listView=(ListView)vv.findViewById(R.id.listview);
            listView.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_list_item_1,DB.getCities(countries.getSelectedItem().toString())));
            countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    listView.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_list_item_1,DB.getCities(parent.getItemAtPosition(position).toString())));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder YesOrNo=new AlertDialog.Builder(cc);
                    YesOrNo.setMessage("هل تريد حذف المدينه؟");
                    YesOrNo.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DB.DeleteCity(parent.getItemAtPosition(position).toString());
                            listView.setAdapter(new ArrayAdapter<String>(cc,android.R.layout.simple_list_item_1,DB.getCities(parent.getItemAtPosition(position).toString())));
                            dialog.cancel();
                        }
                    });
                    YesOrNo.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    YesOrNo.show();
                }
            });
            final AlertDialog a=ShowCity.create();
            ImageButton exit=(ImageButton)vv.findViewById(R.id.exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.cancel();
                }
            });
            a.show();
        }
    }
}
