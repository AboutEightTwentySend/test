package com.example.lambo.net;

import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.lambo.DataClass.ZAXJGClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class ZAXJGnet extends BaseNet {
    NetCallBack callBack;
    public ZAXJGClass zaxjg;
    public ZAXJGnet(String phonenum,NetCallBack callBack){
        this.callBack = callBack;
        HashMap hashMap = new HashMap();
        hashMap.put("phonenum",phonenum);
        initNet(GET, "http://120.25.152.212:3030/data", hashMap);
    }
    @Override
    public void netErrorResponse(VolleyError error) {
        callBack.netErrorResponse(this);
    }

    @Override
    public void netResponse(String response) {
        Log.d(TAG, url+"netResponse: "+response);
        try {
            JSONObject js = new JSONObject(response);
            JSONObject data = js.getJSONObject("data");
            Gson gson = new Gson();
            zaxjg = gson.fromJson(data.toString(),new TypeToken<ZAXJGClass>(){}.getType());
            Log.d(TAG, "netResponse: "+zaxjg.toString());
            callBack.netResponse(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
