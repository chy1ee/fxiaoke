package com.chylee.fxiaoke.common.event.fxiaoke.msg;

import java.util.ArrayList;
import java.util.List;

public class Composite {
    private Title head;
    private Content first;
    private List<Form>  form;
    private Content remark;
    private Link link;

    public Title getHead() {
        return head;
    }

    public void setHead(Title head) {
        this.head = head;
    }

    public Content getFirst() {
        return first;
    }

    public void setFirst(Content first) {
        this.first = first;
    }

    public List<Form> getForm() {
        return form;
    }

    public void setForm(List<Form> form) {
        this.form = form;
    }

    public void addForm(Form form) {
        if (this.form == null)
            this.form = new ArrayList<>();
        this.form.add(form);
    }

    public Content getRemark() {
        return remark;
    }

    public void setRemark(Content remark) {
        this.remark = remark;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
