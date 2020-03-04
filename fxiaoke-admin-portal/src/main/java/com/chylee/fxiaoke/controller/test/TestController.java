package com.chylee.fxiaoke.controller.test;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.common.exception.CrmApiException;
import com.chylee.fxiaoke.service.TestAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    private final TestAccountService accountService;

    public TestController(TestAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("account")
    public ResponseEvent account() {
        try {
            return RestResponse.success(accountService.getAccounts());
        } catch (CrmApiException e) {
            e.printStackTrace();
            return RestResponse.failure(-1, e.getMessage());
        }
    }

    @GetMapping("error")
    public ResponseEvent account2() {
        accountService.updateAccount();
        return RestResponse.success("done");
    }
}
