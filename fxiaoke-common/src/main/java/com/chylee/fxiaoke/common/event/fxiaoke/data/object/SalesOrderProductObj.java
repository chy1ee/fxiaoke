package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.math.BigDecimal;

/**
 * 订单产品
 */
public class SalesOrderProductObj extends DataObject {
    private String discount;    //折扣
    private String remark;  //备注
    private BigDecimal product_price;   //金额
    private String product_id;  //产品名称  关联产品ID
    private BigDecimal quantity;    //数量
    private String unit;    //单位
    private BigDecimal subtotal;    //小计
    private BigDecimal sales_price; //单价
    private String order_id;    //订单ID 关联订单ID
    private String price_book_product_id;

    //自定义
    private String product_name;    //产品名称

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getSales_price() {
        return sales_price;
    }

    public void setSales_price(BigDecimal sales_price) {
        this.sales_price = sales_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPrice_book_product_id() {
        return price_book_product_id;
    }

    public void setPrice_book_product_id(String price_book_product_id) {
        this.price_book_product_id = price_book_product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
