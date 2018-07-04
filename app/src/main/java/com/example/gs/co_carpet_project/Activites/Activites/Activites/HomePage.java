package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Home_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Home;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;

public class HomePage extends AppCompatActivity {
    Intent I;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listView=(ListView)findViewById(R.id.listview);
        String[] activites=getResources().getStringArray(R.array.home);
        int []pictures={R.drawable.home_customers,R.drawable.outcomes,R.drawable.buys,R.drawable.stores,R.drawable.employees,R.drawable.catagories
        ,R.drawable.sells,R.drawable.debts,R.drawable.catagories};
        LinkedList<Home>D=new LinkedList<>();
        for(int i=0;i<activites.length;i++)
            D.add(new Home(pictures[i],activites[i]));

        List_Home_Adapter adapter=new List_Home_Adapter(this,R.layout.list_home_layout,D);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    I=new Intent(HomePage.this,Customers.class);
                    startActivity(I);
                }
                else if(position==1){
                    I=new Intent(HomePage.this,Outcomes.class);
                    startActivity(I);
                }
                else if(position==2){
                    I=new Intent(HomePage.this,Buys.class);
                    startActivity(I);
                }
                else if(position==3){
                    I=new Intent(HomePage.this,Stores.class);
                    startActivity(I);
                }

                else if(position==4){
                    I=new Intent(HomePage.this,Employees.class);
                    startActivity(I);
                }
                else if(position==5){
                    I=new Intent(HomePage.this,Payment.class);
                    startActivity(I);
                }
                else if(position==6){
                    I=new Intent(HomePage.this,Sells.class);
                    startActivity(I);
                }
                else if(position==7){
                    I=new Intent(HomePage.this,Debts.class);
                    startActivity(I);
                }
                else if(position==8){
                    I=new Intent(HomePage.this,Check.class);
                    startActivity(I);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.setting){
            I=new Intent(HomePage.this,Settings.class);
            startActivity(I);
        }
        return super.onOptionsItemSelected(item);
    }
}
