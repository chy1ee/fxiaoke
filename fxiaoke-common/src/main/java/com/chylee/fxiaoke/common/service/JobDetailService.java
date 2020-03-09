package com.chylee.fxiaoke.common.service;

import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.common.exception.ServiceException;
import com.chylee.fxiaoke.common.model.JobDetail;

import java.util.List;

public interface JobDetailService {
    int PAGE_SIZE = 50;
    void initJobDetailCachar();
    void reset(int id) throws ServiceException;
    void insertBatch(List<JobDetail> jobDetailList);
    void upateStatusById(int id, int status, String error);
    List<JobDetail> listStatus0();
    Page listByLogId(int logId, int page, int pageSize);
}
