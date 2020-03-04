package com.chylee.fxiaoke.controller.quartz;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.common.event.support.Page;
import com.chylee.fxiaoke.core.model.QrtzJob;
import com.chylee.fxiaoke.core.service.QrtzJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartz/job")
public class QrtzJobController {
    private static Logger logger = LoggerFactory.getLogger(QrtzJobController.class);

    private final QrtzJobService qrtzJobService;

    public QrtzJobController(QrtzJobService qrtzJobService) {
        this.qrtzJobService = qrtzJobService;
    }

    @PostMapping("delete")
    public ResponseEvent delete(@RequestParam int id) {
        try {
            qrtzJobService.deleteJob(id);
            return new ResponseEvent();
        }
        catch(Exception e) {
            return RestResponse.failure(e);
        }
    }

    @PostMapping("status")
    public ResponseEvent status(@RequestParam int id, @RequestParam byte status) {
        try {
            qrtzJobService.toggleStatus(id, status);
            return new ResponseEvent();
        }
        catch(Exception e) {
            return RestResponse.failure(e);
        }
    }

    @PostMapping("trigger")
    public ResponseEvent trigger(@RequestParam int id) {
        if(qrtzJobService.triggerJob(id))
            return RestResponse.success(null);
        else
            return RestResponse.failure(-1, "执行失败");
    }

    @GetMapping
    public ResponseEvent list(String name, Integer page) {
        if(name != null) {
            String nameToUse = name.trim();
            if(nameToUse.length() > 0) {
                return RestResponse.success(qrtzJobService.list(name));
            }
        }

        int pageToUse = page == null ? 1 : page;
        return RestResponse.success(qrtzJobService.listJobByPage(new Page(pageToUse, 10)));
    }

    @PostMapping
    public ResponseEvent save(QrtzJob qrtzJob) {
        ResponseEvent respEvent;
        try {
            respEvent = RestResponse.success(qrtzJobService.saveJob(qrtzJob));
        }
        catch(Exception e) {
            logger.error("保存任务信息失败", e);
            respEvent = RestResponse.failure(e);
        }
        return respEvent;
    }
}
