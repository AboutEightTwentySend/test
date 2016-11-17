package com.example.lambo.DataClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sEEyOU on 2016/11/17.
 */
public class GoodsClass implements Serializable {
    private int goodsId;
    private String images;
    private String goodsName;
    private String description;
    private String priceMin;
    private String priceMax;
    private boolean isHot;
    private boolean isBest;
    private boolean isNew;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

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

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
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

    @Override
    public String toString() {
        return "{" +
                "goodsId:" + goodsId +
                ", images:'" + images + '\'' +
                ", goodsName:'" + goodsName + '\'' +
                ", description:'" + description + '\'' +
                ", priceMin:" + priceMin +
                ", priceMax:" + priceMax +
                ", isHot:" + isHot +
                ", isBest:" + isBest +
                ", isNew:" + isNew +
                '}';
    }
}
