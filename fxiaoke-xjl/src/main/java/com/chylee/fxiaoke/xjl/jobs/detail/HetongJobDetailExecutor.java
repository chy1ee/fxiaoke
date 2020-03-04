package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.*;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.AccountReqEvent;
import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.event.HetongReqEvent;
import com.chylee.fxiaoke.common.jobs.JobContextHolder;
import com.chylee.fxiaoke.xjl.service.*;
import com.chylee.fxiaoke.xjl.service.impl.FxkSPUObjServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HetongJobDetailExecutor extends AbstractAccountJobDetailExecutor {
    private final ErpHetongService hetongService;
    private final FxkObjectSnPZxService objectSnPZxService;
    private final FxkObject47F7OService object47F7OService;
    private final FxkProductObjService productObjService;
    private final FxkSPUObjServiceImpl spuObjService;

    protected HetongJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                      FXKSequenceService sequenceService, FxkAccountService accountService,
                                      FxkContactService contactObjService, FxkAccountAddrService accountAddrService,
                                      ErpHetongService erpHetongService, FxkObjectSnPZxService fxkObjectSnPZxService,
                                      FxkObject47F7OService object47F7OService, FxkProductObjService productObjService,
                                      FxkSPUObjServiceImpl spuObjService) {
        super(jobDetailService, reportService, sequenceService, accountService, contactObjService, accountAddrService);
        this.hetongService = erpHetongService;
        this.objectSnPZxService = fxkObjectSnPZxService;
        this.object47F7OService = object47F7OService;
        this.productObjService = productObjService;
        this.spuObjService = spuObjService;
    }

    @Override
    public boolean isSupported(int typeId) {
        return typeId == 2;
    }

    @Override
    protected void saveEvent(Event event) throws ErpDataException, CrmApiException {
        HetongReqEvent reqEvent = (HetongReqEvent)event;
        String khbh = saveToErp(reqEvent);
        if (isDevMode())
            Debug("开发模式不回写CRM");
        else
            saveToCrm(khbh, reqEvent);

        JobContextHolder.setSerialNo(String.format("%s-%s",
                reqEvent.getHt().getField_d91gZ__c(), reqEvent.getHt().getField_WCjgL__c()));
        JobContextHolder.setSuccess();
    }

    private void saveToCrm(String khbh, HetongReqEvent reqEvent ) throws CrmApiException {
        //回写客户编号CRM保存结果
        if (khbh != null) {
            String accountId = reqEvent.getAccountObj().get_id();
            Debug("开始回写客户编号：{}-{}", accountId, khbh);
            updateAccountKhbh(accountId, khbh);
        }

        //回写单号单别
        Object_snPZx__c ht = reqEvent.getHt();
        String db = ht.getField_d91gZ__c();
        String dh = ht.getField_WCjgL__c();

        Object_snPZx__c toUpdate = new Object_snPZx__c();
        toUpdate.setDataObjectApiName("object_snPZx__c");
        toUpdate.set_id(ht.get_id());
        toUpdate.setField_d91gZ__c(db);
        toUpdate.setField_WCjgL__c(dh);
        try {
            objectSnPZxService.save(toUpdate);
        } catch (Exception e) {
            throw new CrmApiException(String.format("%s[%s-%s]", e.getMessage(), db, dh));
        }
    }

    private String saveToErp(HetongReqEvent reqEvent) throws ErpDataException {
        String htbh = getDanhao("Object_snPZx__c");

        //处理单别单号
        reqEvent.getHt().setField_WCjgL__c(htbh);
        reqEvent.getHtmx().setAB001(reqEvent.getHt().getField_d91gZ__c());
        reqEvent.getHtmx().setAB002(htbh);

        AccountRespEvent respEvent = hetongService.save(reqEvent);
        if (respEvent.isSuccess())
            return respEvent.getKhbh();

        throw new ErpDataException("保存到Erp失败 - " + respEvent.getMessage());
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException {
        Object_snPZx__c ht = objectSnPZxService.loadById(jobDetail.getDataId());
        JobContextHolder.setType("合同");
        JobContextHolder.setSerialNo(ht.getName());
        JobContextHolder.setOwner(ht.getOwner());

        Debug("***owner = {}", ht.getOwner());

        if (!StringUtils.isEmpty(ht.getField_WCjgL__c()) && !isDevMode()) {
            Debug("合同已对接过，合同单号：{}", ht.getField_WCjgL__c());
            throw new CrmDataException(Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.code,
                    Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.msg);
        }

        AccountReqEvent accountReqEvent = getAccountReqEvent(ht.getField_67u8b__c());

        //客户
        AccountObj accountObj = accountReqEvent.getAccountObj();
        if (!StringUtils.isEmpty(accountObj.getField_AlGoN__c()))
            ht.setAA010(accountObj.getField_AlGoN__c());

        //甲方联系人
        if(!StringUtils.isEmpty(ht.getField_Vvn5I__c()))
            ht.setAA009(accountReqEvent.getContactObj().getName());


        List<Object_47F7O__c> htmxs = object47F7OService.listByHtId(ht.get_id());

        HetongReqEvent reqEvent = handleTsxq(ht, htmxs);
        reqEvent.assign(accountReqEvent);

        return reqEvent;
    }

    private HetongReqEvent handleTsxq(Object_snPZx__c ht, List<Object_47F7O__c> htmxs) throws CrmDataException, CrmApiException {
        int index;
        StringBuilder tsxq = new StringBuilder();

        if (!StringUtils.isEmpty(ht.getField_gri58__c())) {
            String _xq = ht.getField_gri58__c();
            tsxq.append(_xq);
            if (!"；".equals(_xq.substring(_xq.length() -1)))
                tsxq.append("；");
        }

        Object_47F7O__c htmx = null;

        for (Object_47F7O__c obj : htmxs) {
            String dataId = obj.getField_siPlm__c();
            if (dataId == null)
                throw new CrmDataException("明细产品ID不能为空");

            SPUObj spuObj = spuObjService.loadById(dataId);
            obj.setAB005(spuObj.getName());

            if (obj.getField_Oocl6__c().equals(ht.getField_P9um1__c())) {
                htmx = obj;
            }
            else {
                //改用商品名称
                //ProductObj productObj = loadDataObj("ProductObj", dataId, false, ProductObj.class);
                //String xq = productObj.getName();
                tsxq.append(spuObj.getName()).append("；");
            }
        }

        if(htmx == null)
            throw new CrmDataException("找不型号为“"+ht.getField_P9um1__c()+"”的明细项");

        String tsxqS = tsxq.toString();
        int tsxqL = StringUtils.gbkLength(tsxqS);
        if (tsxqL < 0)
            throw new CrmDataException("计算特殊需求长度失败");

        if (tsxqL > 0) {
            ht.setUDF001(StringUtils.gbkLeft(tsxqS, 249));
            if (tsxqL > 249) {
                String tsxqS2 = tsxqS.substring(ht.getUDF001().length());
                if (tsxqL - 249 > 10)
                    ht.setAA013(StringUtils.gbkLeft(tsxqS2, 7) + "...");
                else
                    ht.setAA013(StringUtils.gbkLeft(tsxqS2, 10));
            }

        }

        //产品
        ProductObj productObj = productObjService.loadById(htmx.getField_6210o__c());
        htmx.setAB004(productObj.getProduct_code());
        htmx.setAB006(productObj.getProduct_spec());
        htmx.setAB007(productObj.getUnit());

        HetongReqEvent reqEvent = new HetongReqEvent();
        reqEvent.setHt(ht);
        reqEvent.setHtmx(htmx);

        return reqEvent;
    }
}
