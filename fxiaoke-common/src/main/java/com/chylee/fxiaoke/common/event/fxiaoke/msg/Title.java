package com.chylee.fxiaoke.common.event.fxiaoke.msg;

import java.io.Serializable;

public class Title implements Serializable {
    private String title;

    public Title() {}

    public Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Title{" +
                "title='" + title + '\'' +
                '}';
    }
}
