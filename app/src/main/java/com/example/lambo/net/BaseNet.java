package com.example.lambo.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.lambo.other.NetRequest;

import org.json.JSONException;
import org.json.JSONObject;

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
        request.setShouldCache(false);
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

    public void initNet(int method, String url,JSONObject body) {
        this.url = url;
        this.method = method;
        this.body = body;
    }
    public void initNet(int method, String url) {
        this.url = url;
        this.method = method;
        this.body = null;
    }

    public abstract void netErrorResponse(VolleyError error);
    public abstract void netResponse(String response);

    public interface NetCallBack{
        void netErrorResponse(BaseNet net);
        void netResponse(BaseNet net);
    }
}
