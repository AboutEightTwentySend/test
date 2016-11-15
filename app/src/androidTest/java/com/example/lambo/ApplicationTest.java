package com.example.lambo;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.example.lambo.net.BaseNet;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BaseNet.setContext(this);
    }
}