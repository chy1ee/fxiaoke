package com.chylee.fxiaoke.controller.sys;

import com.chylee.fxiaoke.core.event.sys.DashBoardRespEvent;
import com.chylee.fxiaoke.core.service.DashBoardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/dashboard")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    public DashBoardController(DashBoardService dashBoardService) {
        this.dashBoardService = dashBoardService;
    }

    @GetMapping
    public DashBoardRespEvent info() {
        return dashBoardService.info();
    }
}
