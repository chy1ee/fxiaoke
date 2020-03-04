package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.event.fxiaoke.data.object.OrderPaymentObj;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.PaymentObj;
import com.chylee.fxiaoke.common.util.DateUtils;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.event.ErpJobRespEvent;
import com.chylee.fxiaoke.xjl.event.HuikuanRespEvent;
import com.chylee.fxiaoke.xjl.mapper.AcrtkMapper;
import com.chylee.fxiaoke.xjl.mapper.AcrtlMapper;
import com.chylee.fxiaoke.xjl.model.Acrtk;
import com.chylee.fxiaoke.xjl.model.Acrtl;
import com.chylee.fxiaoke.xjl.service.ErpHuikuanService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ErpHuikuanServiceImpl implements ErpHuikuanService {
    private final AcrtkMapper acrtkMapper;
    private final AcrtlMapper acrtlMapper;

    public ErpHuikuanServiceImpl(AcrtkMapper acrtkMapper, AcrtlMapper acrtlMapper) {
        this.acrtkMapper = acrtkMapper;
        this.acrtlMapper = acrtlMapper;
    }

    @Override
    public ErpJobRespEvent list(String startTime, String endTime) {
        ErpJobRespEvent respEvent = new ErpJobRespEvent();
        try {
            List<Acrtk> data = acrtkMapper.listSummary(startTime, endTime);
            respEvent.setDataIdList(
                    data.stream().map(acrtk -> acrtk.getTK001() + "-" + acrtk.getTK002())
                            .collect(Collectors.toList()));
        }
        catch(Exception e) {
            respEvent.setError(-1, e.getMessage());
        }

        return respEvent;
    }

    @Override
    public HuikuanRespEvent loadHuikuan(String db, String dh) {
        HuikuanRespEvent respEvent = new HuikuanRespEvent();

        Acrtk acrtk = acrtkMapper.loadByDbAndDh(db, dh);
        if(acrtk != null) {
            respEvent.setPaymentObj(toPaymentOb(acrtk));

            List<Acrtl> acrtls = acrtlMapper.listDetail(db, dh);
            if(acrtls != null && !acrtls.isEmpty()) {
                respEvent.setOrderPaymentObjs(
                        acrtls.stream().map(acrtl -> toOrderPaymentObj(acrtl)).collect(Collectors.toList()));
            }
        }
        else
            respEvent.setError(-1, "订单号不存在");

        return respEvent;
    }

    private OrderPaymentObj toOrderPaymentObj(Acrtl acrtl) {
        OrderPaymentObj obj = new OrderPaymentObj();

        obj.setPayment_amount(acrtl.getTL019());
        obj.setOrder_id(acrtl.getTL006());
        obj.setOrder_lb(acrtl.getTL005());
        obj.setRemark(acrtl.getTL023());

        return obj;
    }

    private PaymentObj toPaymentOb(Acrtk acrtk) {
        PaymentObj obj = new PaymentObj();

        obj.setAccount_id(acrtk.getTK004());    //客户
        obj.setPayment_time(DateUtils.toTimestamp(acrtk.getTK003()));  //回款日期
        obj.setRemark(acrtk.getTK009());    //备注
        obj.setField_MDApg__c(acrtk.getTK001());    //单别
        obj.setField_Ueasn__c(acrtk.getTK002());    //单号
        if(StringUtils.isEmpty(acrtk.getTK005()))
            obj.addOwner(acrtk.getTK005());

        return obj;
    }
}
