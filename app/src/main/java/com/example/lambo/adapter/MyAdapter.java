package com.example.lambo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.lambo.DataClass.GoodsClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public abstract class MyAdapter<T> extends BaseAdapter {
    public ArrayList<T> mList;

    public void setList(ArrayList<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public ArrayList<T> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mList == null ? 0 : position;
    }
}
