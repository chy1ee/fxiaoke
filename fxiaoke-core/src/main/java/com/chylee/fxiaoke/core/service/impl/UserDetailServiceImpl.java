package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.core.mapper.SysUserMapper;
import com.chylee.fxiaoke.core.model.SysUser;
import com.chylee.fxiaoke.security.core.security.User;
import com.chylee.fxiaoke.security.core.security.UserDetails;
import com.chylee.fxiaoke.security.core.security.UserDetailsService;
import com.chylee.fxiaoke.security.exception.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final SysUserMapper sysUserMapper;

    public UserDetailServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectByName(username);
        if (sysUser == null)
            throw new UsernameNotFoundException("用户不存在");

        return new UserDetails(sysUser.getId(), 0, sysUser.getName(), sysUser.getNickname(), sysUser.getPassword());
    }
}
