package com.chylee.fxiaoke.controller.fxk;

import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.QueryRespEvent;
import com.chylee.fxiaoke.common.event.fxiaoke.crm.approval.State;
import com.chylee.fxiaoke.common.service.FxkApprovalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fxiaoke/approval")
public class FxkApprovalController {

    private final FxkApprovalService approvalService;

    public FxkApprovalController(FxkApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping
    public QueryRespEvent list() {
        return approvalService.query("apprN88C5U0RHN__crmappr", State.pass,
                1571215557300L, System.currentTimeMillis(), 20,1);
    }


}
