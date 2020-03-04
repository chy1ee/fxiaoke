package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.mapper.SysConfigMapper;
import com.chylee.fxiaoke.common.model.SysConfig;
import com.chylee.fxiaoke.common.service.SysConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    private final SysConfigMapper configMapper;

    public SysConfigServiceImpl(SysConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    public SysConfig loadByType(int type) {
        return configMapper.loadByType(type);
    }
}
