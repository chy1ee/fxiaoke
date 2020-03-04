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

    private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    private Object lockMonitor = new Object();
    private Map<String, DateFormat> dateFormatCacher = new HashMap<>();

    public FXKSequenceServiceImpl(FxkSequenceMapper sequenceMapper, ConfigSeqService configSeqService) {
        this.sequenceMapper = sequenceMapper;
        this.configSeqService = configSeqService;
    }

    @Override
    public String createXh(String api) throws Exception {
        synchronized (this.lockMonitor) {
            Date now = new Date();
            String yf = df.format(now);
            FxkSequence model = sequenceMapper.loadByApiAndYf(api, yf);
            FxkSequence modeltoInsert = new FxkSequence();
            if (model == null) {
                ConfigSeq seqConfig = configSeqService.getConfig(api);
                if (seqConfig == null)
                    throw new ErpDataException("获取派号规则出错：" + api);

                modeltoInsert.setApi(api);
                modeltoInsert.setYf(yf);
                modeltoInsert.setXh(2);
                modeltoInsert.setPattern(seqConfig.getPattern());
                modeltoInsert.setMax(seqConfig.getMax());
                modeltoInsert.setLen(seqConfig.getLen());
                sequenceMapper.insert(modeltoInsert);

                if (modeltoInsert.getId() > 0) {
                    model = sequenceMapper.selectById(modeltoInsert.getId());
                    if (model == null)
                        throw new ErpDataException("新增派号信息失败[id=" + modeltoInsert.getId() + "]");

                    if (dateFormatCacher.containsKey(api))
                        dateFormatCacher.remove(api);
                }
            }
            else {
                if (model.getXh() >= model.getMax())
                    throw new Exception("API序号超过最大值[" + api + "]");

                modeltoInsert.setId(model.getId());
                modeltoInsert.setXh(model.getXh() + 1);
                sequenceMapper.updateById(modeltoInsert);

            }

            DateFormat dateFormat = dateFormatCacher.get(api);
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat(model.getPattern());
                dateFormatCacher.put(api, dateFormat);
            }

            return String.format("%s%0"+model.getLen()+"d", dateFormatCacher.get(api).format(now), model.getXh());
        }
    }
}
