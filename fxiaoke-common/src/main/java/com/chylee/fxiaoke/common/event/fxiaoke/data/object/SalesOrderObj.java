package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单
 */
public class SalesOrderObj extends DataObject {
    private String name;        //销售订单编号
    private String discount;    //整单折扣(%)
    private String order_time;  //下单日期
    private String ship_to_id;  //收货人   关联联系人ID
    private String ship_to_add; //收货地址
    private String ship_to_tel; //收货人电话
    private String ship_to_fax; //传真
    private BigDecimal order_amount;    //销售订单金额(元)
    private String quote_id;    //报价单
    private String delivery_date;   //交货日期;
    private String remark;  //备注
    private String account_id;  //客户名称  关联客户ID
    private String field_4xWXT__c;  //单号
    private String field_4m6gr__c;  //单别
    private String price_book_id;   //价目表
    private List<String> owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getShip_to_id() {
        return ship_to_id;
    }

    public void setShip_to_id(String ship_to_id) {
        this.ship_to_id = ship_to_id;
    }

    public String getShip_to_add() {
        return ship_to_add;
    }

    public void setShip_to_add(String ship_to_add) {
        this.ship_to_add = ship_to_add;
    }

    public String getShip_to_tel() {
        return ship_to_tel;
    }

    public void setShip_to_tel(String ship_to_tel) {
        this.ship_to_tel = ship_to_tel;
    }

    public BigDecimal getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(BigDecimal order_amount) {
        this.order_amount = order_amount;
    }

    public String getQuote_id() {
        return quote_id;
    }

    public void setQuote_id(String quote_id) {
        this.quote_id = quote_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getField_4xWXT__c() {
        return field_4xWXT__c;
    }

    public void setField_4xWXT__c(String field_4xWXT__c) {
        this.field_4xWXT__c = field_4xWXT__c;
    }

    public String getField_4m6gr__c() {
        return field_4m6gr__c;
    }

    public void setField_4m6gr__c(String field_4m6gr__c) {
        this.field_4m6gr__c = field_4m6gr__c;
    }

    public String getPrice_book_id() {
        return price_book_id;
    }

    public void setPrice_book_id(String price_book_id) {
        this.price_book_id = price_book_id;
    }

    public String getShip_to_fax() {
        return ship_to_fax;
    }

    public void setShip_to_fax(String ship_to_fax) {
        this.ship_to_fax = ship_to_fax;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    public void addOwner(String owner) {
        if(this.owner == null)
            this.owner = new ArrayList<>();
        this.owner.add(owner);
    }
}
