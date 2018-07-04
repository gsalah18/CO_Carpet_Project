package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;


public class Fragment_Adapter extends FragmentPagerAdapter {
    LinkedList<Fragment>frags;
    LinkedList<String>titles;
    public Fragment_Adapter(FragmentManager fm, LinkedList<Fragment>frags, LinkedList<String>titles) {
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
}
