package com.chylee.fxiaoke.core.service;

import com.chylee.fxiaoke.core.model.QrtzGroup;

import java.util.List;

public interface QrtzGroupService {
    List<QrtzGroup> listGroup(Byte status);
    void deleteGroup(int id);
    QrtzGroup addGroup(QrtzGroup qrtzGrup);
}
