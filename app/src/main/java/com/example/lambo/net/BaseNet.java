package com.example.lambo.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.lambo.other.NetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/15.
 */
public abstract class BaseNet implements Runnable, Response.ErrorListener, Response.Listener<JSONObject> {
    public final static String TAG = "lambo";
    public final static int GET = 0;
    public final static int POST = 1;

    public String url = "";
    public int method = 0;
    public JSONObject body;
    public VolleyError error;

    private static Context context;
    private static RequestQueue queue;

    public static void setContext(Context _context) {
        context = _context;
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
    }

    @Override
    public void run() {
        NetRequest request = new NetRequest(method, url, body, this, this);
        request.setShouldCache(method == GET);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        this.error = error;
        netErrorResponse(error);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            String jsonString = response.getString("response");
            netResponse(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initNet(int method, String url, HashMap has) {
        this.method = method;
        Iterator i = has.entrySet().iterator();
        if (method == GET){
            if (has.size() > 0) url += "?";
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
                url += entry.getKey() + "=" + entry.getValue();
                if (i.hasNext()) url += "&";
            }
        }else{
            this.body = new JSONObject(has);
        }
        this.url = url;
    }

    public void initNet(int method, String url) {
        this.method = method;
        this.url = url;
        this.body = null;
    }

    public abstract void netErrorResponse(VolleyError error);

    public abstract void netResponse(String response);

    public interface NetCallBack {
        void netErrorResponse(BaseNet net);

        void netResponse(BaseNet net);
    }
}
