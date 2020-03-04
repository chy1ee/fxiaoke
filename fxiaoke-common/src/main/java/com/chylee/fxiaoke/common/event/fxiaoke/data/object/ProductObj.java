package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

/**
 * 产品
 */
public class ProductObj extends DataObject {
    private String product_code;    //产品编码
    private String name;    //产品名称
    private String product_spec;    //规格属性
    private String unit;    //单位

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_spec() {
        return product_spec;
    }

    public void setProduct_spec(String product_spec) {
        this.product_spec = product_spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
