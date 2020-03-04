package com.chylee.fxiaoke.xjl.event.crm;

import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;

public class DeptUserRespEvent extends BaseRespEvent {
    private String employeeNumber;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
