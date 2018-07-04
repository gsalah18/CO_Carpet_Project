package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gs.co_carpet_project.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Runnable r=new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    Intent i=new Intent(MainActivity.this,HomePage.class);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread th=new Thread(r);
        th.start();
    }
}
