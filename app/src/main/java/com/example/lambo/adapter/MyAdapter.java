package com.example.lambo.adapter;

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public abstract class MyAdapter<T> extends BaseAdapter {
    public MyAdapter(){

    }

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
