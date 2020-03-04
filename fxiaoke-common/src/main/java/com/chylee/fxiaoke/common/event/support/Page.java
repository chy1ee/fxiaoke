package com.chylee.fxiaoke.common.event.support;

import java.util.List;

public class Page<T> {
    private List<T> data;
    private int page;
    private int pages;
    private int pageSize;
    private int total;

    public Page(int page, int pageSize) {
        this.page = page < 1 ? 1 : page;
        this.pageSize = pageSize;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setTotal(int total) {
        this.total = total;
        this.pages = (this.total + this.pageSize - 1) / this.pageSize;
        if(this.page > this.pages)
            this.page = this.pages;
    }

    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }
}
