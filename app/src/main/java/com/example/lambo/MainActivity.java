package com.example.lambo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SimpleAdapter;
import com.example.lambo.UI.MyListView;
import com.example.lambo.net.BaseNet;
import com.example.lambo.net.CatsNet;
import com.example.lambo.net.LoginNet;
import com.example.lambo.other.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseNet.NetCallBack {
    public final static String TAG = "lambo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyListView listView = (MyListView) findViewById(R.id.lv);
        List<HashMap<String,String>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("name","name "+i);
            list.add(hashMap);
        }
        new Thread(new CatsNet(this)).run();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("phonenum","18306677680");
        hashMap.put("password", "123456");
        new Thread(new LoginNet(hashMap)).run();
        listView.setAdapter(new SimpleAdapter(this,list,R.layout.list_item,new String[]{"name"},new int[]{R.id.tv}));
    }

    @Override
    public void netErrorResponse(BaseNet net) {
        Log.d(TAG, "netErrorResponse: "+net.error.toString());
    }

    @Override
    public void netResponse(BaseNet net) {
        if (net.url == URL.GET_CATS){
            CatsNet catsNet = (CatsNet) net;
            Log.d(TAG, "netResponse: "+catsNet.cats.toString());
        }
    }
}
