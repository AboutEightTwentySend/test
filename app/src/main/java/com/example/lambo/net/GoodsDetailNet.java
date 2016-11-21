package com.example.lambo.net;

import com.android.volley.VolleyError;
import com.example.lambo.dataclass.Goods;
import com.example.lambo.other.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public class GoodsDetailNet extends BaseNet {
    final static String TAG = "lambo";
    public NetCallBack callBack;
    public int goodsId;
    public Goods goodsDetail;

    public GoodsDetailNet(int goodsId, NetCallBack callBack) {
        initNet(GET, URL.GET_GOODS_DETAIL + "/" + goodsId);
        this.callBack = callBack;
        this.goodsId = goodsId;
    }

    @Override
    public void netErrorResponse(VolleyError error) {
        callBack.netErrorResponse(this);
    }

    @Override
    public void netResponse(String response) {
        Gson gson = new Gson();
        this.goodsDetail = gson.fromJson(response, new TypeToken<Goods>() {}.getType());
        callBack.netResponse(this);
    }
}