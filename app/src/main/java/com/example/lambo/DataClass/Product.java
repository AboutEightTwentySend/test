package com.example.lambo.dataclass;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sEEyOU on 2016/11/21.
 */
public class Product implements Serializable{
    private String images;
    private String note;
    private ArrayList<price> prices;
    private int productId;
    private String productName;
    private String unit;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<price> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<price> prices) {
        this.prices = prices;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Product{" +
                "images:'" + images + '\'' +
                ", note:'" + note + '\'' +
                ", prices:" + prices +
                ", productId:" + productId +
                ", productName:'" + productName + '\'' +
                ", unit:'" + unit + '\'' +
                '}';
    }

    public class price implements Serializable{
        private int userRankId;
        private float memberPrice;

        public int getUserRankId() {
            return userRankId;
        }

        public void setUserRankId(int userRankId) {
            this.userRankId = userRankId;
        }

        public float getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(float memberPrice) {
            this.memberPrice = memberPrice;
        }

        @Override
        public String toString() {
            return "prices{" +
                    "userRankId:" + userRankId +
                    ", memberPrice:" + memberPrice +
                    '}';
        }
    }
}
