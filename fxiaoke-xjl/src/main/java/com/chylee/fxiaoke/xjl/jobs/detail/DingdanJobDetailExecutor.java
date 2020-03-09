package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.data.object.*;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.DingdanRespEvent;
import com.chylee.fxiaoke.common.jobs.JobContextHolder;
import com.chylee.fxiaoke.xjl.service.*;
import com.chylee.fxiaoke.xjl.service.impl.FxkProductObjServiceImpl;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DingdanJobDetailExecutor extends AbstractXjlJobDetailExecutor {

    private final ErpDingdanService dingdanService;
    private final FxkPersonnelObjService personnelObjService;
    private final FxkAccountService accountService;
    private final FxkSalesOrderObjService salesOrderObjService;
    private final FxkContactService contactService;
    private final FxkProductObjServiceImpl productObjService;
    private final FxkPriceBookProductObjService priceBookProductObjService;

    protected DingdanJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                       FXKSequenceService sequenceService, ErpDingdanService dingdanService,
                                       FxkPersonnelObjService personnelObjService, FxkAccountService accountService,
                                       FxkSalesOrderObjService salesOrderObjService, FxkContactService contactService,
                                       FxkProductObjServiceImpl productObjService, FxkPriceBookProductObjService priceBookProductObjService) {
        super(jobDetailService, reportService, sequenceService);
        this.dingdanService = dingdanService;
        this.personnelObjService = personnelObjService;
        this.accountService = accountService;
        this.salesOrderObjService = salesOrderObjService;
        this.contactService = contactService;
        this.productObjService = productObjService;
        this.priceBookProductObjService = priceBookProductObjService;
    }

    @Override
    protected void writeErrorTo(String dataId, String error) {
    }

    @Override
    protected void writeResultTo(Event reqEvent, ResponseEvent resp) throws CrmApiException {
        DingdanRespEvent respEvent = (DingdanRespEvent)reqEvent;

        SalesOrderObj salesOrderObj = respEvent.getSalesOrderObj();
        salesOrderObj.setDataObjectApiName("SalesOrderObj");
        salesOrderObj.setPrice_book_id(FxkPriceBookProductObjService.PRICEBOOK_ID);
        salesOrderObj.setShip_to_fax(null);

        Map<String, List<SalesOrderProductObj>> detail = new HashMap<>();
        List<SalesOrderProductObj> list = new ArrayList<>();
        for (SalesOrderProductObj salesOrderProductObj : respEvent.getSalesOrderProductObjs()) {
            salesOrderProductObj.setProduct_name(null);
            list.add(salesOrderProductObj);
        }
        detail.put("SalesOrderProductObj", list);

        salesOrderObjService.save(salesOrderObj, detail);
    }

    @Override
    protected ResponseEvent saveEvent(Event event) throws CrmApiException, CrmDataException {
        DingdanRespEvent respEvent = (DingdanRespEvent)event;
        String db = respEvent.getSalesOrderObj().getField_4m6gr__c();
        String dh = respEvent.getSalesOrderObj().getField_4xWXT__c();
        List<SalesOrderObj> salesOrderObjs = salesOrderObjService.listByDbAndDh(db, dh);
        if (salesOrderObjs != null && !salesOrderObjs.isEmpty())
            throw new CrmDataException(MessageFormat.format("销售订单已存在{0}-{1}", db, dh));
        return null;
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws  CrmApiException, CrmDataException, ErpDataException {
        String[] ddh = jobDetail.getDataId().split("-");
        if (ddh.length != 2)
            throw new ErpDataException("dataId无效[格式错误]");

        DingdanRespEvent respEvent = dingdanService.loadDingdan(ddh[0], ddh[1]);
        if (!respEvent.isSuccess())
            throw new ErpDataException(respEvent.getMessage());

        if (respEvent.getSalesOrderObj() == null || respEvent.getSalesOrderProductObjs() == null)
            throw new ErpDataException("订单或订单明细为空：" + jobDetail.getId());

        return fillRespEvent(respEvent);
    }

    private DingdanRespEvent fillRespEvent(DingdanRespEvent respEvent) throws CrmApiException, CrmDataException {
        SalesOrderObj salesOrderObj = respEvent.getSalesOrderObj();
        List<String> owner = personnelObjService.getOwner(salesOrderObj.getOwner() == null ? null : salesOrderObj.getOwner().get(0));
        salesOrderObj.setOwner(owner);
        JobContextHolder.getContext().setType("订单");
        JobContextHolder.getContext().setSerialNo(String.format("%s-%s", salesOrderObj.getField_4m6gr__c(), salesOrderObj.getField_4xWXT__c()));
        JobContextHolder.getContext().setOwner(owner);

        //客户
        AccountObj accountObj = accountService.loadByKhbh(respEvent.getSalesOrderObj().getAccount_id());
        salesOrderObj.setAccount_id(accountObj.get_id());

        //联系人
        String shipToId = StringUtils.trim(salesOrderObj.getShip_to_id(), false);
        Debug("联系人{}", shipToId);
        if (shipToId != null) {
            if ("".equals(shipToId))
                salesOrderObj.setShip_to_id(null);
            else {
                String accountId = salesOrderObj.getAccount_id();
                String tel1 = StringUtils.trim(salesOrderObj.getShip_to_tel(), false);
                String tel2 = StringUtils.trim(salesOrderObj.getShip_to_fax(), false);

                List<ContactObj> contactObjs = contactService.listByAccounrIdAndName(accountId, shipToId);
                Debug("联系人数量{}", contactObjs.size());
                if (contactObjs == null || contactObjs.isEmpty()) {
                    ContactObj contactObj = new ContactObj();
                    contactObj.setDataObjectApiName("ContactObj");
                    contactObj.setAccount_id(accountId);
                    contactObj.setName(shipToId);
                    contactObj.setTel1(tel1);
                    contactObj.setTel3(tel2);
                    contactObj.setOwner(accountObj.getOwner());
                    contactService.save(contactObj);
                }
                else {
                    ContactObj contactObjToUse = contactObjs.get(0);
                    Debug(contactObjToUse.toString());
                    salesOrderObj.setShip_to_id(contactObjToUse.get_id());
                }
            }
        }

        //报价单
        if (salesOrderObj.getQuote_id() != null) {
            salesOrderObj.setQuote_id(null);   //暂时这样，CRM无法获取报价单ID
        }

        //订单明细
        for(SalesOrderProductObj salesOrderProductObj : respEvent.getSalesOrderProductObjs()) {
            String productCode = salesOrderProductObj.getProduct_id().trim();
            salesOrderProductObj.setPrice_book_product_id(priceBookProductObjService.listByProductCode(productCode).get(0).get_id());
            salesOrderProductObj.setProduct_id(productObjService.listByProductCode(productCode).get(0).get_id());
        }

        return respEvent;
    }

    @Override
    public boolean isSupported(int typeId) {
        return typeId == 3;
    }
}
