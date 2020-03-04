package com.chylee.fxiaoke.controller.sys;

import com.chylee.fxiaoke.common.event.DataRespEvent;
import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.config.ConfigAdminReqEvent;
import com.chylee.fxiaoke.common.event.config.ConfigApiReqEvent;
import com.chylee.fxiaoke.common.model.SysConfig;
import com.chylee.fxiaoke.common.service.ConfigApiService;
import com.chylee.fxiaoke.common.service.SysConfigService;
import com.chylee.fxiaoke.common.service.impl.ConfigAdminServiceImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sys/config")
public class SysConfigController {

    private final SysConfigService configService;
    private final ConfigAdminServiceImpl adminService;
    private final ConfigApiService apiService;

    public SysConfigController(SysConfigService configService, ConfigAdminServiceImpl adminService,
                               ConfigApiService apiService) {
        this.configService = configService;
        this.adminService = adminService;
        this.apiService = apiService;
    }

    @GetMapping("{type}")
    public DataRespEvent<SysConfig> config(@PathVariable int type) {
        DataRespEvent<SysConfig> respEvent = new DataRespEvent<>();
        SysConfig entity = this.configService.loadByType(type);
        if(entity == null)
            respEvent.setError(-1, "配置信息不存在");
        else
            respEvent.setData(entity);

        return respEvent;
    }

    @PostMapping("api")
    public ResponseEvent openApi(@Valid ConfigApiReqEvent reqEvent, BindingResult result) {
        if(result.hasErrors())
            return new ResponseEvent(result.getFieldError().getDefaultMessage());

        return apiService.saveConfig(reqEvent);
    }

    @PostMapping("report")
    public ResponseEvent report(@Valid ConfigAdminReqEvent reqEvent, BindingResult result) {
        if(result.hasErrors())
            return new ResponseEvent(result.getFieldError().getDefaultMessage());

        ResponseEvent respEvent = adminService.saveConfig(reqEvent);
        if (respEvent.isSuccess()) {
            adminService.cleanAdminOpenIds();
            adminService.cleanStatus();
        }

        return respEvent;
    }

}
