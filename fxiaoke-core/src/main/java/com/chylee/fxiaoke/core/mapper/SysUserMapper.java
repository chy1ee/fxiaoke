package com.chylee.fxiaoke.core.mapper;
import com.chylee.fxiaoke.core.model.SysUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SysUserMapper {
    @Select("select id,name,nickname,password from sys_user where id = #{id}")
    SysUser selectById(int id);

    @Select("select id,name,nickname,password from sys_user where name=#{name}")
    SysUser selectByName(String name);

    @Update("update sys_user set password=#{password} where id=#{id}")
    void updateById(SysUser sysUser);
}
