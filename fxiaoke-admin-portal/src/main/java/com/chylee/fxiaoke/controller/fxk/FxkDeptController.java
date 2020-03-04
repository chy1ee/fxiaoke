package com.chylee.fxiaoke.controller.fxk;

import com.chylee.fxiaoke.common.exception.AccessTokenException;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptListRespEvent;
import com.chylee.fxiaoke.core.event.fxiaoke.dept.DeptUserListRespEvent;
import com.chylee.fxiaoke.core.service.FXKDeptService;
import com.chylee.fxiaoke.common.api.Constants;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fxiaoke/dept")
public class FxkDeptController {

    private final FXKDeptService deptService;

    public FxkDeptController(FXKDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping()
    public DeptListRespEvent deptList() {
        DeptListRespEvent result;
        try {
            result = this.deptService.getDeptList();
        }
        catch(AccessTokenException e) {
            result = new DeptListRespEvent();
            result.setErrorCode(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.code);
            result.setErrorMessage(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.msg);
        }

        return result;
    }

    @GetMapping("{deptId}")
    public DeptUserListRespEvent deptUserList(@PathVariable int deptId) {
        DeptUserListRespEvent result;

        try {
            result = this.deptService.getDeptUserList(deptId, true);
        }
        catch(AccessTokenException e) {
            result = new DeptUserListRespEvent();
            result.setErrorCode(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.code);
            result.setErrorMessage(Constants.interfaceResponseCode.FXK_CORP_ACCESS_TOKEN_EXPIRED.msg);
        }

        return result;
    }

}
