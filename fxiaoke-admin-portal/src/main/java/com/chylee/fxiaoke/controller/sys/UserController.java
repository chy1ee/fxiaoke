package com.chylee.fxiaoke.controller.sys;

import com.chylee.fxiaoke.common.event.ResponseEvent;
import com.chylee.fxiaoke.common.event.RestResponse;
import com.chylee.fxiaoke.common.exception.ServiceException;
import com.chylee.fxiaoke.core.event.UserInfoRespEvent;
import com.chylee.fxiaoke.core.service.SysUserService;
import com.chylee.fxiaoke.security.core.security.SecurityContextHolder;
import com.chylee.fxiaoke.security.core.security.User;
import com.chylee.fxiaoke.security.core.security.UserDetails;
import com.chylee.fxiaoke.security.core.security.UserDetailsCacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sys/user")
public class UserController {

    private final UserDetailsCacher userDetailsCacher;

    private final SysUserService sysUserService;

    public UserController(UserDetailsCacher userDetailsCacher, SysUserService sysUserService) {
        this.userDetailsCacher = userDetailsCacher;
        this.sysUserService = sysUserService;
    }

    @GetMapping("info")
    public ResponseEvent info(String token) {
        UserDetails user = (UserDetails) userDetailsCacher.get(token, null);
        if(user == null)
            return RestResponse.failure(50014, "Token已过期");
        else {
            UserInfoRespEvent respEvent = new UserInfoRespEvent();
            respEvent.setName(user.getUsername());
            return respEvent;
        }
    }

    @PostMapping("password")
    public ResponseEvent password(String oldPassword, String newPassword) {
        User user = SecurityContextHolder.getContext().getUser();

        if(oldPassword == null || newPassword == null)
            return new ResponseEvent("旧密码和新密码都不能为空");

        try {
            this.sysUserService.password(user.getId(), oldPassword, newPassword);
        }
        catch(ServiceException e) {
            return new ResponseEvent(e.getMessage());
        }

        return new ResponseEvent();
    }
}
