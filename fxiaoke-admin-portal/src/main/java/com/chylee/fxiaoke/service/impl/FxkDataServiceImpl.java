package com.chylee.fxiaoke.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.data.*;
import com.chylee.fxiaoke.common.event.fxiaoke.msg.*;
import com.chylee.fxiaoke.common.service.impl.AbstractCrmServiceImpl;
import com.chylee.fxiaoke.service.FxkDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FxkDataServiceImpl extends AbstractCrmServiceImpl implements FxkDataService {
    public FxkDataServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public ResponseEvent sendTestMessage() {
        MsgReqEvent reqEvent = new MsgReqEvent();

        List<String> toUsers = new ArrayList<>();
        toUsers.add("FSUID_F93A48F35DB2F9E0274C023C6C0C4CEB");

        Composite composite = new Composite();
        composite.setHead(new Title("对接状态报告"));
        composite.setFirst(new Content("2019年12月21日"));
        composite.addForm(new Form("类型", "报价单"));
        composite.addForm(new Form("单号", "123456"));
        composite.addForm(new Form("结果", "失败"));
        composite.setRemark(new Content("remark"));
        composite.setLink(new Link("没有详细信息", "#"));

        reqEvent.setToUser(toUsers);
        reqEvent.setMsgType("composite");
        reqEvent.setComposite(composite);

        return doPost("/cgi/message/send", reqEvent, MsgRespEvent.class);
    }

    @Override
    public ResponseEvent getV2(String api, String id, boolean custom) {
        GetData data = new GetData();
        data.setDataObjectApiName(api);
        data.setObjectDataId(id);
        GetReqEvent reqEvent = new GetReqEvent();
        reqEvent.setData(data);

        return doPost(custom ? "/cgi/crm/custom/data/get" : "/cgi/crm/v2/data/get", reqEvent);
    }

    @Override
    public ResponseEvent queryV2(String api, String name, String value, boolean custom) {
        List<QueryInfoFilter> queryInfoFilters = new ArrayList<>();
        queryInfoFilters.add(new QueryInfoFilter(name, value));

        QueryInfo queryInfo = new QueryInfo();
        queryInfo.setFilters(queryInfoFilters);

        QueryData data = new QueryData();
        data.setDataObjectApiName(api);
        data.setSearch_query_info(queryInfo);

        QueryReqEvent reqEvent = new QueryReqEvent();
        reqEvent.setData(data);

        return doPost(custom ? "/cgi/crm/custom/data/query" : "/cgi/crm/v2/data/query", reqEvent);
    }
}
