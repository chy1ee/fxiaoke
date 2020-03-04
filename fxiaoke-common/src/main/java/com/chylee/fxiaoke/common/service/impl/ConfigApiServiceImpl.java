package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.config.ConfigApiReqEvent;
import com.chylee.fxiaoke.common.mapper.SysConfigMapper;
import com.chylee.fxiaoke.common.model.SysConfig;
import com.chylee.fxiaoke.common.service.ConfigApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "noExpired")
public class ConfigApiServiceImpl implements ConfigApiService {
    private Logger logger = LoggerFactory.getLogger(ConfigApiServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    private final SysConfigMapper configMapper;

    public ConfigApiServiceImpl(SysConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    @Cacheable(key = "'com.chylee.fxiaoke.common.service.impl.ConfigApiServiceImpl.getConfig'")
    public ConfigApiReqEvent getConfig() {
        if (logger.isDebugEnabled())
            logger.debug("*********从数据库获取配置信息： {}", ConfigApiReqEvent.TYPE);
        SysConfig config = this.configMapper.loadByType(ConfigApiReqEvent.TYPE);
        if (config != null) {
            try {
                return objectMapper.readValue(config.getContent(), ConfigApiReqEvent.class);
            } catch (JsonProcessingException e) {
                logger.error("获取配置信息失败：{"+ConfigApiReqEvent.TYPE+"}", e);
            }
        }

        return new ConfigApiReqEvent();
    }

    @Override
    @CacheEvict(key = "'com.chylee.fxiaoke.common.service.impl.ConfigApiServiceImpl.getConfig'")
    public ResponseEvent saveConfig(ConfigApiReqEvent reqEvent) {
        SysConfig config;
        try {
            config = new SysConfig(reqEvent.getId(), ConfigApiReqEvent.TYPE,
                    objectMapper.writeValueAsString(reqEvent));
        } catch (JsonProcessingException e) {
            logger.error("保存失败", e);
            return new ResponseEvent("保存失败");
        }

        if (config.getId() == null)
            configMapper.insert(config);
        else
            configMapper.updateById(config);

        if (logger.isDebugEnabled())
            logger.debug("*********删除缓存配置信息： {}", ConfigApiReqEvent.TYPE);

        return new ResponseEvent();
    }
}
