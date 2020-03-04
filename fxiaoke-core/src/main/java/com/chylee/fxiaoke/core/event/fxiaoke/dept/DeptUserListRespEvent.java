package com.chylee.fxiaoke.core.event.fxiaoke.dept;
import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

import java.util.List;

public class DeptUserListRespEvent extends BaseRespEvent {
    /**
     * 人员列表 @see User
     */
    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "DeptUserListRespEvent{" +
                "userList=" + userList +
                '}';
    }
}
