package com.chylee.fxiaoke.xjl.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.QueryInfoFilter;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.mapper.JobDetailMapper;
import com.chylee.fxiaoke.common.model.JobDetail;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.common.util.StringUtils;
import com.chylee.fxiaoke.xjl.event.data.object.Object_qlu3s__c;
import com.chylee.fxiaoke.xjl.service.FxkHetongDjService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FxkHetongDjServiceImpl extends AbstractCrmServiceImpl implements FxkHetongDjService {
    private final JobDetailMapper detailMapper;

    public FxkHetongDjServiceImpl(AccessTokenManager accessTokenManager, JobDetailMapper detailMapper) {
        super(accessTokenManager);
        this.detailMapper = detailMapper;
    }

    @Override
    public Object_qlu3s__c getSuccess(String hetong) throws CrmApiException {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter("field_0R1xn__c", hetong));
        queryInfoFilters.add(new QueryInfoFilter("field_mv48u__c", true));
        List<Object_qlu3s__c> result = queryDataObj("object_qlu3s__c", queryInfoFilters, true, Object_qlu3s__c.class);
        return result == null || result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void upload() throws CrmApiException {
        List<JobDetail> details = detailMapper.listAll(0, 2);
        if (details != null) {
            for (JobDetail detail : details) {
                Object_qlu3s__c object = new Object_qlu3s__c();
                object.setDataObjectApiName("object_qlu3s__c");
                object.setOwner(Arrays.asList(getAdminOpenId()));
                object.setField_0R1xn__c(detail.getDataId());
                object.setField_m55dZ__c(detail.getStatus() == 1 ? null : detail.getError());
                object.setField_mv48u__c(detail.getStatus() == 1 ? true : false);
                if (detail.getStatus() == 1 && detail.getError() != null) {
                    String[] dds = detail.getError().split("-");
                    if (dds.length == 2) {
                        object.setField_161LU__c(dds[0]);
                        object.setField_55sOU__c(dds[1]);
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
    public void save(String hetong, String db, String dh, String error, boolean result) throws CrmApiException {
        Object_qlu3s__c object = new Object_qlu3s__c();
        object.setDataObjectApiName("object_qlu3s__c");
        object.setOwner(Arrays.asList(getAdminOpenId()));
        object.setField_0R1xn__c(hetong);
        object.setField_161LU__c(db);
        object.setField_55sOU__c(dh);
        object.setField_m55dZ__c(error);
        object.setField_mv48u__c(result);
        updateDataObj(object, null, true, true);
    }
}
