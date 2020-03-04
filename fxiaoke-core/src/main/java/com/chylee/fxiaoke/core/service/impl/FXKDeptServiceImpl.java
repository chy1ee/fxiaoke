package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.event.fxiaoke.CorpAccessToken;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.common.service.impl.AbstractFxkServiceImpl;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptListReqEvent;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptListRespEvent;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptUserListReqEvent;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptUserListRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.msg.*;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.core.service.FXKDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  处理信息发送、部门和员工
 */
@Service
public class FXKDeptServiceImpl extends AbstractFxkServiceImpl implements FXKDeptService {
    private static final Logger logger = LoggerFactory.getLogger(FXKDeptServiceImpl.class);

    public FXKDeptServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public DeptListRespEvent getDeptList() throws AccessTokenException {
        DeptListReqEvent arg = new DeptListReqEvent();
        return doPost("/cgi/department/list", arg, DeptListRespEvent.class);
    }

    @Override
    public DeptUserListRespEvent getDeptUserList(Integer departmentId, boolean fetchChild) throws AccessTokenException {
        DeptUserListReqEvent arg = new DeptUserListReqEvent();
        arg.setDepartmentId(departmentId);
        arg.setFetchChild(fetchChild);

        return doPost("/cgi/user/list", arg, DeptUserListRespEvent.class);
    }

    @Override
    public MsgRespEvent sendTextMsg(List<String> toUsers, String content) throws AccessTokenException {
        MsgReqEvent reqEvent = new MsgReqEvent();

        reqEvent.setToUser(toUsers);

        reqEvent.setMsgType("text");

        reqEvent.setText(new Content(content));

        return doPost("/cgi/message/send", reqEvent, MsgRespEvent.class);
    }

    @Override
    public MsgRespEvent sendAdminMsg(String content) throws AccessTokenException, CrmApiException {
        List<String> toUser = new ArrayList<>();
        toUser.add(getAdminOpenId());
        return sendTextMsg(toUser, content);
    }

}
