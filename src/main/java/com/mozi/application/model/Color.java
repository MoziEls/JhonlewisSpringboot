package com.mozi.application.model;

public class Color {

    private String color;
    private String basicColor;
    private String colorSwatchUrl;

    private String skuId;

    public Color(String color, String basicColor, String colorSwatchUrl, String skuId) {
        this.color = color;
        this.basicColor = basicColor;
        this.colorSwatchUrl = colorSwatchUrl;
        this.skuId = skuId;
    }

    public Color() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBasicColor() {
        return basicColor;
    }

    public void setBasicColor(String basicColor) {
        this.basicColor = basicColor;
    }

    public String getColorSwatchUrl() {
        return colorSwatchUrl;
    }

    public void setColorSwatchUrl(String colorSwatchUrl) {
        this.colorSwatchUrl = colorSwatchUrl;
    }

    

    

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
