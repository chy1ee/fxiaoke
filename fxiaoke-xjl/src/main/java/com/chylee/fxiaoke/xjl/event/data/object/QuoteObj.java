package com.chylee.fxiaoke.xjl.event.data.object;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 报价单
 */
public class QuoteObj extends DataObject {
    private String name;                    //报价单编码
    private BigDecimal quote_amount;        //报价单金额
    private String quote_discount;          //整单折扣
    private String opportunity_id;          //商机名称
    private BigDecimal quote_product_sum;   //产品合计
    private Date quote_time;                //报价日期
    private String account_id;              //买方
    private String field_7x3fj__c;          //买方联系人
    private String field_GB4f8__c;          //买方传真
    private String field_P5B2k__c;          //买方地址
    private String field_2bInU__c;          //卖方联系人
    private String field_uc1GT__c;          //卖方联系电话
    private String field_7s5vK__c;          //交货地点
    private String field_ix6cx__c;          //设备名称
    private String field_Jbtgr__c;          //射出量（CC）
    private String field_oEQHE__c;          //炮筒容量（CC）
    private String field_j86il__c;          //锁模力
    private String field_ambw6__c;          //轴式
    private String field_X4422__c;          //马力
    private String field_Np59i__c;          //电压
    private String field_tevVi__c;          //控制系统
    private String field_mIx4B__c;          //油路系统
    private String field_x6Dj4__c;          //主缸行程
    private String field_t21Ku__c;          //开模方式
    private String field_w8AF1__c;          //机台颜色
    private String field_4u1m4__c;          //工作台面
    private String field_ttSo3__c;          //真空泵型号
    private String field_04619__c;          //真空罩内腔有效高度
    private String field_4M62h__c;          //锁模尺寸
    private String field_0Ar72__c;          //机台尺寸
    private String field_ns4nt__c;          //机台工具箱
    private String field_FrJQF__c;          //报价币种
    private String field_4j62N__c;          //审批类型
    private String field_ms1FQ__c;          //买方联系电话
    private String field_N46xB__c;          //总额外折扣
    private String field_321cQ__c;          //让利总金额
    private String field_c5MbM__c;          //交货期
    private String field_vlgrq__c;          //付款方式
    private String field_g2n94__c;          //商品名称
    private String field_EHc11__c;          //射台
    private String field_w1seB__c;          //螺杆直径
    private String field_ezsuA__c;          //标准配置
    private String field_Jn2iy__c;          //供料系统
    private String field_tFd1q__c;          //设备型号
    private String field_h1rW5__c;          //其它选配装置
    private String field_j2oq2__c;          //其它设备信息备注
    private String field_SS32r__c;          //报价单单号
    private String field_kq20e__c;          //报价单单别
    private String field_kRg8S__c;          //报价货币名称
    private String field_UW798__c;          //卖方联系人员工ID
    private String field_f81BW__c;          //含税说明
    private String field_o3LE5__c;          //ERP对接提示
    private List<String> owner;             //负责人

