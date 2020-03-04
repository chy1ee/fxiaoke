package com.chylee.fxiaoke.common.model;

public class SysConfig {
    private Integer id;
    private int type;
    private String content;

    public SysConfig() {}

    public SysConfig(Integer id, int type, String content) {
        this.id = id;
        this.type = type;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
