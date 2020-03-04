package com.chylee.fxiaoke.controller.fxk;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.service.FxkDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fxiaoke/data")
public class FxkDataController {
    private final FxkDataService dataService;

    public FxkDataController(FxkDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("test")
    public ResponseEvent msg() {
        return dataService.sendTestMessage();
    }

    @GetMapping("get")
    public ResponseEvent getV2(String api, String id, boolean custom)  {
        if (api == null || id == null)
            return new ResponseEvent("参数不足");
        return dataService.getV2(api, id, custom);
    }

    @GetMapping("query")
    public ResponseEvent queryV2(String api, String name, String value, boolean custom) {
        if (api == null || name == null || value == null)
            return new ResponseEvent("参数不足");
        return dataService.queryV2(api, name, value, custom);
    }

}