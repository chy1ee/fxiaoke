package com.chylee.fxiaoke.common.service.impl;

import com.chylee.fxiaoke.common.cache.SelectOneCacher;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.BaseCrmReqEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.object.DescribeReqEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.object.Field;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.object.ObjectRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.object.Option;
import com.chylee.fxiaoke.common.api.AccessTokenManager;
import com.chylee.fxiaoke.common.service.FxkObjectService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@CacheConfig(cacheNames = "noExpired")
public class FxkObjectServiceImpl extends AbstractFxkServiceImpl implements FxkObjectService {

    public FxkObjectServiceImpl(AccessTokenManager accessTokenManager) {
        super(accessTokenManager);
    }

    @Override
    public ObjectRespEvent list() {
        BaseCrmReqEvent reqEvent = new BaseCrmReqEvent();
        return doPost("/cgi/crm/v2/object/list", reqEvent, ObjectRespEvent.class);
    }

    @Override
    public com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent<String> describe2(String apiName) {
        DescribeReqEvent reqEvent = new DescribeReqEvent();
        reqEvent.setApiName(apiName);
        return doPost("/cgi/crm/v2/object/describe", reqEvent);
    }

    public ObjectRespEvent describe(String apiName) {
        DescribeReqEvent reqEvent = new DescribeReqEvent();
        reqEvent.setApiName(apiName);
        return doPost("/cgi/crm/v2/object/describe", reqEvent, ObjectRespEvent.class);
    }

    @Override
    @Cacheable
    public SelectOneCacher selectOneMap(String objName, String apiName) {
        if (logger.isDebugEnabled())
            logger.debug("开始缓存{}的{}的属性", objName, apiName);

        SelectOneCacher result = new SelectOneCacher();

        ObjectRespEvent objectRespEvent = this.describe(objName);
        if(!objectRespEvent.isSuccess()) {
            logger.error("缓存失败[{}][{}]", objName, apiName);
            return result;
        }

        Collection<Field> fields = objectRespEvent.getData().getDescribe().getFields().values();
        for(Field field : fields) {
            if (apiName != null && !apiName.equals(field.getApi_name()))
                continue;

            try {
                if ("select_one".equals(field.getType()) && field.getOptions() != null) {
                    Debug("缓存SelectOne[{}][{}]", objName, apiName);
                    int i = 0;
                    for(Option option : field.getOptions()) {
                        Debug("{}、[label={}][value={}]", i++, option.getValue(), option.getLabel());
                        result.put(option.getValue(), option.getLabel());
                    }
                }
            }
            catch(Exception e) {
                logger.error("缓存单选代码表失败 - " + field.getApi_name(), e);
            }
        }

        return result;
    }

    @Override
    @CacheEvict(key="'com.chylee.fxiaoke.xjl.service.impl.FxkObjectServiceImpl_selectOneMap_'+#objName+'_'+#apiName")
    public void clearCacher(String objName, String apiName) {
        Debug("***清空缓存[com.chylee.fxiaoke.xjl.service.impl.FxkObjectServiceImpl_selectOneMap_{}_{}]", objName, apiName);
    }
}
