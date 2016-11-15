package com.example.lambo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.lambo.UI.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        listView.setAdapter(new SimpleAdapter(this,list,R.layout.list_item,new String[]{"name"},new int[]{R.id.tv}));
    }
}
