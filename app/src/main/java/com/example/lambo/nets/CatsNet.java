package com.example.lambo.nets;

import com.android.volley.VolleyError;
import com.example.lambo.dataclasss.Cat;
import com.example.lambo.other.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class CatsNet extends BaseNet{
    final static String TAG = "lambo";
    public NetCallBack callBack;
    public ArrayList<Cat> cats;
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
        this.cats = gson.fromJson(response, new TypeToken<ArrayList<Cat>>() {}.getType());
        callBack.netResponse(this);
    }
}
