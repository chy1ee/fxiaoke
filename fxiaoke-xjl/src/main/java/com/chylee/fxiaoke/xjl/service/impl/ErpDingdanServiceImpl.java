package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.xjl.event.DingdanRespEvent;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderObj;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderProductObj;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.mapper.CoptcMapper;
import com.chylee.fxiaoke.xjl.mapper.CoptdMapper;
import com.chylee.fxiaoke.xjl.model.Coptc;
import com.chylee.fxiaoke.xjl.model.Coptd;
import com.chylee.fxiaoke.xjl.service.ErpDingdanService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErpDingdanServiceImpl implements ErpDingdanService {
    private final CoptcMapper coptcMapper;
    private final CoptdMapper coptdMapper;

    public ErpDingdanServiceImpl(CoptcMapper coptcMapper, CoptdMapper coptdMapper) {
        this.coptcMapper = coptcMapper;
        this.coptdMapper = coptdMapper;
    }

    @Override
    public ErpJobRespEvent list(String startTime, String endTime) {
        ErpJobRespEvent respEvent = new ErpJobRespEvent();
        try {
            List<Coptc> data = coptcMapper.list(startTime, endTime);
            respEvent.setDataIdList(
                    data.stream().map(coptc -> coptc.getTC001() + "-" + coptc.getTC002())
                            .collect(Collectors.toList()));
        }
        catch(Exception e) {
            respEvent.setError(-1, e.getMessage());
        }

        return respEvent;
    }

    @Override
    public DingdanRespEvent loadDingdan(String db, String dh) {
        DingdanRespEvent respEvent = new DingdanRespEvent();

        Coptc coptc = coptcMapper.loadByDbAndDh(db, dh);
        if(coptc != null) {
            SalesOrderObj salesOrderObj = toSalesOrderObj(coptc);

            List<Coptd> coptds = coptdMapper.listByDbAndDh(db, dh);
            if(coptds != null && !coptds.isEmpty()) {
                respEvent.setSalesOrderProductObjs(
                        coptds.stream().map(coptd -> toSalesOrderProductObj(coptd, salesOrderObj)).collect(Collectors.toList()));
            }

            respEvent.setSalesOrderObj(salesOrderObj);
        }
        else
            respEvent.setError(-1, "订单号不存在");

        return respEvent;
    }

    private SalesOrderObj toSalesOrderObj(Coptc coptc) {
        SalesOrderObj salesOrderObj = new SalesOrderObj();

        salesOrderObj.setDiscount("100.00");        //整单折扣(%)
        if (!StringUtils.isEmpty(coptc.getTC003())) {
            Date orderTime = DateUtils.fromString(coptc.getTC003().trim());
            if (orderTime != null)
                salesOrderObj.setOrder_time(String.valueOf(orderTime.getTime()));      //下单日期
        }
        salesOrderObj.setShip_to_id(StringUtils.trim(coptc.getTC018(), false));      //收货人   关联联系人ID
        salesOrderObj.setShip_to_add(StringUtils.trim(coptc.getTC010(), false));     //收货地址
        salesOrderObj.setShip_to_tel(StringUtils.phone(coptc.getTC074()));     //收货人电话
        salesOrderObj.setShip_to_fax(StringUtils.phone(coptc.getTC075()));     //传真
        salesOrderObj.setOrder_amount(coptc.getTCI03());   //销售订单金额(元)
        salesOrderObj.setQuote_id(null);        //报价单
        if (!StringUtils.isEmpty(coptc.getTCI01())) {
            Date deliveryTime = DateUtils.fromString(coptc.getTCI01().trim());
            if (deliveryTime != null)
                salesOrderObj.setDelivery_date(String.valueOf(deliveryTime.getTime()));      //交货日期();
        }
        salesOrderObj.setRemark(StringUtils.trim(coptc.getTC015(), false));          //备注
        salesOrderObj.setAccount_id(StringUtils.trim(coptc.getTC004(), false));      //客户名称 客户编号需转换 关联客户ID
        salesOrderObj.setField_4xWXT__c(StringUtils.trim(coptc.getTC002(), false));  //单号
        salesOrderObj.setField_4m6gr__c(StringUtils.trim(coptc.getTC001(), false));  //单别
        String owner = StringUtils.trim(coptc.getTC006(), false);
        if (owner != null)
            salesOrderObj.addOwner(owner);           //业务员
        return salesOrderObj;
    }

    private SalesOrderProductObj toSalesOrderProductObj(Coptd coptd, SalesOrderObj salesOrderObj) {
        SalesOrderProductObj salesOrderProductObj = new SalesOrderProductObj();

        salesOrderProductObj.setDiscount(
                coptd.getTD026().multiply(new BigDecimal(100)).setScale(2,
                        BigDecimal.ROUND_HALF_UP).toString());    //折扣
        salesOrderProductObj.setRemark(StringUtils.trim(coptd.getTD020(), false));  //备注
        salesOrderProductObj.setProduct_price(coptd.getTD012());   //金额
        String product_id = StringUtils.trim(coptd.getTD014(), false);
        if (product_id == null)
            product_id = StringUtils.trim(coptd.getTD004(), false);
        salesOrderProductObj.setProduct_id(product_id);  //产品名称  关联产品ID
        salesOrderProductObj.setProduct_name(StringUtils.trim(coptd.getTD005(), false)); //产品名称
        salesOrderProductObj.setQuantity(coptd.getTD008());    //数量
        salesOrderProductObj.setUnit(StringUtils.trim(coptd.getTD010(), false));    //单位
        salesOrderProductObj.setSubtotal(coptd.getTD012());    //小计
        salesOrderProductObj.setSales_price(coptd.getTD011()); //单价
        salesOrderProductObj.setOrder_id(null);    //订单ID 关联订单ID

        if (!StringUtils.isEmpty(coptd.getTD017()) && !StringUtils.isEmpty(coptd.getTD018()))
            salesOrderObj.setQuote_id(String.format("%s-%s", coptd.getTD017().trim(), coptd.getTD018().trim()));

        return salesOrderProductObj;
    }
}
