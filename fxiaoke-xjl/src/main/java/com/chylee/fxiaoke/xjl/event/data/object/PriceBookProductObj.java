package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

public class PriceBookProductObj extends DataObject {
    private String pricebook_id;
    private String product_id;
    private String product_code;

    public String getPricebook_id() {
        return pricebook_id;
    }

    public void setPricebook_id(String pricebook_id) {
        this.pricebook_id = pricebook_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}
