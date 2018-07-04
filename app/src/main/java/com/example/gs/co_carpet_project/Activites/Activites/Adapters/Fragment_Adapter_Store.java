package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Sell_Frag;
import com.example.gs.co_carpet_project.Activites.Activites.Fragments.Store_Frag;

import java.util.LinkedList;


public class Fragment_Adapter_Store extends FragmentPagerAdapter {
    LinkedList<Store_Frag>frags;
    LinkedList<String>titles;
    public Fragment_Adapter_Store(FragmentManager fm, LinkedList<Store_Frag>frags, LinkedList<String>titles) {
        super(fm);
        this.frags=frags;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f= frags.get(position);
        Bundle b=new Bundle();
        b.putInt("msg",position);
        f.setArguments(b);
        return f;
    }

    @Override
    public int getCount() {
        return frags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void R(){
        frags.get(0).Refresh();
        frags.get(1).Refresh();
    }
}
