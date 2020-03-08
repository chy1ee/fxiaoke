package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.exception.ErpDataException;
import com.chylee.fxiaoke.core.event.config.ConfigSeq;
import com.chylee.fxiaoke.core.mapper.FxkSequenceMapper;
import com.chylee.fxiaoke.core.model.FxkSequence;
import com.chylee.fxiaoke.core.service.ConfigSeqService;
import com.chylee.fxiaoke.core.service.FXKSequenceService;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  处理报价单、合同和订单序号
 */
@Service
public class FXKSequenceServiceImpl implements FXKSequenceService {
    private final FxkSequenceMapper sequenceMapper;
    private final ConfigSeqService configSeqService;

    private Object lockMonitor = new Object();
    private Map<String, DateFormat> dateFormatCacher = new HashMap<>();

    public FXKSequenceServiceImpl(FxkSequenceMapper sequenceMapper, ConfigSeqService configSeqService) {
        this.sequenceMapper = sequenceMapper;
        this.configSeqService = configSeqService;
    }

    @Override
    public String createXh(String api) throws Exception {
        synchronized (this.lockMonitor) {
            ConfigSeq seqConfig = configSeqService.getConfig(api);
            if (seqConfig == null)
                throw new ErpDataException("获取派号规则配置出错：" + api);

            String yf = getDateFormat(api, seqConfig.getPattern()).format(new Date());
            FxkSequence model = sequenceMapper.loadByApiAndYf(api, yf);
            if (model == null) {
                FxkSequence modeltoInsert = new FxkSequence();
                modeltoInsert.setApi(api);
                modeltoInsert.setYf(yf);
                modeltoInsert.setXh(2);
                modeltoInsert.setPattern("");
                modeltoInsert.setMax(0);
                modeltoInsert.setLen(0);
                sequenceMapper.insert(modeltoInsert);

                if (modeltoInsert.getId() > 0) {
                    model = new FxkSequence();
                    model.setXh(1);
                }
            }
            else {
                if (model.getXh() >= seqConfig.getMax())
                    throw new Exception("API序号超过最大值[" + api + "]");

                FxkSequence modeltoUpdate = new FxkSequence();
                modeltoUpdate.setId(model.getId());
                modeltoUpdate.setXh(model.getXh() + 1);
                sequenceMapper.updateById(modeltoUpdate);
            }

            return String.format("%s%0"+seqConfig.getLen()+"d", yf, model.getXh());
        }
    }

    private DateFormat getDateFormat(String api, String pattern) {
        DateFormat dateFormat = dateFormatCacher.get(api);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern);
            dateFormatCacher.put(api, dateFormat);
        }
        return dateFormat;
    }
}
