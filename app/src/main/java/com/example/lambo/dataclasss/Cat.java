package com.example.lambo.dataclasss;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/16.
 */
public class Cat implements Serializable {
    private int catId;
    private String catName;
    private ArrayList<Cat> children;

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

    public ArrayList<Cat> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Cat> children) {
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
