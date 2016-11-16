package com.example.lambo;

import android.app.Application;
import android.util.Log;

import com.example.lambo.net.BaseNet;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class LamboApplication extends Application {
    final static String TAG = "lambo";
    @Override
    public void onCreate() {
        super.onCreate();
        BaseNet.setContext(this);
    }
}