package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.*;
import com.chylee.fxiaoke.common.event.fxiaoke.data.object.DataObject;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.exception.CrmApiException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class AbstractCrmServiceImpl extends AbstractFxkServiceImpl {

    public AbstractCrmServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    /**
     * 更新对象数据
     *
     * @param obj
     * @param <T>
     * @return
     * @throws CrmApiException
     * @throws AccessTokenException
     */
    protected <T extends DataObject> String updateDataObj(T obj)
            throws CrmApiException {
        return updateDataObj(obj, null, false, false);
    }

    /**
     * 更新对象数据
     *
     * @param obj
     * @param details
     * @param isCustom
     * @param isCreate
     * @param <T>
     * @return
     */
    protected <T extends DataObject> String updateDataObj(DataObject obj, Map<String, List<T>> details,
                                                          boolean isCustom,
                                                          boolean isCreate)
            throws CrmApiException {
        UpdateRespEvent respEvent = update(obj, details, isCustom, isCreate);
        if (!respEvent.isSuccess()) {
            throw new CrmApiException(new StringBuffer(isCreate?"新增":"更新")
                    .append("对象失败[").append(obj.getDataObjectApiName()).append("][")
                    .append(obj.get_id()).append("]").append(respEvent.getErrorMessage()).toString());
        }

        return respEvent.getDataId();
    }

    /**
     * 查询对象
     *
     * @param apiName
     * @param queryInfoFilters
     * @param custom
     * @param responseType
     * @param <T>
     * @return
     */
    protected <T extends DataObject> List<T> queryDataObj(String apiName, List<QueryInfoFilter> queryInfoFilters,
                                                          boolean custom, Class<T> responseType)
            throws CrmApiException {
         return queryDataObj(apiName, queryInfoFilters, null, custom, responseType);
    }

    protected <T extends DataObject> List<T> queryDataObj(String apiName, List<QueryInfoFilter> queryInfoFilters,
                                                          List<QueryInfoOrder> orders, boolean custom, Class<T> responseType)
            throws CrmApiException {
        QueryRespEvent<T> respEvent = query(apiName, queryInfoFilters, orders, responseType, custom);
        if (!respEvent.isSuccess())
            throw new CrmApiException("查找对象失败["+apiName+"][" + queryInfoFilters.toString() + "]" + respEvent.getErrorMessage());

        return respEvent.getData().getDataList();
    }

    /**
     * 根据ID查找对象
     *
     * @param apiName
     * @param dataId
     * @param custom
     * @param responseType
     * @param <T>
     * @return
     * @throws CrmApiException
     * @throws AccessTokenException
     */
    protected <T extends DataObject> T loadDataObj(String apiName, String dataId, boolean custom, Class<T> responseType)
            throws CrmApiException {
        GetRespEvent<T> respEvent = get(apiName, dataId,
                responseType, custom);
        if(!respEvent.isSuccess())
            throw new CrmApiException("根据ID查询对象失败[" + apiName + "][" + dataId + "]" + respEvent.getErrorMessage());

        return respEvent.getData();
    }

    protected <T extends DataObject> GetRespEvent<T> get(String dataObjectApiName, String objectDataId,
                                                         Class<T> clazz, boolean isCustom) {
        GetData data = new GetData();
        data.setDataObjectApiName(dataObjectApiName);
        data.setObjectDataId(objectDataId);
        GetReqEvent reqEvent = new GetReqEvent();
        reqEvent.setData(data);
        return doPost(
                isCustom ? "/cgi/crm/custom/data/get" : "/cgi/crm/v2/data/get",
                reqEvent, GetRespEvent.class, clazz);
    }

    protected <T extends DataObject> QueryRespEvent query(String objectApiName, List<QueryInfoFilter> filterList,
                                                          List<QueryInfoOrder> orders, Class<T> clazz, boolean isCustom) {
        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setFilters(filterList);
        queryInfo.setOrders(orders);
        QueryData data = new QueryData();
        data.setDataObjectApiName(objectApiName);
        data.setSearch_query_info(queryInfo);
        QueryReqEvent reqEvent = new QueryReqEvent();
        reqEvent.setData(data);
        return doPost(isCustom ? "/cgi/crm/custom/data/query" : "/cgi/crm/v2/data/query",
                reqEvent, QueryRespEvent.class, clazz);
    }

    protected <T extends DataObject> QueryRespEvent query(String objectApiName, List<QueryInfoFilter> filterList,
                                                          Class<T> clazz, boolean isCustom) {
        return query(objectApiName, filterList, null, clazz, isCustom);
    }

    protected <T  extends DataObject> UpdateRespEvent update(DataObject obj, Map<String, List<T>> details,
                                                             boolean isCustom,
                                                             boolean isCreate) {
        UpdateData updateData = new UpdateData();
        updateData.setObject_data(obj);
        updateData.setDetails(details);
        UpdateReqEvent reqEvent = new UpdateReqEvent();
        reqEvent.setData(updateData);
        String url = MessageFormat.format("/cgi/crm/{0}/data/{1}",
                isCustom ? "custom" : "v2", isCreate ? "create" : "update");
        return doPost(url, reqEvent, UpdateRespEvent.class);
    }
}
