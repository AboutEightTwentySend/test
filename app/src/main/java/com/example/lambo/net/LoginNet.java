package com.example.lambo.net;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class LoginNet extends BaseNet {
    public LoginNet(HashMap hashMap){
        initNet(POST, "https://api.baicaiyx.com/v1/auth/login", new JSONObject(hashMap));
    }
    @Override
    public void netErrorResponse(VolleyError error) {

    }

    @Override
    public void netResponse(String response) {

    }
}
