package com.example.gs.co_carpet_project.Activites.Activites.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.Exp_Sells_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.R;

/**
 * Created by GSalah on 3/12/2017.
 */

public class Sell_Frag extends Fragment {
    LocalDatabase DB;
    ExpandableListView listView;
    int Id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.details_sell_item,container,false);
        DB=new LocalDatabase(getActivity());
        Bundle b=getArguments();
        Id=b.getInt("id");
        listView=(ExpandableListView)v.findViewById(R.id.listview);
        Refresh();


        return v;
    }

    public void Refresh(){
        listView.setAdapter(new Exp_Sells_Adapter(getActivity(),DB.getSell(Id)));
    }
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                    return true;

                }

                return false;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().finish();
        startActivity(getActivity().getIntent());

    }
}
