package com.example.lambo.dataclasss;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public class GoodsList implements Serializable {
    private Object query;
    private ArrayList<Goods> rows;
    private boolean hasMore;

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }

    public ArrayList<Goods> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Goods> rows) {
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
