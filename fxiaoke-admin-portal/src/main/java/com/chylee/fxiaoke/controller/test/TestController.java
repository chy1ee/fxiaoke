package com.chylee.fxiaoke.controller.test;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.xjl.service.FxkBaojiadanDjService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    private final FxkBaojiadanDjService baojiadanDjService;

    public TestController(FxkBaojiadanDjService baojiadanDjService) {
        this.baojiadanDjService = baojiadanDjService;
    }

    @GetMapping("baojiadan/{dataId}")
    public ResponseEvent baojiadanResult(@PathVariable String dataId) {
        try {
            return RestResponse.success(
                    baojiadanDjService.getSuccess(dataId));
        } catch (CrmApiException e) {
            return RestResponse.failure(e);
        }
    }
}
