package com.chylee.fxiaoke.common.event;

import com.chylee.fxiaoke.common.event.support.Page;

import java.util.Collection;

public class DataRespEvent<T> extends ResponseEvent {
    private T data;
    private Collection<T> list;

    private long page;
    private long pages;
    private long pageSize;
    private long total;

    public DataRespEvent() { }

    public DataRespEvent(Page<T> page) {
        if(page == null || page.getTotal() == 0)
            return;

        this.total = (int)page.getTotal();
        this.pages = (int)page.getPages();
        this.page = (int)page.getPage();
        this.list = page.getData();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Collection<T> getList() {
        return list;
    }

    public void setList(Collection<T> list) {
        this.list = list;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
