package com.chylee.fxiaoke.controller.quartz;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.core.model.QrtzTrigger;
import com.chylee.fxiaoke.core.service.QrtzTriggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartz/trigger")
public class QrtzTriggerController {
    private static Logger logger = LoggerFactory.getLogger(QrtzTriggerController.class);

    private final QrtzTriggerService qrtzTriggerService;

    public QrtzTriggerController(QrtzTriggerService qrtzTriggerService) {
        this.qrtzTriggerService = qrtzTriggerService;
    }

    @PostMapping("delete")
    public ResponseEvent delete(@RequestParam int id) {
        try {
            qrtzTriggerService.deleteTrigger(id);
            return new ResponseEvent();
        }
        catch(Exception e) {
            return RestResponse.failure(e);
        }
    }

    @PostMapping("status")
    public ResponseEvent status(@RequestParam int id, @RequestParam byte status) {
        try {
            return RestResponse.success(qrtzTriggerService.toggleStatus(id, status));
        }
        catch(Exception e) {
            return RestResponse.failure(e);
        }
    }

    @GetMapping
    public ResponseEvent list(Integer page) {
        int pageToUse = page == null ? 1 : page;
        return RestResponse.success(qrtzTriggerService.listTriggerByPage(new Page(pageToUse, 10)));
    }

    @PostMapping
    public ResponseEvent save(QrtzTrigger qrtzTrigger) {
        ResponseEvent respEvent;
        try {
            respEvent = RestResponse.success(qrtzTriggerService.saveTrigger(qrtzTrigger));
        }
        catch(Exception e) {
            logger.error("保存任务信息失败", e);
            respEvent = RestResponse.failure(e);
        }
        return respEvent;
    }
}
