package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.config.ConfigAdminReqEvent;
import com.chylee.fxiaoke.common.mapper.SysConfigMapper;
import com.chylee.fxiaoke.common.model.SysConfig;
import com.chylee.fxiaoke.common.service.ConfigAdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@CacheConfig(cacheNames = "noExpired")
public class ConfigAdminServiceImpl implements ConfigAdminService {
    private Logger logger = LoggerFactory.getLogger(ConfigAdminServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    private final SysConfigMapper configMapper;

    public ConfigAdminServiceImpl(SysConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    @Cacheable(key = "'com.chylee.fxiaoke.common.service.impl.ConfigAdminServiceImpl_getStatus'")
    public int getStatus() {
        if (logger.isDebugEnabled())
            logger.debug("从数据库获取配置信息： {}-status", ConfigAdminReqEvent.TYPE);

        ConfigAdminReqEvent reqEvent = getCofig();
        return reqEvent == null ? 0 : reqEvent.getStatus();
    }

    @Override
    @Cacheable(key = "'com.chylee.fxiaoke.common.service.impl.ConfigAdminServiceImpl_getAdminOpenIds'")
    public List<String> getAdminOpenIds() {
        if (logger.isDebugEnabled())
            logger.debug("从数据库获取配置信息： {}-adminIds", ConfigAdminReqEvent.TYPE);

        ConfigAdminReqEvent reqEvent = getCofig();
        return reqEvent == null ? null : Arrays.asList(reqEvent.getOpenid().split("\n"));
    }

    @Override
    @CacheEvict(key = "'com.chylee.fxiaoke.common.service.impl.ConfigAdminServiceImpl_getStatus'")
    public void cleanStatus() {
        if (logger.isDebugEnabled())
            logger.debug("删除缓存配置信息： {}-status", ConfigAdminReqEvent.TYPE);
    }

    @Override
    @CacheEvict(key = "'com.chylee.fxiaoke.common.service.impl.ConfigAdminServiceImpl_getAdminOpenIds'")
    public void cleanAdminOpenIds() {
        if (logger.isDebugEnabled())
            logger.debug("删除缓存配置信息： {}-adminIds", ConfigAdminReqEvent.TYPE);
    }

    @Override
    public ResponseEvent saveConfig(ConfigAdminReqEvent reqEvent) {
        SysConfig config;
        try {
            config = new SysConfig(reqEvent.getId(), ConfigAdminReqEvent.TYPE,
                    objectMapper.writeValueAsString(reqEvent));
        } catch (JsonProcessingException e) {
            logger.error("保存失败", e);
            return new ResponseEvent("保存失败");
        }

        if (config.getId() == null)
            configMapper.insert(config);
        else
            configMapper.updateById(config);

        return new ResponseEvent();
    }

    public ConfigAdminReqEvent getCofig() {
        SysConfig config = this.configMapper.loadByType(ConfigAdminReqEvent.TYPE);
        if (config != null) {
            try {
                return objectMapper.readValue(config.getContent(), ConfigAdminReqEvent.class);
            } catch (JsonProcessingException e) {
                logger.error("获取配置信息失败：1002", e);
            }
        }

        return new ConfigAdminReqEvent();
    }
}
