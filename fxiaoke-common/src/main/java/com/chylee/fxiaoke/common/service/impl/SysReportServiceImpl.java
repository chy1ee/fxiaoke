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

    @Value("${spring.profiles.active}")
    protected String profiles;

    public SysReportServiceImpl(AccessTokenManager accessTokenManager, ConfigAdminService configuration,
                                FxkReportMapper reportMapper) {
        super(accessTokenManager);
        this.configuration = configuration;
        this.reportMapper = reportMapper;
    }


    @Override
    public MsgRespEvent send(String content) {
        return send(configuration.getAdminOpenIds(), content);
    }

    @Override
    public MsgRespEvent send(List<String> openIds, String content) {
        return configuration.getStatus() == 1 ? this.sendTextMsg(openIds, content) : new MsgRespEvent();
    }

    private MsgRespEvent sendTextMsg(List<String> toUsers, String content) {
        if (content != null && content.length() > 255)
            content = StringUtils.gbkLeft(content, 255);

        MsgReqEvent reqEvent = new MsgReqEvent();
        reqEvent.setToUser(toUsers);
        reqEvent.setMsgType("text");
        reqEvent.setText(new Content(content));

        reportMapper.insert(new FxkReport(0, null, null, reqEvent.getToUser().get(0), content));

        return "dev".equals(profiles) ?  new MsgRespEvent() :
                doPost("/cgi/message/send", reqEvent, MsgRespEvent.class);
    }

    @Override
    public MsgRespEvent sendExecutorReport(List<String> toUser, String type, String serial, String error) {
        if (configuration.getStatus() != 1)
            return new MsgRespEvent();

        if (error != null && error.length() > 255)
            error = StringUtils.gbkLeft(error, 255);

        MsgReqEvent reqEvent = new MsgReqEvent();
        Composite composite = new Composite();
        composite.setHead(new Title("对接状态报告"));
        composite.setFirst(new Content(df.format(new Date())));
        composite.addForm(new Form("类型", type));
        composite.addForm(new Form("单号", serial));
        composite.addForm(new Form("结果", error == null ? "成功" : "失败"));
        if (error != null)
            composite.setRemark(new Content(error));
        composite.setLink(new Link("没有详细信息", "#"));

        if (toUser == null || toUser.isEmpty())
            reqEvent.setToUser(configuration.getAdminOpenIds());
        else {
            reqEvent.setToUser(Stream.of(toUser, configuration.getAdminOpenIds())
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList()));
        }

        reqEvent.setMsgType("composite");
        reqEvent.setComposite(composite);

        reportMapper.insert(new FxkReport(0, type, serial, reqEvent.getToUser().get(0), error));

        return "dev".equals(profiles) ?  new MsgRespEvent() :
                doPost("/cgi/message/send", reqEvent, MsgRespEvent.class);
    }
}
