package com.example.demohttp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

public class SingletonQueue
{
    private static SingletonQueue mInstance;
    private RequestQueue requestQueue;
    private static Context cont;

    public SingletonQueue(Context cont){
        this.cont = cont;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
//            requestQueue = Volley.newRequestQueue(cont);
//
//            Network network = new BasicNetwork(new OkHttpStack());
//            RequestQueue queue = new RequestQueue(new DiskBasedCache(new File(getCacheDir(), "volley")), network);
//            queue.start();
            //requestQueue = Volley.newRequestQueue(cont);

            requestQueue = Volley.newRequestQueue(cont,new OkHttp3Stack(new OkHttpClient()));
        }
        return requestQueue;
    }

    public static synchronized SingletonQueue getInstance(Context cont)
    {
        if(mInstance==null)
        {
            mInstance = new SingletonQueue(cont);
        }
        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request,String tag)
    {
        request.setTag(tag);
        Log.d(tag, "addToRequestQueue: "+tag);
        requestQueue.add(request);
    }


    public void cancelRequest(String tag)
    {
        if(requestQueue != null)
        {
            Log.d("Request Cancel", "cancelRequest: "+tag);
            requestQueue.cancelAll(tag);
        }
    }
}

