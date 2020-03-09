/**
 * 合同
 */
package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.xjl.event.data.object.Object_snPZx__c;
import com.chylee.fxiaoke.xjl.event.data.object.PersonnelObj;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.exception.CrmDataException;
import com.chylee.fxiaoke.common.exception.ErpDataException;
import com.chylee.fxiaoke.common.service.FxkObjectService;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.service.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkHetongServiceImpl extends AbstractCrmServiceImpl implements FxkHetongService {
    private final FxkObjectService objectService;
    private final FxkPersonnelObjService personnelObjService;

    public FxkHetongServiceImpl(AccessTokenManager accessTokenManager, FxkObjectService objectService,
                                     FxkPersonnelObjService personnelObjService) {
        super(accessTokenManager);
        this.objectService = objectService;
        this.personnelObjService = personnelObjService;
    }

    @Override
    public void update(String ht, String db, String dh) throws CrmApiException {
        Object_snPZx__c toUpdate = new Object_snPZx__c();
        toUpdate.setDataObjectApiName("object_snPZx__c");
        toUpdate.set_id(ht);
        toUpdate.setField_d91gZ__c(db);
        toUpdate.setField_WCjgL__c(dh);

        updateDataObj(toUpdate, null, true, false);
    }

    @Override
    public Object_snPZx__c loadById(String id) throws CrmApiException, CrmDataException, ErpDataException {
        Object_snPZx__c ht = loadDataObj("object_snPZx__c", id, true, Object_snPZx__c.class);
        if (ht == null)
            throw new CrmDataException("合同不存在["+id+"]");

        //乙方联系人
        if (!StringUtils.isEmpty(ht.getField_1KCtX__c())) {
            PersonnelObj persion = personnelObjService.loadById(ht.getField_1KCtX__c());
            if (logger.isDebugEnabled())
                logger.debug("乙方联系人：" + persion);
            ht.setAA016(persion.getEmployee_number());
        }

        //单号单别
        if ("option1".equals(ht.getField_h8a0k__c()))
            ht.setField_d91gZ__c("2S01");
        else if ("S6d1Y5ruw".equals(ht.getField_h8a0k__c()))
            ht.setField_d91gZ__c("2S02");
        else
            throw new ErpDataException("乙方无效");

        return handleSelectOne(ht);
    }

    @Override
    public List<Object_snPZx__c> listByDbAndDh(String db, String dh) throws CrmApiException, CrmDataException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_d91gZ__c", db));
        queryInfoFilters.add(new QueryInfoFilter("field_WCjgL__c", dh));
        List<Object_snPZx__c> hts = queryDataObj("object_snPZx__c", queryInfoFilters,
                true, Object_snPZx__c.class);
        if (hts == null || hts.isEmpty())
            throw new CrmDataException(String.format("找不到对应的合同：%s-%s", db, dh));

        return hts;
    }

    private Object_snPZx__c handleSelectOne(Object_snPZx__c ht) {

        if (ht.getField_9Mc92__c() != null)
            ht.setField_9Mc92__c(
                    objectService.selectOneMap("object_snPZx__c", "field_9Mc92__c").loadByLabel(ht.getField_9Mc92__c()));

        if (ht.getField_Q977o__c() != null)
            ht.setField_Q977o__c(
                    objectService.selectOneMap("object_snPZx__c", "field_Q977o__c").loadByLabel(ht.getField_Q977o__c()));


        return ht;
    }
}
