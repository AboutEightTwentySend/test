package com.example.lambo.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/11/15.
 */
public abstract class BaseNet {
    private static Context context;
    private static RequestQueue queue;
    public static void setContext(Context _context) {
        context = _context;
        if (queue != null){

            queue = Volley.newRequestQueue(context);
        }
    }
}
