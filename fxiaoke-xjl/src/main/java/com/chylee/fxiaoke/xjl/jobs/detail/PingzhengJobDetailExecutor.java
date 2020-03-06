package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.xjl.event.data.object.Object_79pYP__c;
import com.chylee.fxiaoke.xjl.event.data.object.Object_h18X2__c;
import com.chylee.fxiaoke.xjl.event.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.PingzhengReqEvent;
import com.chylee.fxiaoke.common.jobs.JobContextHolder;
import com.chylee.fxiaoke.xjl.service.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingzhengJobDetailExecutor extends AbstractXjlJobDetailExecutor {
    private final ErpPingzhengService pingzhengService;
    private final FxkAccountService accountService;
    private final FxkPersonnelObjService personnelObjService;
    private final FxkBaoxiaoService baoxiaoService;
    private final FxkBaoxiaoMxService baoxiaoMxService;
    private final FxkBaoxiaoDjService baoxiaoDjService;

    protected PingzhengJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                         FXKSequenceService sequenceService, ErpPingzhengService pingzhengService,
                                         FxkAccountService accountService, FxkPersonnelObjService personnelObjService,
                                         FxkBaoxiaoService baoxiaoService, FxkBaoxiaoMxService baoxiaoMxService,
                                         FxkBaoxiaoDjService baoxiaoDjService) {
        super(jobDetailService, reportService, sequenceService);
        this.pingzhengService = pingzhengService;
        this.accountService = accountService;
        this.personnelObjService = personnelObjService;
        this.baoxiaoService = baoxiaoService;
        this.baoxiaoMxService = baoxiaoMxService;
        this.baoxiaoDjService = baoxiaoDjService;
    }

    @Override
    protected void writeErrorTo(String dataId, String error) {
        try {
            baoxiaoDjService.save(dataId, null, null, error, false);
        } catch (Exception e) {
            logger.error(error, e);
        }
    }

    @Override
    protected void writeResultTo(Event event, ResponseEvent resp) throws CrmApiException {
        PingzhengReqEvent reqEvent = (PingzhengReqEvent)event;

        Object_okom1__c pz = reqEvent.getPz();
        String dh = pz.getField_EHyt1__c();
        String db = pz.getField_8GijD__c();
        try {
            baoxiaoDjService.save(pz.get_id(), db, dh, null, true);
        } catch (Exception e) {
            String error = String.format("%s[报价单][%s-%s][%s]",
                    Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.msg, db, dh, pz.get_id());
            logger.error(error, e);
            throw new CrmApiException(Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code, error);
        }
    }

    @Override
    protected ResponseEvent saveEvent(Event event) throws ErpDataException {
        PingzhengReqEvent reqEvent = (PingzhengReqEvent)event;
        Object_okom1__c pz = reqEvent.getPz();

        String pzbh = getDanhao("Object_okom1__c");
        pz.setField_EHyt1__c("9101");
        pz.setField_8GijD__c(pzbh);

        ResponseEvent respEvent = pingzhengService.save(reqEvent);
        if (!respEvent.isSuccess())
            throw new ErpDataException("保存一般凭证失败 - " + respEvent.getMessage());

        return respEvent;
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException {
        Object_okom1__c pz = baoxiaoService.loadById(jobDetail.getDataId());
        JobContextHolder.getContext().setType("报销单");
        JobContextHolder.getContext().setSerialNo(pz.getName());
        JobContextHolder.getContext().setOwner(pz.getOwner());

        Object_h18X2__c baoxiaoDj = baoxiaoDjService.getSuccess(pz.get_id());
        if (baoxiaoDj != null && !isDevMode()) {
            String db = baoxiaoDj.getField_1r0bN__c();
            String dh = baoxiaoDj.getField_g6j6m__c();
            Debug("报销单已对接过，报销单号：{}-{}", db, dh);
            throw new CrmDataException(Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.code,
                    String.format("%s[%s-%s]", Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.msg, db, dh));
        }

        //客户
        if(!StringUtils.isEmpty(pz.getField_246FG__c()))
            pz.setField_246FG__c(accountService.loadById(pz.getField_246FG__c()).getField_AlGoN__c());

        //报销人
        pz.setField_N7e3j__c(personnelObjService.getNumberByOpenId(pz.getOwner().get(0)));

        List<Object_79pYP__c> pzmxs = baoxiaoMxService.listByPzId(pz.get_id());

        PingzhengReqEvent reqEvent = new PingzhengReqEvent();
        reqEvent.setPz(pz);
        reqEvent.setPzmx(pzmxs);

        return reqEvent;
    }

    @Override
    public boolean isSupported(int typeId) {
        return false;
    }
}
