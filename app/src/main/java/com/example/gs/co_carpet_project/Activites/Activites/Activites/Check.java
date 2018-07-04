package com.example.gs.co_carpet_project.Activites.Activites.Activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Fragment_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Check_Frag;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;

public class Check extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Fragment_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabLayout=(TabLayout)findViewById(R.id.tablayout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinkedList<Fragment>frags=new LinkedList<>();
        frags.add(new Check_Frag());
        frags.add(new Check_Frag());

        LinkedList<String>titles=new LinkedList<>();
        titles.add("صادر");
        titles.add("وارد");

        adapter=new Fragment_Adapter(getSupportFragmentManager(),frags,titles);
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

}
