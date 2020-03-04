package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.event.Event;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_79pYP__c;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.Object_okom1__c;
import com.chylee.fxiaoke.common.exception.*;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.PingzhengReqEvent;
import com.chylee.fxiaoke.xjl.jobs.JobContextHolder;
import com.chylee.fxiaoke.xjl.service.ErpPingzhengService;
import com.chylee.fxiaoke.xjl.service.FxkAccountService;
import com.chylee.fxiaoke.xjl.service.FxkObject79pYPService;
import com.chylee.fxiaoke.xjl.service.FxkPersonnelObjService;
import com.chylee.fxiaoke.xjl.service.impl.FxkObjectOkom1ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingzhengJobDetailExecutor extends AbstractXjlJobDetailExecutor {
    private final ErpPingzhengService pingzhengService;
    private final FxkAccountService accountService;
    private final FxkPersonnelObjService personnelObjService;
    private final FxkObjectOkom1ServiceImpl objectOkom1Service;
    private final FxkObject79pYPService object79pYPService;

    protected PingzhengJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                         FXKSequenceService sequenceService, ErpPingzhengService pingzhengService, FxkAccountService accountService, FxkPersonnelObjService personnelObjService, FxkObjectOkom1ServiceImpl objectOkom1Service, FxkObject79pYPService object79pYPService) {
        super(jobDetailService, reportService, sequenceService);
        this.pingzhengService = pingzhengService;
        this.accountService = accountService;
        this.personnelObjService = personnelObjService;
        this.objectOkom1Service = objectOkom1Service;
        this.object79pYPService = object79pYPService;
    }

    @Override
    protected void saveEvent(Event event) throws ErpDataException, CrmApiException {
        PingzhengReqEvent reqEvent = (PingzhengReqEvent)event;
        saveToErp(reqEvent);
        if (isDevMode())
            Debug("开发模式不回写CRM");
        else
            saveToCrm(reqEvent);

        JobContextHolder.setSerialNo(String.format("%s-%s", reqEvent.getPz().getField_8GijD__c(), reqEvent.getPz().getField_EHyt1__c()));
        JobContextHolder.setSuccess();
    }

    private void saveToCrm(PingzhengReqEvent reqEvent) throws CrmApiException {
        Object_okom1__c pz = reqEvent.getPz();
        String dh = pz.getField_EHyt1__c();
        String db = pz.getField_8GijD__c();
        Object_okom1__c toUpdate = new Object_okom1__c();
        toUpdate.setDataObjectApiName("object_okom1__c");
        toUpdate.set_id(pz.get_id());
        toUpdate.setField_EHyt1__c(dh);
        toUpdate.setField_8GijD__c(db);
        try {
            objectOkom1Service.save(pz);
        } catch (Exception e) {
            throw new CrmApiException(String.format("%s[%s-%s]", e.getMessage(), db, dh));
        }
    }

    private void saveToErp(PingzhengReqEvent reqEvent) throws ErpDataException {
        Object_okom1__c pz = reqEvent.getPz();

        String pzbh = getDanhao("Object_okom1__c");
        pz.setField_EHyt1__c("9101");
        pz.setField_8GijD__c(pzbh);

        ResponseEvent respEvent = pingzhengService.save(reqEvent);
        if (!respEvent.isSuccess())
            throw new ErpDataException("保存一般凭证失败 - " + respEvent.getMessage());
    }

    @Override
    protected Event createEvent(JobDetail jobDetail) throws CrmApiException, CrmDataException {
        Object_okom1__c pz = objectOkom1Service.loadById(jobDetail.getDataId());
        JobContextHolder.setType("报销单");
        JobContextHolder.setSerialNo(pz.getName());
        JobContextHolder.setOwner(pz.getOwner());

        if (!StringUtils.isEmpty(pz.getField_8GijD__c()) && !isDevMode()) {
            Debug("报销单已对接过，报销单号：{}-{}", pz.getField_EHyt1__c(), pz.getField_8GijD__c());
            throw new CrmDataException(Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.code,
                    String.format("%s[%s-%s]", Constants.interfaceResponseCode.EXECUTOR_IGNORED_SYNCHRONIZE.msg,
                            pz.getField_EHyt1__c(), pz.getField_8GijD__c()));
        }

        //客户
        if(!StringUtils.isEmpty(pz.getField_246FG__c()))
            pz.setField_246FG__c(accountService.loadById(pz.getField_246FG__c()).getField_AlGoN__c());

        //报销人
        pz.setField_N7e3j__c(personnelObjService.getNumberByOpenId(pz.getOwner().get(0)));

        List<Object_79pYP__c> pzmxs = object79pYPService.listByPzId(pz.get_id());

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
