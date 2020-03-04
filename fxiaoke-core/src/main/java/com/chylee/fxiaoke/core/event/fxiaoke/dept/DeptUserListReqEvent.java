package com.chylee.fxiaoke.core.event.fxiaoke.dept;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseReqEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.AccessTokenManager;

public class DeptUserListReqEvent extends BaseReqEvent {

    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 是否获取子部门
     */
    private boolean fetchChild;

    private boolean showDepartmentIdsDetail;

    public DeptUserListReqEvent() {
        this.showDepartmentIdsDetail = true;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public boolean isFetchChild() {
        return fetchChild;
    }

    public void setFetchChild(boolean fetchChild) {
        this.fetchChild = fetchChild;
    }

    public boolean isShowDepartmentIdsDetail() {
        return showDepartmentIdsDetail;
    }

    public void setShowDepartmentIdsDetail(boolean showDepartmentIdsDetail) {
        this.showDepartmentIdsDetail = showDepartmentIdsDetail;
    }

    @Override
    public String toString() {
        return "DeptUserListReqEvent{" +
                "departmentId=" + departmentId +
                ", fetchChild=" + fetchChild +
                ", showDepartmentIdsDetail=" + showDepartmentIdsDetail +
                ", corpAccessToken='" + corpAccessToken + '\'' +
                ", corpId='" + corpId + '\'' +
                '}';
    }
}
