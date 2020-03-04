package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户
 */
public class AccountObj extends DataObject {
    private String _id;             //id
    private String name;            //客户名称
    private String account_type;    //客户类型
    private String account_no;      //客户编号
    private String tel;             //电话
    private String fax;             //传真
    private String address;         //地址
    private AreaLocation area_location;   //地区定位
    private String back_reason;     //销售人员退回原因
    private String industry_level2; //2级行业
    private String industry_level1; //1级行业
    private String account_level;   //客户级别
    private String remark;          //备注
    private String email;           //邮件
    private String url;             //网址
    private String account_source;  //来源
    private String field_5QF0R__c;  //注册号
    private String field_20jT0__c;  //统一社会信用代码
    private String field_e4t2b__c;  //客户简称
    private String field_Zmaa2__c;  //登记状态
    private String field_b3Td0__c;  //公司类型
    private Date field_2740P__c;    //成立日期
    private String field_mgv5h__c;  //法定代表人
    private String field_1Juaa__c;  //注册资本
    private String field_0CE1m__c;  //登记机关
    private Date field_yX1h1__c;    //核准日期
    private String field_7d693__c;  //经营范围
    private Date field_qsy4t__c;    //营业期限自
    private Date field_6ZJk5__c;    //营业期限至
    private String field_c7xeo__c;  //客户所属省区
    private String field_PlekP__c;  //客户所属区域代码
    private String field_p3Bv1__c;  //客户名称首个拼音字母
    private String field_AlGoN__c;  //客户编号
    private List<String> owner;

    private String ywry;

    @Override
    public String get_id() {
        return _id;
    }

    @Override
    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public AreaLocation getArea_location() {
        return area_location;
    }

    public void setArea_location(AreaLocation area_location) {
        this.area_location = area_location;
    }

    public String getBack_reason() {
        return back_reason;
    }

    public void setBack_reason(String back_reason) {
        this.back_reason = back_reason;
    }

    public String getIndustry_level2() {
        return industry_level2;
    }

    public void setIndustry_level2(String industry_level2) {
        this.industry_level2 = industry_level2;
    }

    public String getIndustry_level1() {
        return industry_level1;
    }

    public void setIndustry_level1(String industry_level1) {
        this.industry_level1 = industry_level1;
    }

    public String getAccount_level() {
        return account_level;
    }

    public void setAccount_level(String account_level) {
        this.account_level = account_level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAccount_source() {
        return account_source;
    }

    public void setAccount_source(String account_source) {
        this.account_source = account_source;
    }

    public String getField_5QF0R__c() {
        return field_5QF0R__c;
    }

    public void setField_5QF0R__c(String field_5QF0R__c) {
        this.field_5QF0R__c = field_5QF0R__c;
    }

    public String getField_20jT0__c() {
        return field_20jT0__c;
    }

    public void setField_20jT0__c(String field_20jT0__c) {
        this.field_20jT0__c = field_20jT0__c;
    }

    public String getField_e4t2b__c() {
        return field_e4t2b__c;
    }

    public void setField_e4t2b__c(String field_e4t2b__c) {
        this.field_e4t2b__c = field_e4t2b__c;
    }

    public String getField_Zmaa2__c() {
        return field_Zmaa2__c;
    }

    public void setField_Zmaa2__c(String field_Zmaa2__c) {
        this.field_Zmaa2__c = field_Zmaa2__c;
    }

    public String getField_b3Td0__c() {
        return field_b3Td0__c;
    }

    public void setField_b3Td0__c(String field_b3Td0__c) {
        this.field_b3Td0__c = field_b3Td0__c;
    }

    public Date getField_2740P__c() {
        return field_2740P__c;
    }

    public void setField_2740P__c(Date field_2740P__c) {
        this.field_2740P__c = field_2740P__c;
    }

    public String getField_mgv5h__c() {
        return field_mgv5h__c;
    }

    public void setField_mgv5h__c(String field_mgv5h__c) {
        this.field_mgv5h__c = field_mgv5h__c;
    }

    public String getField_1Juaa__c() {
        return field_1Juaa__c;
    }

    public void setField_1Juaa__c(String field_1Juaa__c) {
        this.field_1Juaa__c = field_1Juaa__c;
    }

    public String getField_0CE1m__c() {
        return field_0CE1m__c;
    }

    public void setField_0CE1m__c(String field_0CE1m__c) {
        this.field_0CE1m__c = field_0CE1m__c;
    }

    public Date getField_yX1h1__c() {
        return field_yX1h1__c;
    }

    public void setField_yX1h1__c(Date field_yX1h1__c) {
        this.field_yX1h1__c = field_yX1h1__c;
    }

    public String getField_7d693__c() {
        return field_7d693__c;
    }

    public void setField_7d693__c(String field_7d693__c) {
        this.field_7d693__c = field_7d693__c;
    }

    public Date getField_qsy4t__c() {
        return field_qsy4t__c;
    }

    public void setField_qsy4t__c(Date field_qsy4t__c) {
        this.field_qsy4t__c = field_qsy4t__c;
    }

    public Date getField_6ZJk5__c() {
        return field_6ZJk5__c;
    }

    public void setField_6ZJk5__c(Date field_6ZJk5__c) {
        this.field_6ZJk5__c = field_6ZJk5__c;
    }

    public String getField_c7xeo__c() {
        return field_c7xeo__c;
    }

    public void setField_c7xeo__c(String field_c7xeo__c) {
        this.field_c7xeo__c = field_c7xeo__c;
    }

    public String getField_PlekP__c() {
        return field_PlekP__c;
    }

    public void setField_PlekP__c(String field_PlekP__c) {
        this.field_PlekP__c = field_PlekP__c;
    }

    public String getField_p3Bv1__c() {
        return field_p3Bv1__c;
    }

    public void setField_p3Bv1__c(String field_p3Bv1__c) {
        this.field_p3Bv1__c = field_p3Bv1__c;
    }

    public String getField_AlGoN__c() {
        return field_AlGoN__c;
    }

    public void setField_AlGoN__c(String field_AlGoN__c) {
        this.field_AlGoN__c = field_AlGoN__c;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getYwry() {
        return ywry;
    }

    public void setYwry(String ywry) {
        this.ywry = ywry;
    }
}
