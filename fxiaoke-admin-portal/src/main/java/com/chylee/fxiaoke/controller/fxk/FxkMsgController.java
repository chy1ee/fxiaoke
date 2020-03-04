package com.chylee.fxiaoke.controller.fxk;

import com.chylee.fxiaoke.common.event.fxiaoke.msg.MsgRespEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.core.service.FXKDeptService;
import com.chylee.fxiaoke.common.api.Constants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("fxiaoke/msg")
public class FxkMsgController {

    private final FXKDeptService fxkDeptService;

    public FxkMsgController(FXKDeptService fxkDeptService) {
        this.fxkDeptService = fxkDeptService;
    }

    @PostMapping
    public MsgRespEvent sendMsg(String openId, String content) {
        MsgRespEvent result;

        List<String> toUsers = new ArrayList<>();
        toUsers.add(openId);
        try {
            result = this.fxkDeptService.sendTextMsg(toUsers, content);
        }
        catch(AccessTokenException e) {
            result = new MsgRespEvent();
            result.setErrorCode(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.code);
            result.setErrorMessage(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.msg);
        }

        return result;
    }

}
