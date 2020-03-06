package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 */
public class ContactObj extends DataObject implements Comparable<ContactObj> {
    private String name;    //姓名
    private String account_id;
    private String tel1;    //电话1
    private String tel2;    //
    private String tel3;
    private String mobile1;
    private String email;
    private String company;
    private Long last_modified_time;
    private List<String> owner; //负责人

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getLast_modified_time() {
        return last_modified_time;
    }

    public void setLast_modified_time(Long last_modified_time) {
        this.last_modified_time = last_modified_time;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    public void addOwner(String o) {
        if(owner == null)
            owner = new ArrayList();
        owner.add(o);
    }

    @Override
    public int compareTo(ContactObj o) {
        // 大于0，正序
        return this.getLast_modified_time().compareTo(o.getLast_modified_time()) < 0 ? 1 : -1;
    }
}
