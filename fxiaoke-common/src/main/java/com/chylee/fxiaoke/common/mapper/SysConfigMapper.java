package com.chylee.fxiaoke.common.mapper;

import com.chylee.fxiaoke.common.model.SysConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysConfigMapper {
    @Select("select id,type,content from sys_config where type = #{type}")
    SysConfig loadByType(int type);

    @Insert("insert into sys_config(type,content) values(#{type},#{content}) ")
    void insert(SysConfig config);

    @Update("update sys_config set content=#{content} where id=#{id}")
    void updateById(SysConfig config);
}
