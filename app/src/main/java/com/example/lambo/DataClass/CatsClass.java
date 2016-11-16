package com.example.lambo.DataClass;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class CatsClass implements Serializable {
    private int catId;
    private String catName;
    private ArrayList<CatsClass> children;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public ArrayList<CatsClass> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<CatsClass> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "{" +
                "catId:" + catId +
                ", catName:" + catName +
                ", children:" + children +
                "}";
    }
}
