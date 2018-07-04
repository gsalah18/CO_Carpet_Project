package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Sells_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Sell_Items_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Sell_Frag;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Sells_class;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Store_class;
import com.example.gs.co_carpet_project.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class Sells extends AppCompatActivity {
    ListView listview;
    FrameLayout frame;
    LinearLayout titleLayout;
    FloatingActionButton fab;
    LocalDatabase DB;
    Context cc=this;
    LinkedList<Sells_class>Ids;
    LinkedList<Sells_class>items=new LinkedList<>();
    LinkedList<Store_class>items_del=new LinkedList<>();
    EditText id;
    LinkedList<Store_class>data;
    int i;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sells);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        DB=new LocalDatabase(this);
        listview=(ListView)findViewById(R.id.listview);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        frame=(FrameLayout)findViewById(R.id.frame);
        titleLayout=(LinearLayout)findViewById(R.id.layout);
        Refresh();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Sell_Frag frag=new Sell_Frag();
                Bundle b=new Bundle();
                b.putInt("id",Ids.get(position).getId());
                frag.setArguments(b);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,frag).commit();
                frame.setVisibility(View.VISIBLE);
                listview.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                titleLayout.setVisibility(View.INVISIBLE);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder YesOrNo=new AlertDialog.Builder(cc);
                YesOrNo.setMessage("هل تريد حذف المبيعه؟");
                YesOrNo.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeleteSell(Ids.get(position).getId());
                        Refresh();
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
                return false;
            }
        });
    }







    AlertDialog alert;
    public void Add(View v){
        final AlertDialog.Builder AddDialog=new AlertDialog.Builder(cc);
        LayoutInflater inflater=LayoutInflater.from(cc);
        View vv=inflater.inflate(R.layout.add_sells,null);
        AddDialog.setView(vv);
        final AlertDialog a=AddDialog.create();
        id= (EditText) vv.findViewById(R.id.id);
        Button addItem= (Button) vv.findViewById(R.id.add_item);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    AlertDialog.Builder AddItem = new AlertDialog.Builder(cc);
                    LayoutInflater inflater1 = LayoutInflater.from(cc);
                    View vv1 = inflater1.inflate(R.layout.add_sell_item, null);
                    AddItem.setView(vv1);
                    Spinner carpet = (Spinner) vv1.findViewById(R.id.carpet);
                    final ListView list = (ListView) vv1.findViewById(R.id.listview);

                    carpet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            i = position;
                            data = DB.getStore(position);
                            if (data.size() > 0) {
                                LinkedList<String> dd = new LinkedList<String>();
                                for (Store_class s : data)
                                    dd.add(s.getType());

                                list.setAdapter(new ArrayAdapter<String>(cc, android.R.layout.simple_list_item_1, dd));
                            } else DB.Message("المخزن فارغ");
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ShowItemDetails(data.get(position), i);
                        }
                    });


                    alert = AddItem.create();
                    ImageButton exit = (ImageButton) vv1.findViewById(R.id.exit);
                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.cancel();
                        }
                    });
                    alert.show();
                }catch (Exception e){
                    DB.Message(e+"");
                }
            }
        });
        final String date = new SimpleDateFormat("y/MM/dd").format(new Date());

        Button save=(Button)vv.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<Sells_class>test=DB.getSell(Integer.parseInt(id.getText().toString()));
                if(test.size()==0) {
                    if (items.size() > 0) {
                        DB.AddSellID(new Sells_class(Integer.parseInt(id.getText().toString()), date));
                        for (Sells_class s : items) {
                            DB.AddSell(s, Integer.parseInt(id.getText().toString()));
                        }
                        for(Store_class s:items_del)
                            DB.UpdateStore(s);
                        DB.Message("تم اضافه المبيعه");
                        Refresh();
                        a.cancel();
                    } else DB.Message("يجب اضافه اغراض");
                }else DB.Message("المبيعه موجوده");
            }
        });
        ImageButton exit=(ImageButton)vv.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.cancel();

            }
        });

        a.show();

    }
    private void Refresh(){
        Ids=DB.getSellIds();
        listview.setAdapter(new List_Sell_Items_Adapter(this,R.layout.details_sell_item,Ids));Ids=DB.getSellIds();
    }
    private boolean NotEmpty(EditText e){
        return e.getText().toString().length()>0;
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    private void ShowItemDetails(final Store_class store, int position){
        AlertDialog.Builder ItemDialog=new AlertDialog.Builder(cc);
        LayoutInflater inflater=LayoutInflater.from(cc);
        View v=inflater.inflate(R.layout.details_sells,null);
        ItemDialog.setView(v);

        final AlertDialog a=ItemDialog.create();

        final String Carpet;

        TextView type=(TextView)v.findViewById(R.id.type);
        type.setText("النوع :"+store.getType());

        TextView color=(TextView)v.findViewById(R.id.color);
        color.setText("اللون :"+store.getColor());

        TextView size=(TextView)v.findViewById(R.id.size);
        if(position==0){
            size.setText("المقاس :"+store.getSize());
            Carpet="سجاد";
        }
        else{
            size.setVisibility(View.INVISIBLE);
            Carpet="ستائر";
        }

        final TextView amount=(TextView)v.findViewById(R.id.amount);
        amount.setText("الكميه الموجوده :"+store.getAmount());

        TextView price=(TextView)v.findViewById(R.id.price);
        price.setText("السعر :"+store.getPrice());

        final EditText ordered=new EditText(cc);

        ordered.setHint("كم الكميه المطلوبه");
        ordered.setInputType(InputType.TYPE_CLASS_NUMBER);
        ordered.setGravity(Gravity.CENTER);

        Button add=new Button(cc);
        add.setText("اضافه");

        LinearLayout layout=(LinearLayout)v.findViewById(R.id.layout);
        layout.addView(ordered);
        layout.addView(add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (NotEmpty(ordered) && NotEmpty(id)) {
                        int curr = Integer.parseInt(ordered.getText().toString());
                        int prev = store.getAmount();
                        Sells_class sell = new Sells_class(Integer.parseInt(id.getText().toString()), Carpet, store.getType()
                                , store.getColor(), store.getSize(), curr, store.getPrice());
                        items.add(sell);
                        int x;
                        if (sell.getCarpet().equals("سجاد"))
                            x = 0;
                        else x = 1;
                        items_del.add(new Store_class(x, sell.getType(), sell.getSize(), sell.getColor(), prev - curr
                                , sell.getPrice()));
                        a.cancel();

                        AlertDialog.Builder YesNO = new AlertDialog.Builder(cc);
                        YesNO.setMessage("هل تريد اضافه عرض اخر");
                        YesNO.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        YesNO.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert.cancel();
                            }
                        });
                        YesNO.show();
                    } else DB.Message("يجب اضافه كميه" + "\n" + "او تحقق من تعبئه رقم المبيعه");
                }catch (Exception e){
                    DB.Message(e+"");
                }
            }
        });

        a.show();
    }


}
