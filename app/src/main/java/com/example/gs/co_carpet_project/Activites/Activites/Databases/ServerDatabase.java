package com.example.gs.co_carpet_project.Activites.Activites.Databases;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.gs.co_carpet_project.Activites.Activites.Others.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class ServerDatabase {
    Context context;

    public ServerDatabase(Context context) {
        this.context = context;
    }
    public void Add(String link, final HashMap<String,String>param){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL(link), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Message(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message(error + "");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }
        };
        Singlton.getInstance(context).AddtoRequest(stringRequest);
    }
    void Delete(String link, HashMap<String,String>param){

    }

    public LinkedList<String>getGood(){
        final LinkedList<String>Data=new LinkedList<>();
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.POST,Constants.URL("GetGood.php"),null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(final JSONArray response) {

                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject obj=response.getJSONObject(i);
                            Data.add(obj.getString("name"));

                            Message(Arrays.toString(Data.toArray()));
                        } catch (JSONException e) {
                            Message(e+"");
                        }
                    }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message(error+"");
            }
        });

        Singlton.getInstance(context).AddtoRequest(request);

        return Data;
    }
    public void Message(String msg){
        AlertDialog.Builder adb=new AlertDialog.Builder(this.context);
        adb.setMessage(msg);
        adb.setNegativeButton("حسنا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.show();
    }
}
