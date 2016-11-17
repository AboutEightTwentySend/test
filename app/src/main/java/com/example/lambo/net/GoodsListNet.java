package com.example.lambo.net;

import android.util.Log;

import com.android.volley.VolleyError;
import com.example.lambo.DataClass.CatClass;
import com.example.lambo.DataClass.GoodsListClass;
import com.example.lambo.other.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public class GoodsListNet extends BaseNet{
    final static String TAG = "lambo";
    public NetCallBack callBack;
    public int catId;
    public GoodsListClass goodsListData;
    public GoodsListNet(int catId, NetCallBack callBack){
        HashMap<String,Integer> hasMap = new HashMap<>();
        hasMap.put("catId",catId);
        initNet(GET, URL.GET_GOODS_LIST,hasMap);
        this.callBack = callBack;
        this.catId = catId;
    }

    @Override
    public void netErrorResponse(VolleyError error) {
        callBack.netErrorResponse(this);
    }

    @Override
    public void netResponse(String response) {
        Log.d(TAG, "netResponse: "+response);
        Gson gson = new Gson();
        this.goodsListData = gson.fromJson(response, new TypeToken<GoodsListClass>() {}.getType());
        Log.d(TAG, "netResponse: "+goodsListData.toString());
        callBack.netResponse(this);
    }
}