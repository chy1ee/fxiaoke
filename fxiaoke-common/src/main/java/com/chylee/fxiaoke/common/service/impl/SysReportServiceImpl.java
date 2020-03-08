package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.event.fxiaoke.msg.*;
import com.chylee.fxiaoke.common.mapper.FxkReportMapper;
import com.chylee.fxiaoke.common.model.FxkReport;
import com.chylee.fxiaoke.common.service.ConfigAdminService;
import com.chylee.fxiaoke.common.service.SysReportService;
import com.chylee.fxiaoke.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SysReportServiceImpl extends AbstractFxkServiceImpl implements SysReportService {
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ConfigAdminService configuration;
    private final FxkReportMapper reportMapper;

    public SysReportServiceImpl(AccessTokenManager accessTokenManager, ConfigAdminService configuration,
                                FxkReportMapper reportMapper) {
        super(accessTokenManager);
        this.configuration = configuration;
        this.reportMapper = reportMapper;
    }


    @Override
    public MsgRespEvent sendToAdmin(String content) {
        return sendTextMsg(configuration.getAdminOpenIds(), content);
    }

    /*
    @Override
    public MsgRespEvent send(List<String> openIds, String content) {
        return configuration.getStatus() == 1 ? this.sendTextMsg(openIds, content) : new MsgRespEvent();
    }
     */

    private MsgRespEvent sendTextMsg(List<String> toUsers, String content) {
        if (content != null && content.length() > 255)
            content = StringUtils.gbkLeft(content, 255);

        MsgReqEvent reqEvent = new MsgReqEvent();
        reqEvent.setToUser(toUsers);
        reqEvent.setMsgType("text");
        reqEvent.setText(new Content(content));

        saveReport(null, null, null, reqEvent.getToUser());

        if (isDevMode()) {
            Debug("文本信息通知[{}][{}]", toUsers, content);
            return new MsgRespEvent();
        }
        else
            return doPost("/cgi/message/send", reqEvent, MsgRespEvent.class);
    }

    @Override
    public MsgRespEvent sendExecutorReport(List<String> toUser, String type, String serial, boolean success, String message) {
        if (configuration.getStatus() != 1)
            return new MsgRespEvent();

        if (message != null && message.length() > 255)
            message = StringUtils.gbkLeft(message, 255);

        MsgReqEvent reqEvent = new MsgReqEvent();
        Composite composite = new Composite();
        composite.setHead(new Title("对接状态报告"));
        composite.setFirst(new Content(df.format(new Date())));
        composite.addForm(new Form("类型", type));
        composite.addForm(new Form("单号", serial));
        composite.addForm(new Form("结果", success ? "成功" : "失败"));
        if (message != null)
            composite.setRemark(new Content(message));
        composite.setLink(new Link("没有详细信息", "#"));

        if (toUser == null || toUser.isEmpty())
            reqEvent.setToUser(configuration.getAdminOpenIds());
        else if (!success || configuration.getStatus() == 1) {
            reqEvent.setToUser(Stream.of(toUser, configuration.getAdminOpenIds())
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList()));
        }
        else {
            reqEvent.setToUser(toUser);
        }

        reqEvent.setMsgType("composite");
        reqEvent.setComposite(composite);

        saveReport(type, serial, message, reqEvent.getToUser());

        return "dev".equals(profiles) ?  new MsgRespEvent() :
                doPost("/cgi/message/send", reqEvent, MsgRespEvent.class);
    }

    private void saveReport(String type, String serial, String error, List<String> toUser) {
        StringBuilder builder = new StringBuilder();
        for (int i=0,j=toUser.size();i<j&&i<5;i++) {
            builder.append(toUser.get(i));
            if (i > 0)
                builder.append(",");
        }
        reportMapper.insert(new FxkReport(0, type, serial, builder.toString(), error));
    }
}
