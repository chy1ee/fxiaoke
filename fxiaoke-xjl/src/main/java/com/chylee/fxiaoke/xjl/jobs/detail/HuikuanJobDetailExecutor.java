package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.data.object.OrderPaymentObj;
import com.chylee.fxiaoke.xjl.event.data.object.PaymentObj;
import com.chylee.fxiaoke.xjl.event.data.object.SalesOrderObj;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.HuikuanRespEvent;
import com.chylee.fxiaoke.xjl.service.*;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HuikuanJobDetailExecutor extends AbstractXjlJobDetailExecutor {
    private final ErpHuikuanService huikuanService;
    private final FxkPaymentObjService paymentObjService;
    private final FxkAccountService accountService;
    private final FxkPersonnelObjService personnelObjService;
    private final FxkSalesOrderObjService salesOrderObjService;

    protected HuikuanJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                       FXKSequenceService sequenceService, ErpHuikuanService huikuanService,
                                       FxkPaymentObjService paymentObjService, FxkAccountService accountService,
                                       FxkPersonnelObjService personnelObjService,
                                       FxkSalesOrderObjService salesOrderObjService) {
        super(jobDetailService, reportService, sequenceService);
        this.huikuanService = huikuanService;
        this.paymentObjService = paymentObjService;
        this.accountService = accountService;
        this.personnelObjService = personnelObjService;
        this.salesOrderObjService = salesOrderObjService;
    }


    @Override
    protected void writeErrorTo(String dataId, String error) {
    }

    @Override
    protected void writeResultTo(Event reqEvent, ResponseEvent resp) throws CrmApiException {
    }

    @Override
    protected ResponseEvent saveEvent(Event event) throws ErpDataException, CrmApiException {
        HuikuanRespEvent respEvent = (HuikuanRespEvent)event;
        PaymentObj paymentObj = respEvent.getPaymentObj();
        List<OrderPaymentObj> orderPaymentObjs = respEvent.getOrderPaymentObjs();

        if (orderPaymentObjs == null || orderPaymentObjs.isEmpty())
            throw new ErpDataException("回款明细信息为空");

        paymentObj.setDataObjectApiName("PaymentObj");
        Map<String, List<OrderPaymentObj>> detail = null;

        if (!orderPaymentObjs.isEmpty()) {
            detail = new HashMap<>();
            detail.put("OrderPaymentObj", new ArrayList<>(orderPaymentObjs));
        }

        paymentObjService.save(paymentObj, detail);

        return new ResponseEvent();
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException {
        String[] ddh = jobDetail.getDataId().split("-");
        if (ddh.length != 2)
            throw new ErpDataException("dataId无效[格式错误]");

        HuikuanRespEvent respEvent = huikuanService.loadHuikuan(ddh[0], ddh[1]);
        if (!respEvent.isSuccess())
            throw new ErpDataException(respEvent.getMessage());

        if (respEvent.getPaymentObj() == null)
            throw new ErpDataException("汇款单回款明细不存在" + jobDetail.getId());

        return fillRespEvent(respEvent);
    }

    private Event fillRespEvent(HuikuanRespEvent respEvent) throws CrmApiException, CrmDataException {
        PaymentObj paymentObj = respEvent.getPaymentObj();

        //客户
        paymentObj.setAccount_id(accountService.loadById(paymentObj.getAccount_id()).get_id());

        //业务员
        paymentObj.setOwner(personnelObjService.getOwner(paymentObj.getOwner() == null ? null : paymentObj.getOwner().get(0)));

        //明细订单信息
        if (respEvent.getOrderPaymentObjs() != null) {
            for (OrderPaymentObj obj : respEvent.getOrderPaymentObjs()) {
                //关联订单
                List<SalesOrderObj> salesOrderObjs = salesOrderObjService.listByDbAndDh(obj.getOrder_lb(), obj.getOrder_id());
                if (salesOrderObjs == null || salesOrderObjs.isEmpty())
                    throw new CrmDataException("订单[" + obj.getOrder_lb() + "-" + obj.getOrder_id() + "]不存在");

                obj.setOrder_id(salesOrderObjs.get(0).get_id());
                obj.setOrder_lb(null);
            }
        }

        return respEvent;
    }

    @Override
    public boolean isSupported(int typeId) {
        return typeId == 4;
    }
}
