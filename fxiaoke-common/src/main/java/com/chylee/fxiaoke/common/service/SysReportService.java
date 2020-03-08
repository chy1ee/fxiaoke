package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.event.fxiaoke.msg.MsgRespEvent;

import java.util.List;

public interface SysReportService {
    MsgRespEvent sendToAdmin(String content);
    MsgRespEvent sendExecutorReport(List<String> toUser, String type, String serial, boolean success, String error);
}
