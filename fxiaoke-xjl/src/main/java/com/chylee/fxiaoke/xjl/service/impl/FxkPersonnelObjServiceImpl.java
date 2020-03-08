package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.event.data.object.PersonnelObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.xjl.event.crm.DeptUserReqEvent;
import com.chylee.fxiaoke.xjl.event.crm.DeptUserRespEvent;
import com.chylee.fxiaoke.xjl.service.FxkPersonnelObjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FxkPersonnelObjServiceImpl extends AbstractCrmServiceImpl implements FxkPersonnelObjService {
    public FxkPersonnelObjServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public PersonnelObj loadByYgbh(String ygbh) throws CrmDataException, CrmApiException {
        if(ygbh == null)
            throw new CrmDataException("员工编号为空");

        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("employee_number", ygbh.trim()));
        List<PersonnelObj> personnelObjs = this.queryDataObj("PersonnelObj", queryInfoFilters, false, PersonnelObj.class);
        if (personnelObjs == null || personnelObjs.isEmpty())
            throw new CrmDataException("员工不存在ygbh=" + ygbh);

        if(personnelObjs.size() != 1)
            throw new CrmDataException("员工编号重复员工不存在ygbh=" + ygbh);


        return personnelObjs.get(0);
    }

    @Override
    public PersonnelObj loadById(String dataId) throws CrmDataException, CrmApiException {
        if(dataId == null)
            throw new CrmDataException("员工DataId为空");

        PersonnelObj obj = this.loadDataObj("PersonnelObj", dataId, false, PersonnelObj.class);
        if (obj == null)
            throw new CrmDataException("员工不存在dataId=" + dataId);

        return obj;
    }

    @Override
    public String getNumberByOpenId(String openUserId) throws CrmDataException {
        DeptUserReqEvent reqEvent = new DeptUserReqEvent();
        reqEvent.setOpenUserId(openUserId);
        DeptUserRespEvent respEvent = doPost("/cgi/user/get", reqEvent, DeptUserRespEvent.class);
        if (respEvent.isSuccess())
            return respEvent.getEmployeeNumber();

        throw new CrmDataException("获取员工编号失败 - " + respEvent.getMessage());
    }

    @Override
    public List<String> getOwner(String ygbh) throws CrmApiException {
        String ownerToReturn;
        String ygbhToUse = StringUtils.trim(ygbh);
        if(ygbhToUse == null) {
            ownerToReturn = getAdminOpenId();
        }
        else {
            try {
                PersonnelObj personnelObj = loadByYgbh(ygbhToUse);
                return personnelObj.getOwner();
            }
            catch(CrmDataException e) {
                logger.error("获取员工OpenID失败[{}]", ygbhToUse);
                ownerToReturn = getAdminOpenId();
            }
        }
        return Arrays.asList(ownerToReturn);
    }
}
