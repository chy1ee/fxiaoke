package com.chylee.fxiaoke.common.api;

import com.chylee.fxiaoke.common.event.RequestEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.BaseRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent;
import com.chylee.fxiaoke.common.exception.FxiaokeException;
import com.chylee.fxiaoke.common.service.impl.AbstractFxkServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FXiaokeApi extends ComplieModeSupported {
    protected final String apiUrl = "https://open.fxiaoke.com";

    protected Logger logger = LoggerFactory.getLogger(AbstractFxkServiceImpl.class);

    protected <T extends BaseRespEvent> T doPost(String url, RequestEvent reqEvent, Class<T> clazz) {
        return doPost(url, reqEvent, clazz, null);
    }

    protected <T extends BaseRespEvent> T doPost(String url, RequestEvent reqEvent, Class<T> clazz, Class parameterType) {
        T t = null;
        ObjectRespEvent<String> result = doPost(url, reqEvent);
        if (result.getErrorCode() == 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                if (parameterType == null)
                    t = mapper.readValue(result.getData(), clazz);
                else {
                    JavaType javaType = TypeFactory.defaultInstance().constructParametricType(clazz, parameterType);
                    t = mapper.readValue(result.getData(), javaType);
                }
            } catch (Exception e) {
                logger.error("Json反序列化失败", e);
                try {
                    t = clazz.newInstance();
                    t.setErrorCode(Constants.interfaceResponseCode.FXK_POST_JSON_CONVERT_ERROR.code);
                    t.setErrorMessage(Constants.interfaceResponseCode.FXK_POST_JSON_CONVERT_ERROR.msg);
                } catch (Exception e2) {
                    logger.error("doPost error, details:", e2);
                }
            }
        }

        if (t != null) {
            return t;
        }

        try {
            t = clazz.newInstance();
            t.setErrorCode(result.getErrorCode());
            t.setErrorMessage(result.getErrorMessage());
        } catch (Exception e) {
            logger.error("doPost error, details:", e);
        }
        return t;
    }

    protected ObjectRespEvent<String> doPost(String url, RequestEvent reqEvent) {
        ObjectRespEvent<String> result = validate(reqEvent);
        if (!result.isSuccess())
            return result;

        try {
            Thread.sleep(200);

            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonToPost = mapper.writeValueAsString(reqEvent);

            if(logger.isDebugEnabled())
                logger.debug("url: {}, reqEvent: {}", url, jsonToPost);

            HttpResponseMessageVO resp = HttpTookit.sendPostByJson(apiUrl + url, jsonToPost);

            if(logger.isDebugEnabled())
                logger.debug("url = {},respEvent = {}", url, resp.toString());

            if ("200".equals(resp.getHttpCode())) {
                result.setData(resp.getContent());
            } else {
                result.setErrorCode(Constants.interfaceException.INTERFACE_EXCEPTION.code);
                result.setErrorMessage(Constants.interfaceException.INTERFACE_EXCEPTION.msg.concat(",HTTP Status Code:").concat(
                        resp.getHttpCode()));
            }
        } catch (FxiaokeException e) {
            logger.error("doPost error, details:", e);
            result.setErrorMessage(e.getMessage());
            result.setErrorCode(e.getCode());
        } catch (JsonProcessingException e) {
            logger.error("doPost error, details:", e);
            Constants.interfaceResponseCode a = Constants.interfaceResponseCode.FXK_POST_JSON_CONVERT_ERROR;
            result.setErrorCode(a.code);
            result.setErrorMessage(a.msg);
        } catch (InterruptedException e) {
            logger.error("doPost error, details:", e);
            result.setErrorCode(-1);
            result.setErrorMessage("等待超时");
        }

        return result;
    }

    protected ObjectRespEvent<String> validate(RequestEvent reqEvent) {
        return new ObjectRespEvent();
    }
}
