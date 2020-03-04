package com.chylee.fxiaoke.common.event.fxiaoke.data.object;

import java.util.List;

/**
 * 员工
 */
public class PersonnelObj extends DataObject {
    private String name;    //员工昵称
    private String employee_number; //员工编号
    private List<String> main_department; //主属部门
    private String position;    //职位
    private List<String> owner;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployee_number() {
        return employee_number;
    }

    public void setEmployee_number(String employee_number) {
        this.employee_number = employee_number;
    }

    public List<String> getMain_department() {
        return main_department;
    }

    public void setMain_department(List<String> main_department) {
        this.main_department = main_department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<String> getOwner() {
        return owner;
    }

    public void setOwner(List<String> owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "PersonnelObj{" +
                "name='" + name + '\'' +
                ", employee_number='" + employee_number + '\'' +
                ", main_department=" + main_department +
                ", position='" + position + '\'' +
                ", owner=" + owner +
                '}';
    }
}
