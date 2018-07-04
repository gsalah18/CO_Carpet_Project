package com.example.gs.co_carpet_project.Activites.Activites.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Bill_Frag_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Item_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Adapters.List_Paid_Adapter;
import com.example.gs.co_carpet_project.Activites.Activites.Databases.LocalDatabase;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Bills_class;
import com.example.gs.co_carpet_project.R;

import java.util.LinkedList;


public class Bills_Frag extends Fragment {

    ListView listView;
    String Customer;
    LocalDatabase DB;
    List_Bill_Frag_Adapter adapter;
    LinkedList<Bills_class>Data;
    ListView list;
    List_Paid_Adapter adapter1;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_bills, container, false);
        Bundle b=getArguments();
        Customer=b.getString("customer");
        DB=new LocalDatabase(getActivity());
        listView=(ListView) v.findViewById(R.id.listview);
        Refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder PaidOrItem=new AlertDialog.Builder(getActivity());
                PaidOrItem.setItems(new String[]{"تفاصيل الفاتوره", "الدفعات"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            AlertDialog.Builder ItemsDialog=new AlertDialog.Builder(getActivity());
                            LayoutInflater inflater1=LayoutInflater.from(getActivity());
                            View itemView=inflater1.inflate(R.layout.dialog_items,null);
                            ItemsDialog.setView(itemView);
                            final AlertDialog a=ItemsDialog.create();

                            ListView listView=(ListView)itemView.findViewById(R.id.listview);
                            listView.setAdapter(new List_Item_Adapter(getActivity(),R.layout.details_items
                                    ,DB.getItems(Data.get(position).getId(),Customer)));

                            ImageButton exit=(ImageButton)itemView.findViewById(R.id.exit);
                            exit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    a.cancel();
                                }
                            });
                            a.show();
                        }else{
                            AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                            LayoutInflater inflater1=LayoutInflater.from(getActivity());
                            View vv=inflater1.inflate(R.layout.list_dialog,null);
                            adb.setView(vv);
                            list=(ListView)vv.findViewById(R.id.listview);
                            adapter1=new List_Paid_Adapter(getActivity(),R.layout.list_payments,DB.getPaid(Data.get(position).getId(),Data.get(position).getCustomer()));
                            list.setAdapter(adapter1);
                            FloatingActionButton fab=(FloatingActionButton)vv.findViewById(R.id.fab);
                            fab.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                                    LayoutInflater inflater1=LayoutInflater.from(getActivity());
                                    View vv=inflater1.inflate(R.layout.add_paid,null);
                                    adb.setView(vv);
                                    final AlertDialog alert=adb.create();

                                    ImageButton exit=(ImageButton)vv.findViewById(R.id.exit);
                                    exit.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alert.cancel();
                                        }
                                    });

                                    final EditText price=(EditText)vv.findViewById(R.id.price);
                                    Button add=(Button)vv.findViewById(R.id.add);
                                    add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            DB.AddPaid(Data.get(position).getId(),Data.get(position).getCustomer(),Double.parseDouble(price.getText().toString()));
                                            Refresh();
                                            adapter1=new List_Paid_Adapter(getActivity(),R.layout.list_payments,DB.getPaid(Data.get(position).getId(),Data.get(position).getCustomer()));
                                            list.setAdapter(adapter1);
                                            alert.cancel();
                                            DB.Message("تم اضافه الدفعه");
                                        }
                                    });


                                    alert.show();

                                }
                            });

                            adb.show();

                        }

                    }
                });
                PaidOrItem.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                AlertDialog.Builder YesOrNo=new AlertDialog.Builder(getActivity());
                YesOrNo.setMessage("هل تريد حذف الفاتوره");
                YesOrNo.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DB.DeleteBill(Data.get(position).getId(),Customer);
                        DB.Message("تم حذف الفاتوره");
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
                return true;
            }
        });
        return v;
    }
    private void Refresh(){
        Data=DB.getBills(Customer);
        adapter=new List_Bill_Frag_Adapter(getActivity(),R.layout.details_bills,Data);
        listView.setAdapter(adapter);
    }

    @Override
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
