package com.mozi.application.model;

import java.util.ArrayList;
import java.util.Map;

public class Product {

    private String productId;
    private String title;
    private ArrayList<Color> colorSwatches;
    private String skuid;
    private String nowPrice;
    private String labelType;


    public Product() {
    }

    public Product(String productId, String title, ArrayList<Color> colorSwatches, String skuid, String nowPrice, String labelType) {
        this.productId = productId;
        this.title = title;
        this.colorSwatches = colorSwatches;
        this.skuid = skuid;
        this.nowPrice = nowPrice;
        this.labelType = labelType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Color> getColorSwatches() {
        return colorSwatches;
    }

    public void setColorSwatches(ArrayList<Color> colorSwatches) {
        this.colorSwatches = colorSwatches;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
}
