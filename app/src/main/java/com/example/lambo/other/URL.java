package com.example.lambo.other;

import com.example.lambo.net.BaseNet;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class URL {
    public final static String HOST = "https://api.baicaiyx.com/v1";
    public final static String QINIU = "https://o86r83cfz.qnssl.com";
    public final static String GET_CATS = HOST + "/categories";
    public final static String GET_CATS_CHILDREN = HOST + "/categories/tree/";
    public final static String GET_GOODS_LIST = HOST + "/goods/list";


    public static void throwNet(BaseNet net){
        new Thread(net).run();
    }
}
