package com.example.lambo.dataclasss;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/21.
 */
public class Attr implements Serializable {
    private int attrId;
    private String attrName;
    private ArrayList<Attr> children;
    private ArrayList<GoodsAttr> goodsAttrs;

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public ArrayList<Attr> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Attr> children) {
        this.children = children;
    }

    public ArrayList<GoodsAttr> getGoodsAttrs() {
        return goodsAttrs;
    }

    public void setGoodsAttrs(ArrayList<GoodsAttr> goodsAttrs) {
        this.goodsAttrs = goodsAttrs;
    }

    @Override
    public String toString() {
        return "Attr{" +
                "attrId:" + attrId +
                ", attrName:'" + attrName + '\'' +
                ", children:" + children +
                ", goodsAttrs:" + goodsAttrs +
                '}';
    }

    public class GoodsAttr implements Serializable{
        private String attrValue;
        private int goodsAttrId;
        private int attrId;
        private int parentId;

        public String getAttrValue() {
            return attrValue;
        }

        public void setAttrValue(String attrValue) {
            this.attrValue = attrValue;
        }

        public int getGoodsAttrId() {
            return goodsAttrId;
        }

        public void setGoodsAttrId(int goodsAttrId) {
            this.goodsAttrId = goodsAttrId;
        }

        public int getAttrId() {
            return attrId;
        }

        public void setAttrId(int attrId) {
            this.attrId = attrId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        @Override
        public String toString() {
            return "GoodsAttr{" +
                    "attrValue:'" + attrValue + '\'' +
                    ", goodsAttrId:" + goodsAttrId +
                    ", attrId:" + attrId +
                    ", parentId:" + parentId +
                    '}';
        }
    }
}
