package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.jobs.JobContext;
import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.xjl.event.data.object.Object_1sCfv__c;
import com.chylee.fxiaoke.xjl.event.data.object.QuoteLinesObj;
import com.chylee.fxiaoke.xjl.event.data.object.QuoteObj;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.AccountReqEvent;
import com.chylee.fxiaoke.xjl.event.AccountRespEvent;
import com.chylee.fxiaoke.xjl.event.BaojiadanReqEvent;
import com.chylee.fxiaoke.common.jobs.JobContextHolder;
import com.chylee.fxiaoke.xjl.service.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaojiadanJobDetailExecutor extends AbstractAccountJobDetailExecutor {
    private final ErpBaojiadanService baojiadanService;
    private final FxkBaojiadanDjService baojiadanDjService;
    private final FxkQuoteObjService quoteObjService;
    private final FxkQuoteLinesObjService quoteLinesObjService;

    protected BaojiadanJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                         FXKSequenceService sequenceService, FxkAccountService accountService,
                                         FxkContactService contactObjService, FxkAccountAddrService accountAddrService,
                                         ErpBaojiadanService baojiadanService, FxkBaojiadanDjService baojiadanDjService,
                                         FxkQuoteObjService quoteObjService, FxkQuoteLinesObjService quoteLinesObjService) {
        super(jobDetailService, reportService, sequenceService, accountService, contactObjService, accountAddrService);
        this.baojiadanService = baojiadanService;
        this.baojiadanDjService = baojiadanDjService;
        this.quoteObjService = quoteObjService;
        this.quoteLinesObjService = quoteLinesObjService;
    }

    @Override
    public boolean isSupported(int typeId) {
        return typeId == 1;
    }

    @Override
    protected void writeErrorTo(String dataId, String error) {
        try {
            quoteObjService.update(dataId, null, error);
        } catch (CrmApiException e) {
            logger.error(error, e);
        }
    }

    @Override
    protected void writeResultTo(Event event, ResponseEvent resp) throws CrmApiException {
        BaojiadanReqEvent reqEvent = (BaojiadanReqEvent)event;
        AccountRespEvent respEvent = (AccountRespEvent)resp;

        //回写客户编号CRM保存结果
        if(respEvent.isCreate()) {
            updateAccountKhbh(reqEvent.getAccountObj().get_id(),
                    StringUtils.trim(respEvent.getCopma().getMA001(), false),
                    respEvent.getCopma().getMA003());
        }

        //回写单号单别
        QuoteObj quoteObj = reqEvent.getQuoteObj();
        String db = quoteObj.getField_kq20e__c();
        String dh = quoteObj.getField_SS32r__c();

        try {
            quoteObjService.update(quoteObj.get_id(), db, dh);
        } catch (Exception e) {
            throw new CrmApiException(Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code,
                    String.format("%s[合同][%s-%s][%s]", Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.msg, db, dh, quoteObj.get_id())
            );
        }
        /*
        try {
            baojiadanDjService.save(quoteObj.get_id(), db, dh, null, true);
        } catch (CrmApiException e) {
            String error = String.format("%s[报价单][%s-%s][%s]",
                    Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.msg, db, dh, quoteObj.get_id());
            logger.error(error, e);
            throw new CrmApiException(Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code, error);
        }
        */
    }

    @Override
    protected ResponseEvent saveEvent(Event event) throws ErpDataException, CrmApiException {
        BaojiadanReqEvent reqEvent = (BaojiadanReqEvent)event;

        String dh = getDanhao("QuoteObj");
        reqEvent.getQuoteObj().setField_kq20e__c("2101");
        reqEvent.getQuoteObj().setField_SS32r__c(dh);
        reqEvent.getQuoteLinesObj().setBjddb("2101");
        reqEvent.getQuoteLinesObj().setBjddh(dh);

        AccountRespEvent respEvent = baojiadanService.save(reqEvent);
        if (respEvent.isSuccess()) {
            JobContextHolder.getContext().setSerialNo(String.format("%s-%s",
                    reqEvent.getQuoteObj().getField_kq20e__c(), reqEvent.getQuoteObj().getField_SS32r__c()));
            return respEvent;
        }

        throw new ErpDataException("保存到Erp失败 - " + respEvent.getMessage());
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException {
        Debug("获取报价单信息[DataId={}]", jobDetail.getDataId());
        QuoteObj quoteObj = quoteObjService.loadById(jobDetail.getDataId());
        JobContext context = JobContextHolder.getContext();
        context.setType("报价单");
        context.setSerialNo(quoteObj.getName());
        context.setOwner(quoteObj.getOwner());
        context.setMessage("CRM的报价单[" + quoteObj.getName() + "]已成功对接到易飞");

        Object_1sCfv__c baojiadanDj = baojiadanDjService.getSuccess(quoteObj.get_id());
        if (baojiadanDj != null && !isDevMode()) {
            String db = baojiadanDj.getField_GI813__c();
            String dh = baojiadanDj.getField_88n41__c();
            Debug("报价单已对接过[{}-{}]", db, dh);
            throw new CrmDataException(Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.code,
                    String.format("%s[%s-%s]", Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.msg, db, dh));
        }

        //含税说明
        if ("option1".equals(quoteObj.getField_f81BW__c()))
            quoteObj.setField_f81BW__c("1");
        else if ("D6w3kgc3y".equals(quoteObj.getField_f81BW__c()))
            quoteObj.setField_f81BW__c("2");

        Debug("获取客户信息[AccountId={}]", quoteObj.getAccount_id());
        AccountReqEvent accountReqEvent = getAccountReqEvent(quoteObj.getAccount_id());
        AccountObj accountObj = accountReqEvent.getAccountObj();
        if (!StringUtils.isEmpty(accountObj.getField_AlGoN__c()))
            quoteObj.setAccount_bh(accountObj.getField_AlGoN__c());
        quoteObj.setAccount_name(accountObj.getName());

        Debug("客户编码[{}]", accountObj.getField_AlGoN__c());

        Debug("开始从CRM获取报价单明细数据");
        List<QuoteLinesObj> quoteLinesObjs = quoteLinesObjService.listByQuoteId(quoteObj.get_id());

        BaojiadanReqEvent baojiadanReqEvent = this.handleTsxq(quoteObj, quoteLinesObjs);
        baojiadanReqEvent.assign(accountReqEvent);

        return baojiadanReqEvent;
    }

    private BaojiadanReqEvent handleTsxq(QuoteObj quoteObj, List<QuoteLinesObj> quoteLinesObjs) throws CrmDataException {
        StringBuilder tsxq = new StringBuilder();

        if (!StringUtils.isEmpty(quoteObj.getField_j2oq2__c())) {
            String _xq = quoteObj.getField_j2oq2__c();
            Debug("特殊需求 - {}", _xq);
            tsxq.append(_xq);
            int l = _xq.length();
            if (!"；".equals(_xq.substring(l-1)))
                tsxq.append("；");
        }

        QuoteLinesObj quoteLinesObj = null;

        for (QuoteLinesObj obj : quoteLinesObjs) {
            if (quoteObj.getField_tFd1q__c().equals(obj.getField_A5P2v__c()))
                quoteLinesObj = obj;
            else {
                String dataId = obj.getProduct_id();
                if (dataId == null)
                    throw new CrmDataException("明细产品ID不能为空");

                //不使用产品名称，使用商品名称
                //ProductObj productObj = loadDataObj("ProductObj", dataId, false, ProductObj.class);
                //String xq = productObj.getName();
                tsxq.append(obj.getField_6XaI6__c()).append("；");
            }
        }

        if(quoteLinesObj == null)
            throw new CrmDataException("找不型号为“"+quoteObj.getField_tFd1q__c()+"”的明细项");

        String tsxqS = tsxq.toString();
        int tsxqL = StringUtils.gbkLength(tsxqS);
        if (tsxqL < 0)
            throw new CrmDataException("计算特殊需求长度失败");

        if (tsxqL > 0) {
            quoteObj.setXq1(StringUtils.gbkLeft(tsxqS, 249));
            if (tsxqL > 249) {
                String tsxqS2 = tsxqS.substring(quoteObj.getXq1().length());
                if (tsxqL - 249 > 10)
                    quoteObj.setXq2(StringUtils.gbkLeft(tsxqS2, 7) + "...");
                else
                    quoteObj.setXq2(StringUtils.gbkLeft(tsxqS2, 10));
            }
        }

        BaojiadanReqEvent reqEvent = new BaojiadanReqEvent();
        reqEvent.setQuoteObj(quoteObj);
        reqEvent.setQuoteLinesObj(quoteLinesObj);

        return reqEvent;
    }

}
