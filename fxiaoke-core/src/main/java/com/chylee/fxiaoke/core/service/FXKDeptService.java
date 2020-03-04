package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptListRespEvent;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptUserListRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.msg.MsgRespEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;

import java.util.List;

public interface FXKDeptService {
    /**
     * 获取部门列表
     *
     * @return
     * @throws AccessTokenException
     */
    DeptListRespEvent getDeptList() throws AccessTokenException;

    DeptUserListRespEvent getDeptUserList(Integer departmentId, boolean fetchChild) throws AccessTokenException;

    MsgRespEvent sendTextMsg(List<String> toUsers, String content) throws AccessTokenException;

    MsgRespEvent sendAdminMsg(String content) throws AccessTokenException, CrmApiException;
}
