package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.jobs.JobContext;
import com.chylee.fxiaoke.xjl.event.data.object.*;
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
    private final FxkHetongService hetongService2;
    private final FxkHetongDjService hetongDjService;
    private final FxkHetongMxService hetongMxService;
    private final FxkProductObjService productObjService;
    private final FxkSPUObjServiceImpl spuObjService;

    protected HetongJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                      FXKSequenceService sequenceService, FxkAccountService accountService,
                                      FxkContactService contactObjService, FxkAccountAddrService accountAddrService,
                                      ErpHetongService erpHetongService, FxkHetongService hetongService2,
                                      FxkHetongDjService hetongDjService, FxkHetongMxService hetongMxService,
                                      FxkProductObjService productObjService, FxkSPUObjServiceImpl spuObjService) {
        super(jobDetailService, reportService, sequenceService, accountService, contactObjService, accountAddrService);
        this.hetongService = erpHetongService;
        this.hetongService2 = hetongService2;
        this.hetongDjService = hetongDjService;
        this.hetongMxService = hetongMxService;
        this.productObjService = productObjService;
        this.spuObjService = spuObjService;
    }

    @Override
    public boolean isSupported(int typeId) {
        return typeId == 2;
    }

    @Override
    protected void writeErrorTo(String dataId, String error) {
        Object_snPZx__c toUpdate = new Object_snPZx__c();
        toUpdate.setDataObjectApiName("object_snPZx__c");
        toUpdate.set_id(dataId);
        toUpdate.setField_WCjgL__c(error);
        try {
            hetongService2.save(toUpdate);
        } catch (Exception e) {
            logger.error(error, e);
        }
    }

   @Override
    protected void writeResultTo(Event event, ResponseEvent resp) throws CrmApiException {
        HetongReqEvent reqEvent = (HetongReqEvent)event;
        AccountRespEvent respEvent = (AccountRespEvent)resp;

        //回写客户编号CRM保存结果
       //回写客户编号CRM保存结果
       if(respEvent.isCreate()) {
           updateAccountKhbh(reqEvent.getAccountObj().get_id(),
                   StringUtils.trim(respEvent.getCopma().getMA001(), false),
                   respEvent.getCopma().getMA003());
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
            hetongService2.save(toUpdate);
        } catch (Exception e) {
            throw new CrmApiException(Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code,
                    String.format("%s[合同][%s-%s][%s]", Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.msg, db, dh, ht.get_id())
            );
        }
    }

    @Override
    protected ResponseEvent saveEvent(Event event) throws ErpDataException, CrmApiException {
        HetongReqEvent reqEvent = (HetongReqEvent)event;

        String htbh = getDanhao("Object_snPZx__c");

        //处理单别单号
        reqEvent.getHt().setField_WCjgL__c(htbh);
        reqEvent.getHtmx().setAB001(reqEvent.getHt().getField_d91gZ__c());
        reqEvent.getHtmx().setAB002(htbh);

        AccountRespEvent respEvent = hetongService.save(reqEvent);
        if (respEvent.isSuccess()) {
            JobContextHolder.getContext().setSerialNo(String.format("%s-%s",
                    reqEvent.getHt().getField_d91gZ__c(), reqEvent.getHt().getField_WCjgL__c()));
            return respEvent;
        }

        throw new ErpDataException("保存到Erp失败 - " + respEvent.getMessage());
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException, ErpDataException {
        Object_snPZx__c ht = hetongService2.loadById(jobDetail.getDataId());

        JobContext context = JobContextHolder.getContext();
        context.setType("合同");
        context.setSerialNo(ht.getName());
        context.setOwner(ht.getOwner());
        context.setMessage("CRM的合同[" + ht.getName() + "]已成功对接到易飞");

        Object_qlu3s__c hetongDj = hetongDjService.getSuccess(ht.get_id());
        if (hetongDj != null && !isDevMode()) {
            String db = hetongDj.getField_161LU__c();
            String dh = hetongDj.getField_55sOU__c();
            Debug("合同已对接过，合同单号：{}-{}", db, dh);
            throw new CrmDataException(Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.code,
                    String.format("%s[%s-%s]", Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.msg, db, dh));
        }

        AccountReqEvent accountReqEvent = getAccountReqEvent(ht.getField_67u8b__c());

        //客户
        AccountObj accountObj = accountReqEvent.getAccountObj();
        if (!StringUtils.isEmpty(accountObj.getField_AlGoN__c()))
            ht.setAA010(accountObj.getField_AlGoN__c());

        //甲方联系人
        if(!StringUtils.isEmpty(ht.getField_Vvn5I__c()))
            ht.setAA009(accountReqEvent.getContactObj().getName());


        List<Object_47F7O__c> htmxs = hetongMxService.listByHtId(ht.get_id());

        HetongReqEvent reqEvent = handleTsxq(ht, htmxs);
        reqEvent.assign(accountReqEvent);

        return reqEvent;
    }

    private HetongReqEvent handleTsxq(Object_snPZx__c ht, List<Object_47F7O__c> htmxs) throws CrmDataException, CrmApiException {
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
