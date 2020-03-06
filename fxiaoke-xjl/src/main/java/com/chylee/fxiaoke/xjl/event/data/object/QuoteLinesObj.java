package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.math.BigDecimal;

/**
 * 报价单明细
 */
public class QuoteLinesObj extends DataObject {
    private String name;                        //报价单明细编码
    private BigDecimal selling_price;           //销售单价
    private String total_discount;              //总折扣
    private BigDecimal extra_discount_amount;   //额外折扣小计
    private String discount;                    //折扣
    private BigDecimal total_discount_amount;   //总折扣小计
    private String extra_discount;              //额外折扣
    private BigDecimal price;                   //价格(元)
    private String product_id;                  //产品名称
    private BigDecimal system_discount_amount;  //系统折扣小计
    private BigDecimal quantity;                //数量
    private String quote_lines_unit;            //单位
    private String quote_id;                    //报价单
    private BigDecimal sales_amount;            //销售金额小计
    private BigDecimal total_amount;            //报价小计
    private BigDecimal sales_price;             //报价(元)
    private String field_W9xsn__c;              //产品分类
    private String field_kUrd0__c;              //备注
    private String field_6XaI6__c;              //商品名称
    private String field_A5P2v__c;              //产品编码
    private String quote_lines_specs;           //规格

    //自定义
    private String bjddb;                       //报价单单别
    private String bjddh;                       //报价单单号
    private String mxxh;                        //明细序号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(BigDecimal selling_price) {
        this.selling_price = selling_price;
    }

    public String getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(String total_discount) {
        this.total_discount = total_discount;
    }

    public BigDecimal getExtra_discount_amount() {
        return extra_discount_amount;
    }

    public void setExtra_discount_amount(BigDecimal extra_discount_amount) {
        this.extra_discount_amount = extra_discount_amount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal_discount_amount() {
        return total_discount_amount;
    }

    public void setTotal_discount_amount(BigDecimal total_discount_amount) {
        this.total_discount_amount = total_discount_amount;
    }

    public String getExtra_discount() {
        return extra_discount;
    }

    public void setExtra_discount(String extra_discount) {
        this.extra_discount = extra_discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public BigDecimal getSystem_discount_amount() {
        return system_discount_amount;
    }

    public void setSystem_discount_amount(BigDecimal system_discount_amount) {
        this.system_discount_amount = system_discount_amount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getQuote_lines_unit() {
        return quote_lines_unit;
    }

    public void setQuote_lines_unit(String quote_lines_unit) {
        this.quote_lines_unit = quote_lines_unit;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }

    public BigDecimal getSales_amount() {
        return sales_amount;
    }

    public void setSales_amount(BigDecimal sales_amount) {
        this.sales_amount = sales_amount;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getSales_price() {
        return sales_price;
    }

    public void setSales_price(BigDecimal sales_price) {
        this.sales_price = sales_price;
    }

    public String getField_W9xsn__c() {
        return field_W9xsn__c;
    }

    public void setField_W9xsn__c(String field_W9xsn__c) {
        this.field_W9xsn__c = field_W9xsn__c;
    }

    public String getField_kUrd0__c() {
        return field_kUrd0__c;
    }

    public void setField_kUrd0__c(String field_kUrd0__c) {
        this.field_kUrd0__c = field_kUrd0__c;
    }

    public String getField_6XaI6__c() {
        return field_6XaI6__c;
    }

    public void setField_6XaI6__c(String field_6XaI6__c) {
        this.field_6XaI6__c = field_6XaI6__c;
    }

    public String getField_A5P2v__c() {
        return field_A5P2v__c;
    }

    public void setField_A5P2v__c(String field_A5P2v__c) {
        this.field_A5P2v__c = field_A5P2v__c;
    }

    public String getQuote_lines_specs() {
        return quote_lines_specs;
    }

    public void setQuote_lines_specs(String quote_lines_specs) {
        this.quote_lines_specs = quote_lines_specs;
    }

    public String getBjddb() {
        return bjddb;
    }

    public void setBjddb(String bjddb) {
        this.bjddb = bjddb;
    }

    public String getBjddh() {
        return bjddh;
    }

    public void setBjddh(String bjddh) {
        this.bjddh = bjddh;
    }

    public String getMxxh() {
        return mxxh;
    }

    public void setMxxh(String mxxh) {
        this.mxxh = mxxh;
    }
}
