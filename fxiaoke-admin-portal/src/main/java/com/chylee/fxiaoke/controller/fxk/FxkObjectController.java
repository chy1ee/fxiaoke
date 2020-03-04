package com.chylee.fxiaoke.controller.fxk;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.object.ObjectRespEvent;
import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.common.api.Constants;
import com.chylee.fxiaoke.common.service.FxkObjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fxiaoke/object")
public class FxkObjectController {

    private final FxkObjectService objectService;

    public FxkObjectController(FxkObjectService objectService) {
        this.objectService = objectService;
    }

    @GetMapping("describe")
    public com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent describe(String apiName) {
        try {
            return objectService.describe2(apiName);
        } catch (AccessTokenException e) {
            com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent respEvent =
                    new com.chylee.fxiaoke.common.event.fxiaoke.ObjectRespEvent();
            respEvent.setError(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.code,
                    Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.msg);
            return respEvent;
        }
    }

    @GetMapping("list")
    public ObjectRespEvent list() {
        try {
            return objectService.list();
        } catch (AccessTokenException e) {
            ObjectRespEvent respEvent = new ObjectRespEvent();
            respEvent.setError(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.code,
                    Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.msg);
            return respEvent;
        }
    }
}
