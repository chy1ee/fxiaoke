package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.core.mapper.JobTypeMapper;
import com.chylee.fxiaoke.core.model.JobType;
import com.chylee.fxiaoke.core.service.JobTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "noExpired")
public class JobTypeServiceImpl implements JobTypeService {
    private Logger logger = LoggerFactory.getLogger(JobTypeServiceImpl.class);

    private final JobTypeMapper jobTypeMapper;

    public JobTypeServiceImpl(JobTypeMapper jobTypeMapper) {
        this.jobTypeMapper = jobTypeMapper;
    }

    @Override
    @CacheEvict(key = "'com.chylee.fxiaoke.core.service.impl.JobTypeServiceImpl.JobType'")
    public void refresh() {
        if (logger.isDebugEnabled())
            logger.debug("********清空任务列表缓存");
    }

    @Override
    @Cacheable(key = "'com.chylee.fxiaoke.core.service.impl.JobTypeServiceImpl.JobType'")
    public List<JobType> listJobTypes() {
        if (logger.isDebugEnabled())
            logger.debug("********从数据库加载任务列表");
        return this.jobTypeMapper.listAll();
    }

}
