package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.core.model.JobType;

import java.util.List;

public interface JobTypeService {
    void refresh();
    List<JobType> listJobTypes();
}
