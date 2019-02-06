package com.mozi.application.model;

public class Price {

    private String was;
    private String then1;
    private String then2;
    private String now;
    private String uom;
    private String currency;


    public Price() {
    }

    public Price(String was, String then1, String then2, String now, String uom, String currency) {
        this.was = was;
        this.then1 = then1;
        this.then2 = then2;
        this.now = now;
        this.uom = uom;
        this.currency = currency;
    }

    public String getWas() {
        return was;
    }

    public void setWas(String was) {
        this.was = was;
    }

    public String getThen1() {
        return then1;
    }

    public void setThen1(String then1) {
        this.then1 = then1;
    }

    public String getThen2() {
        return then2;
    }

    public void setThen2(String then2) {
        this.then2 = then2;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (was != null ? !was.equals(price.was) : price.was != null) return false;
        if (then1 != null ? !then1.equals(price.then1) : price.then1 != null) return false;
        if (then2 != null ? !then2.equals(price.then2) : price.then2 != null) return false;
        if (now != null ? !now.equals(price.now) : price.now != null) return false;
        if (uom != null ? !uom.equals(price.uom) : price.uom != null) return false;
        return currency != null ? currency.equals(price.currency) : price.currency == null;
    }

    @Override
    public int hashCode() {
        int result = was != null ? was.hashCode() : 0;
        result = 31 * result + (then1 != null ? then1.hashCode() : 0);
        result = 31 * result + (then2 != null ? then2.hashCode() : 0);
        result = 31 * result + (now != null ? now.hashCode() : 0);
        result = 31 * result + (uom != null ? uom.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
