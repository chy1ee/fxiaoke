package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.common.exception.ServiceException;
import com.chylee.fxiaoke.core.mapper.SysUserMapper;
import com.chylee.fxiaoke.core.model.SysUser;
import com.chylee.fxiaoke.core.service.SysUserService;
import com.chylee.fxiaoke.security.core.security.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    public SysUserServiceImpl(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void password(int userId, String oldPassword, String newPassword) throws ServiceException {
        SysUser user = userMapper.selectById(userId);
        if(user == null)
            throw new ServiceException("用户不存在");

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new ServiceException("旧密码错误");

        SysUser ToUpdate = new SysUser();
        ToUpdate.setId(userId);
        ToUpdate.setPassword(passwordEncoder.encode(newPassword));

        try {
            userMapper.updateById(ToUpdate);
        }
        catch(Exception e) {
            this.logger.error("修改密码失败", e);
            throw new ServiceException("保存新密码时发生错误");
        }
    }
}
