package com.example.gs.co_carpet_project.Activites.Activites.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gs.co_carpet_project.Activites.Activites.Activites.Outcomes;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Outcomes_class;
import com.example.gs.co_carpet_project.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Exp_Out_Adapter extends ArrayAdapter<Outcomes_class> {
    Context context;
    LinkedList<Outcomes_class> Data;
    public Exp_Out_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Outcomes_class> objects) {
        super(context, resource, objects);
        this.context=context;
        this.Data= (LinkedList<Outcomes_class>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.details_outcomes,parent,false);
        Outcomes_class outcomes=Data.get(position);
        TextView outcome=(TextView)convertView.findViewById(R.id.outcome);
        outcome.setText(outcomes.getOutcome());
        TextView price=(TextView)convertView.findViewById(R.id.price);
        price.setText(""+outcomes.getPrice());
        TextView date=(TextView)convertView.findViewById(R.id.date);
        //Toast.makeText(context,outcomes.getDate(),Toast.LENGTH_LONG).show();
    try {
            DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date d=format.parse(outcomes.getDate());
            date.setText(new SimpleDateFormat("yyyy/MM/dd").format(d));
        }catch(Exception e){ Toast.makeText(context,e+"",Toast.LENGTH_LONG).show();}

        return convertView;
    }
}