    //自定义
    private String xq1;
    private String xq2;
    private String account_name;            //买方名称
    private String account_bh;              //买方编号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getQuote_amount() {
        return quote_amount;
    }

    public void setQuote_amount(BigDecimal quote_amount) {
        this.quote_amount = quote_amount;
    }

    public String getQuote_discount() {
        return quote_discount;
    }

    public void setQuote_discount(String quote_discount) {
        this.quote_discount = quote_discount;
    }

    public String getOpportunity_id() {
        return opportunity_id;
    }

    public void setOpportunity_id(String opportunity_id) {
        this.opportunity_id = opportunity_id;
    }

    public BigDecimal getQuote_product_sum() {
        return quote_product_sum;
    }

    public void setQuote_product_sum(BigDecimal quote_product_sum) {
        this.quote_product_sum = quote_product_sum;
    }

    public Date getQuote_time() {
        return quote_time;
    }

    public void setQuote_time(Date quote_time) {
        this.quote_time = quote_time;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getField_7x3fj__c() {
        return field_7x3fj__c;
    }

    public void setField_7x3fj__c(String field_7x3fj__c) {
        this.field_7x3fj__c = field_7x3fj__c;
    }

    public String getField_GB4f8__c() {
        return field_GB4f8__c;
    }

    public void setField_GB4f8__c(String field_GB4f8__c) {
        this.field_GB4f8__c = field_GB4f8__c;
    }

    public String getField_P5B2k__c() {
        return field_P5B2k__c;
    }

    public void setField_P5B2k__c(String field_P5B2k__c) {
        this.field_P5B2k__c = field_P5B2k__c;
    }

    public String getField_2bInU__c() {
        return field_2bInU__c;
    }

    public void setField_2bInU__c(String field_2bInU__c) {
        this.field_2bInU__c = field_2bInU__c;
    }

    public String getField_uc1GT__c() {
        return field_uc1GT__c;
    }

    public void setField_uc1GT__c(String field_uc1GT__c) {
        this.field_uc1GT__c = field_uc1GT__c;
    }

    public String getField_7s5vK__c() {
        return field_7s5vK__c;
    }

    public void setField_7s5vK__c(String field_7s5vK__c) {
        this.field_7s5vK__c = field_7s5vK__c;
    }

    public String getField_ix6cx__c() {
        return field_ix6cx__c;
    }

    public void setField_ix6cx__c(String field_ix6cx__c) {
        this.field_ix6cx__c = field_ix6cx__c;
    }

    public String getField_Jbtgr__c() {
        return field_Jbtgr__c;
    }

    public void setField_Jbtgr__c(String field_Jbtgr__c) {
        this.field_Jbtgr__c = field_Jbtgr__c;
    }

    public String getField_oEQHE__c() {
        return field_oEQHE__c;
    }

    public void setField_oEQHE__c(String field_oEQHE__c) {
        this.field_oEQHE__c = field_oEQHE__c;
    }

    public String getField_j86il__c() {
        return field_j86il__c;
    }

    public void setField_j86il__c(String field_j86il__c) {
        this.field_j86il__c = field_j86il__c;
    }

    public String getField_ambw6__c() {
        return field_ambw6__c;
    }

    public void setField_ambw6__c(String field_ambw6__c) {
        this.field_ambw6__c = field_ambw6__c;
    }

    public String getField_X4422__c() {
        return field_X4422__c;
    }

    public void setField_X4422__c(String field_X4422__c) {
        this.field_X4422__c = field_X4422__c;
    }

    public String getField_Np59i__c() {
        return field_Np59i__c;
    }

    public void setField_Np59i__c(String field_Np59i__c) {
        this.field_Np59i__c = field_Np59i__c;
    }

    public String getField_tevVi__c() {
        return field_tevVi__c;
    }

    public void setField_tevVi__c(String field_tevVi__c) {
        this.field_tevVi__c = field_tevVi__c;
    }

    public String getField_mIx4B__c() {
        return field_mIx4B__c;
    }

    public void setField_mIx4B__c(String field_mIx4B__c) {
        this.field_mIx4B__c = field_mIx4B__c;
    }

    public String getField_x6Dj4__c() {
        return field_x6Dj4__c;
    }

    public void setField_x6Dj4__c(String field_x6Dj4__c) {
        this.field_x6Dj4__c = field_x6Dj4__c;
    }

    public String getField_t21Ku__c() {
        return field_t21Ku__c;
    }

    public void setField_t21Ku__c(String field_t21Ku__c) {
        this.field_t21Ku__c = field_t21Ku__c;
    }

    public String getField_w8AF1__c() {
        return field_w8AF1__c;
    }

    public void setField_w8AF1__c(String field_w8AF1__c) {
        this.field_w8AF1__c = field_w8AF1__c;
    }

    public String getField_4u1m4__c() {
        return field_4u1m4__c;
    }

    public void setField_4u1m4__c(String field_4u1m4__c) {
        this.field_4u1m4__c = field_4u1m4__c;
    }

    public String getField_ttSo3__c() {
        return field_ttSo3__c;
    }

    public void setField_ttSo3__c(String field_ttSo3__c) {
        this.field_ttSo3__c = field_ttSo3__c;
    }

    public String getField_04619__c() {
        return field_04619__c;
    }

    public void setField_04619__c(String field_04619__c) {
        this.field_04619__c = field_04619__c;
    }

    public String getField_4M62h__c() {
        return field_4M62h__c;
    }

    public void setField_4M62h__c(String field_4M62h__c) {
        this.field_4M62h__c = field_4M62h__c;
    }

    public String getField_0Ar72__c() {
        return field_0Ar72__c;
    }

    public void setField_0Ar72__c(String field_0Ar72__c) {
        this.field_0Ar72__c = field_0Ar72__c;
    }

    public String getField_ns4nt__c() {
        return field_ns4nt__c;
    }

    public void setField_ns4nt__c(String field_ns4nt__c) {
        this.field_ns4nt__c = field_ns4nt__c;
    }

    public String getField_FrJQF__c() {
        return field_FrJQF__c;
    }

    public void setField_FrJQF__c(String field_FrJQF__c) {
        this.field_FrJQF__c = field_FrJQF__c;
    }

    public String getField_4j62N__c() {
        return field_4j62N__c;
    }

    public void setField_4j62N__c(String field_4j62N__c) {
        this.field_4j62N__c = field_4j62N__c;
    }

    public String getField_ms1FQ__c() {
        return field_ms1FQ__c;
    }

    public void setField_ms1FQ__c(String field_ms1FQ__c) {
        this.field_ms1FQ__c = field_ms1FQ__c;
    }

    public String getField_N46xB__c() {
        return field_N46xB__c;
    }

    public void setField_N46xB__c(String field_N46xB__c) {
        this.field_N46xB__c = field_N46xB__c;
    }

    public String getField_321cQ__c() {
        return field_321cQ__c;
    }

    public void setField_321cQ__c(String field_321cQ__c) {
        this.field_321cQ__c = field_321cQ__c;
    }

    public String getField_c5MbM__c() {
        return field_c5MbM__c;
    }

    public void setField_c5MbM__c(String field_c5MbM__c) {
        this.field_c5MbM__c = field_c5MbM__c;
    }

    public String getField_vlgrq__c() {
        return field_vlgrq__c;
    }

    public void setField_vlgrq__c(String field_vlgrq__c) {
        this.field_vlgrq__c = field_vlgrq__c;
    }

    public String getField_g2n94__c() {
        return field_g2n94__c;
    }

    public void setField_g2n94__c(String field_g2n94__c) {
        this.field_g2n94__c = field_g2n94__c;
    }

    public String getField_EHc11__c() {
        return field_EHc11__c;
    }

    public void setField_EHc11__c(String field_EHc11__c) {
        this.field_EHc11__c = field_EHc11__c;
    }

    public String getField_w1seB__c() {
        return field_w1seB__c;
    }

    public void setField_w1seB__c(String field_w1seB__c) {
        this.field_w1seB__c = field_w1seB__c;
    }

    public String getField_ezsuA__c() {
        return field_ezsuA__c;
    }

    public void setField_ezsuA__c(String field_ezsuA__c) {
        this.field_ezsuA__c = field_ezsuA__c;
    }

    public String getField_Jn2iy__c() {
        return field_Jn2iy__c;
    }

    public void setField_Jn2iy__c(String field_Jn2iy__c) {
        this.field_Jn2iy__c = field_Jn2iy__c;
    }

    public String getField_tFd1q__c() {
        return field_tFd1q__c;
    }

    public void setField_tFd1q__c(String field_tFd1q__c) {
        this.field_tFd1q__c = field_tFd1q__c;
    }

    public String getField_h1rW5__c() {
        return field_h1rW5__c;
    }

    public void setField_h1rW5__c(String field_h1rW5__c) {
        this.field_h1rW5__c = field_h1rW5__c;
    }

    public String getField_j2oq2__c() {
        return field_j2oq2__c;
    }

    public void setField_j2oq2__c(String field_j2oq2__c) {
        this.field_j2oq2__c = field_j2oq2__c;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getField_SS32r__c() {
        return field_SS32r__c;
    }

    public void setField_SS32r__c(String field_SS32r__c) {
        this.field_SS32r__c = field_SS32r__c;
    }

    public String getField_kq20e__c() {
        return field_kq20e__c;
    }

    public void setField_kq20e__c(String field_kq20e__c) {
        this.field_kq20e__c = field_kq20e__c;
    }

    public String getField_kRg8S__c() {
        return field_kRg8S__c;
    }

    public void setField_kRg8S__c(String field_kRg8S__c) {
        this.field_kRg8S__c = field_kRg8S__c;
    }

    public String getField_UW798__c() {
        return field_UW798__c;
    }

    public void setField_UW798__c(String field_UW798__c) {
        this.field_UW798__c = field_UW798__c;
    }

    public String getField_f81BW__c() {
        return field_f81BW__c;
    }

    public void setField_f81BW__c(String field_f81BW__c) {
        this.field_f81BW__c = field_f81BW__c;
    }

    public String getAccount_bh() {
        return account_bh;
    }

    public void setAccount_bh(String account_bh) {
        this.account_bh = account_bh;
    }

    public String getXq1() {
        return xq1;
    }

    public void setXq1(String xq1) {
        this.xq1 = xq1;
    }

    public String getXq2() {
        return xq2;
    }

    public void setXq2(String xq2) {
        this.xq2 = xq2;
    }

    public String getField_o3LE5__c() {
        return field_o3LE5__c;
    }

    public void setField_o3LE5__c(String field_o3LE5__c) {
        this.field_o3LE5__c = field_o3LE5__c;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }
}
