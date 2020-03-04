package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.common.exception.ServiceException;

public interface SysUserService {
    void password(int userId, String oldPassword, String newPassword) throws ServiceException;
}
