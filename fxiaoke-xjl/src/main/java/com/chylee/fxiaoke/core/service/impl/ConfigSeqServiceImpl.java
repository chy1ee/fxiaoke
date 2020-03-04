package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.event.config.ConfigApiReqEvent;
import com.chylee.fxiaoke.common.mapper.SysConfigMapper;
import com.chylee.fxiaoke.common.model.SysConfig;
import com.chylee.fxiaoke.core.event.config.ConfigSeq;
import com.chylee.fxiaoke.core.event.config.ConfigSeqReqEvent;
import com.chylee.fxiaoke.core.service.ConfigSeqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@CacheConfig(cacheNames = "noExpired")
public class ConfigSeqServiceImpl implements ConfigSeqService {
    private final SysConfigMapper configMapper;

    private Logger logger = LoggerFactory.getLogger(ConfigSeqServiceImpl.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public ConfigSeqServiceImpl(SysConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    @Cacheable
    public ConfigSeq getConfig(String api) {
        SysConfig config = configMapper.loadByType(ConfigSeqReqEvent.TYPE);
        if (config == null)
            return null;

        ConfigSeqReqEvent reqEvent;
        try {
            reqEvent = objectMapper.readValue(config.getContent(), ConfigSeqReqEvent.class);
        } catch (JsonProcessingException e) {
            logger.error("读取配置信息错误["+ConfigSeqReqEvent.TYPE+"]", e);
            return null;
        }

        try {
            Method method = ConfigSeqReqEvent.class.getDeclaredMethod("getApi" + api);
            return (ConfigSeq) method.invoke(reqEvent);
        } catch (Exception e) {
            logger.error("读取参数值错误["+ConfigSeqReqEvent.TYPE+"]["+api+"]", e);
        }

        return null;
    }
}
