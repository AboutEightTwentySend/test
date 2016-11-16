package com.example.lambo.net;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.lambo.DataClass.CatsClass;
import com.example.lambo.other.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class CatsNet extends BaseNet{
    final static String TAG = "lambo";
    public NetCallBack callBack;
    public ArrayList<CatsClass> cats;
    public CatsNet(NetCallBack callBack){
        initNet(GET, URL.GET_CATS);
        this.callBack = callBack;
    }

    @Override
    public void netErrorResponse(VolleyError error) {
        callBack.netErrorResponse(this);
    }

    @Override
    public void netResponse(String response) {
        Gson gson = new Gson();
        this.cats = gson.fromJson(response, new TypeToken<ArrayList<CatsClass>>() {}.getType());
        callBack.netResponse(this);
    }
}
