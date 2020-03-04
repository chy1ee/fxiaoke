package com.chylee.fxiaoke.core.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class QrtzGroup {
    private Integer id;
    @NotBlank(message="名称不能为空")
    @Length(min=2, max=20, message = "名称长度必须为2-20位")
    private String name;
    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
