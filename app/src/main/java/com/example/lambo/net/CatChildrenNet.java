package com.example.lambo.net;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.lambo.DataClass.CatClass;
import com.example.lambo.other.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public class CatChildrenNet  extends BaseNet{
    final static String TAG = "lambo";
    public NetCallBack callBack;
    public int catId;
    public CatClass cat;
    public CatChildrenNet(int catId,NetCallBack callBack){
        initNet(GET, URL.GET_CATS_CHILDREN);
        this.callBack = callBack;
        this.catId = catId;
        url += catId;
    }

    @Override
    public void netErrorResponse(VolleyError error) {
        callBack.netErrorResponse(this);
    }

    @Override
    public void netResponse(String response) {
        Gson gson = new Gson();
        Log.d(TAG, "netResponse: "+response);
        this.cat = gson.fromJson(response, new TypeToken<CatClass>() {}.getType());
        callBack.netResponse(this);
    }
}
