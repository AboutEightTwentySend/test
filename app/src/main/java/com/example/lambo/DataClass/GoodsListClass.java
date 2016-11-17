package com.example.lambo.DataClass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public class GoodsListClass implements Serializable {
    private Object query;
    private ArrayList<GoodsClass> rows;
    private boolean hasMore;

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }

    public ArrayList<GoodsClass> getRows() {
        return rows;
    }

    public void setRows(ArrayList<GoodsClass> rows) {
        this.rows = rows;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "{" +
                "query:" + query +
                ", rows:" + rows +
                ", hasMore:" + hasMore +
                '}';
    }
}
