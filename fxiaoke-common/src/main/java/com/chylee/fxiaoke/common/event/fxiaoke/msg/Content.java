package com.chylee.fxiaoke.common.event.fxiaoke.msg;

import java.io.Serializable;

public class Content implements Serializable {
    private String content;

    public Content() {}

    public Content(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" +
                "content='" + content + '\'' +
                '}';
    }
}
