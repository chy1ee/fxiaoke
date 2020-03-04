package com.chylee.fxiaoke.core.mapper;

import com.chylee.fxiaoke.core.model.QrtzGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface QrtzGroupMapper {
    @Select("select * from qrtz_group order by id")
    List<QrtzGroup> list();

    @Select("select * from qrtz_group where status = 1 order by id")
    List<QrtzGroup> list0();

    @Update("update qrtz_group set status = -1 where id = #{id}")
    void delete(Integer id);

    @Select("select * From qrtz_group where name = #{name}")
    QrtzGroup loadByName(String name);

    @Insert("insert into qrtz_group(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(QrtzGroup qrtzGroup);
}
