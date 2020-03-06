package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.mapper.JobDetailMapper;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.event.data.object.Object_1sCfv__c;
import com.chylee.fxiaoke.xjl.service.FxkBaojiadanDjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FxkBaojiadanDjServiceImpl extends AbstractCrmServiceImpl implements FxkBaojiadanDjService {
    private final JobDetailMapper detailMapper;

    public FxkBaojiadanDjServiceImpl(AccessTokenManager accessTokenManager, JobDetailMapper detailMapper) {
        super(accessTokenManager);
        this.detailMapper = detailMapper;
    }

    @Override
    public Object_1sCfv__c getSuccess(String baojiadan) throws CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_o2t9J__c", baojiadan));
        queryInfoFilters.add(new QueryInfoFilter("field_1xf8a__c", true));
        List<Object_1sCfv__c> result = queryDataObj("object_1sCfv__c", queryInfoFilters, true, Object_1sCfv__c.class);
        return result == null || result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void upload() throws CrmApiException {
        List<JobDetail> details = detailMapper.listAll(0, 1);
        if (details != null) {
            for (JobDetail detail : details) {
                Object_1sCfv__c object = new Object_1sCfv__c();
                object.setDataObjectApiName("object_1sCfv__c");
                object.setOwner(Arrays.asList(getAdminOpenId()));
                object.setField_o2t9J__c(detail.getDataId());
                object.setField_ZWCn2__c(detail.getStatus() == 1 ? null : detail.getError());
                object.setField_1xf8a__c(detail.getStatus() == 1 ? true : false);
                if (detail.getStatus() == 1 && detail.getError() != null) {
                    String[] dds = detail.getError().split("-");
                    if (dds.length == 2) {
                        object.setField_GI813__c(dds[0]);
                        object.setField_88n41__c(dds[1]);
                    }
                }
                try {
                    updateDataObj(object, null, true, true);
                } catch (Exception e) {
                    detailMapper.updateDzById(
                            detail.getId(), e.getMessage() == null ? null : StringUtils.gbkLeft(e.getMessage(), 255));
                }
            }
        }
    }

    @Override
    public void save(String baojiadan, String db, String dh, String error, boolean result) throws CrmApiException {
        Object_1sCfv__c object = new Object_1sCfv__c();
        object.setDataObjectApiName("object_1sCfv__c");
        object.setField_o2t9J__c(baojiadan);
        object.setField_GI813__c(db);
        object.setField_88n41__c(dh);
        object.setField_ZWCn2__c(error);
        object.setField_1xf8a__c(result);
        object.setOwner(Arrays.asList(getAdminOpenId()));
        updateDataObj(object, null, true, true);
    }
}
