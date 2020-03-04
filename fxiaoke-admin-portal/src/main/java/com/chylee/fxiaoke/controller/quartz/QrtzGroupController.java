package com.chylee.fxiaoke.controller.quartz;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.core.model.QrtzGroup;
import com.chylee.fxiaoke.core.service.QrtzGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("quartz/group")
public class QrtzGroupController {
    private final QrtzGroupService qrtzGroupService;

    public QrtzGroupController(QrtzGroupService qrtzGroupService) {
        this.qrtzGroupService = qrtzGroupService;
    }

    @GetMapping
    public ResponseEvent list(Byte status) {
        return RestResponse.success(qrtzGroupService.listGroup(status));
    }

    @PostMapping
    public ResponseEvent add(@Valid QrtzGroup qrtzGroup, BindingResult result) {
        if(result.hasErrors())
            return new ResponseEvent(-1, result.getFieldError().getDefaultMessage());

        qrtzGroup.setName(qrtzGroup.getName().trim());
        QrtzGroup group = qrtzGroupService.addGroup(qrtzGroup);
        return group == null ? RestResponse.failure(-1, "名称已存在") : RestResponse.success(group);
    }

    @DeleteMapping
    public ResponseEvent delete(int id) {
        qrtzGroupService.deleteGroup(id);
        return new ResponseEvent();
    }
}
