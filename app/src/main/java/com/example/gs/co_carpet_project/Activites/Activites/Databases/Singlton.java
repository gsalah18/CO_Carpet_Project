package com.example.gs.co_carpet_project.Activites.Activites.Databases;

import android.content.Context;
import android.support.v4.util.Pools;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by GSalah on 3/19/2017.
 */

public class Singlton {
    private static Singlton Instance;
    private RequestQueue queue;
    private Context context;

    public Singlton(Context context) {
        this.context = context;
        queue=getRequestQueue();
    }
    public RequestQueue getRequestQueue(){
        if(queue==null){
            queue= Volley.newRequestQueue(context);
        }
        return queue;
    }
    public static synchronized Singlton getInstance(Context context){
        if(Instance==null)
            Instance=new Singlton(context);
        return Instance;
    }
    public void AddtoRequest(Request request){
        queue.add(request);
    }
}
