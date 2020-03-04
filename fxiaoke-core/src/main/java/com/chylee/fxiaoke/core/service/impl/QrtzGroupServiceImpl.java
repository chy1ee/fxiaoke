package com.chylee.fxiaoke.core.service.impl;

import com.chylee.fxiaoke.core.mapper.QrtzGroupMapper;
import com.chylee.fxiaoke.core.model.QrtzGroup;
import com.chylee.fxiaoke.core.service.QrtzGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QrtzGroupServiceImpl implements QrtzGroupService {
    @Autowired
    private QrtzGroupMapper qrtzGroupMapper;

    @Override
    public List<QrtzGroup> listGroup(Byte status) {
        return status != null && 1 == status ? qrtzGroupMapper.list0() : qrtzGroupMapper.list();
    }

    @Override
    public void deleteGroup(int id) {
        qrtzGroupMapper.delete(id);
    }

    @Override
    public QrtzGroup addGroup(QrtzGroup qrtzGroup) {
        QrtzGroup groupToCheck = qrtzGroupMapper.loadByName(qrtzGroup.getName());
        if(groupToCheck == null) {
            qrtzGroupMapper.insert(qrtzGroup);
            qrtzGroup.setStatus((byte) 1);
            return qrtzGroup;
        }
        else
            return null;
    }
}
