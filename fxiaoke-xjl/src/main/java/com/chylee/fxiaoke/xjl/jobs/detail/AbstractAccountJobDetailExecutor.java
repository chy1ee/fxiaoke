package com.chylee.fxiaoke.xjl.jobs.detail;

import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.xjl.event.data.object.AccountAddrObj;
import com.chylee.fxiaoke.xjl.event.data.object.AccountObj;
import com.chylee.fxiaoke.xjl.event.data.object.ContactObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.JobDetailService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import com.chylee.fxiaoke.xjl.event.AccountReqEvent;
import com.chylee.fxiaoke.xjl.service.FxkAccountAddrService;
import com.chylee.fxiaoke.xjl.service.FxkAccountService;
import com.chylee.fxiaoke.xjl.service.FxkContactService;

import java.util.Collections;
import java.util.List;

public abstract class AbstractAccountJobDetailExecutor extends AbstractXjlJobDetailExecutor {
    private final FxkAccountService accountService;
    private final FxkContactService contactObjService;
    private final FxkAccountAddrService accountAddrService;

    protected AbstractAccountJobDetailExecutor(JobDetailService jobDetailService, SysReportService reportService,
                                               FXKSequenceService sequenceService, FxkAccountService accountService,
                                               FxkContactService contactObjService, FxkAccountAddrService accountAddrService) {
        super(jobDetailService, reportService, sequenceService);
        this.accountService = accountService;
        this.contactObjService = contactObjService;
        this.accountAddrService = accountAddrService;
    }

    protected AccountReqEvent getAccountReqEvent(String dataId) throws CrmApiException, CrmDataException {
        //客户
        AccountObj accountObj = accountService.loadById(dataId);

        //联系人
        List<ContactObj> contactObjs = contactObjService.listByAccounrIdAndName(accountObj.get_id(), null);
        if (contactObjs == null || contactObjs.isEmpty())
            throw new CrmDataException("联系人不能为空[" + accountObj.getField_AlGoN__c() + "]");

        Collections.sort(contactObjs);

        //联系地址
        List<AccountAddrObj> accountAddrObjs = accountAddrService.listAccountAddrByAccountId(accountObj.get_id());
        if (accountAddrObjs == null)
            accountAddrObjs = Collections.emptyList();

        AccountReqEvent reqEvent = new AccountReqEvent();
        reqEvent.setAccountObj(accountObj);
        reqEvent.setContactObj(contactObjs.get(0));
        reqEvent.setAccountAddrObjs(accountAddrObjs);

        return reqEvent;
    }

    protected void updateAccountKhbh(String accountId, String khbh, String khmc) throws CrmApiException {
        if (khbh == null) {
            logger.error("易飞的客户编号为空[{}]", khmc);
            throw new CrmApiException(Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code, "易飞的客户编号为空");
        }

        try {
            accountService.updateKhbhById(accountId,  khbh);
        }
        catch(CrmApiException e) {
            String error = String.format("%s[客户][%s][%s]",
                    Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.msg, khbh, accountId);
            logger.error(error, e);
            throw new CrmApiException(Constants.interfaceResponseCode.EXECUTOR_WRITE_BACK_ERROR.code, error);
        }
    }
}
