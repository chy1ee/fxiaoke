package com.chylee.fxiaoke.controller.quartz;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.core.service.QrtzLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz/log")
public class QrtzLogController {
    private final QrtzLogService qrtzLogService;


    public QrtzLogController(QrtzLogService qrtzLogService) {
        this.qrtzLogService = qrtzLogService;
    }

    @GetMapping
    public ResponseEvent list(Integer page) {
        int pageToUse = page == null || page < 1 ? 1 : page;
        return RestResponse.success(qrtzLogService.listLogByPage(new Page(pageToUse, 10)));
    }
}
