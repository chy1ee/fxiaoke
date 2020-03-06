package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

public class AccountAddrObj extends DataObject {
    private String remark;      //备注
    private String contact_id;   //联系人
    private String add_type;    //地址类型
    private String contact_way; //联系方式
    private String zipcode; //邮政编码
    private String account_id;  //客户名称
    private String address; //地址
    private boolean is_default_add;
    private boolean is_ship_to_add;

    public AccountAddrObj() {}

    public AccountAddrObj(String address) {
        this.address = address;
        this.is_default_add = true;
        this.is_ship_to_add = true;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getAdd_type() {
        return add_type;
    }

    public void setAdd_type(String add_type) {
        this.add_type = add_type;
    }

    public String getContact_way() {
        return contact_way;
    }

    public void setContact_way(String contact_way) {
        this.contact_way = contact_way;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIs_default_add() {
        return is_default_add;
    }

    public void setIs_default_add(boolean is_default_add) {
        this.is_default_add = is_default_add;
    }

    public boolean isIs_ship_to_add() {
        return is_ship_to_add;
    }

    public void setIs_ship_to_add(boolean is_ship_to_add) {
        this.is_ship_to_add = is_ship_to_add;
    }
}
