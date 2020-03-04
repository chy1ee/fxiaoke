package com.chylee.fxiaoke.core.event.fxiaoke.dept;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

import java.util.List;

public class DeptListRespEvent extends BaseRespEvent {
    /**
     * 部门列表 @see DepartmentResult
     */
    private List<Department> departments;

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public String toString() {
        return "DeptListRespEvent{" +
                "departments=" + departments +
                ", errorCode=" + getErrorCode() +
                ", errorMessage='" + getErrorMessage() + '\'' +
                '}';
    }
}
