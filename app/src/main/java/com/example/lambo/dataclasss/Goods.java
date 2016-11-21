package com.example.lambo.dataclasss;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/21.
 */
public class Goods implements Serializable {
    private String goodsName;
    private String description;
    private int goodsId;
    private String images;
    private boolean isHot;
    private boolean isBest;
    private boolean isNew;
    private String priceMax;
    private String priceMin;
    private ArrayList<Product> products;
    @SerializedName(value="attrs",alternate={"attr"})
    private ArrayList<Attr> attrs;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    public boolean isBest() {
        return isBest;
    }

    public void setIsBest(boolean isBest) {
        this.isBest = isBest;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Attr> getAttrs() {
        return attrs;
    }

    public void setAttrs(ArrayList<Attr> attr) {
        this.attrs = attrs;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsName:'" + goodsName + '\'' +
                ", description:'" + description + '\'' +
                ", goodsId:" + goodsId +
                ", images:'" + images + '\'' +
                ", isHot:" + isHot +
                ", isBest:" + isBest +
                ", isNew:" + isNew +
                ", priceMax:'" + priceMax + '\'' +
                ", priceMin:'" + priceMin + '\'' +
                ", products:" + products +
                ", attrs:" + attrs +
                '}';
    }
}
