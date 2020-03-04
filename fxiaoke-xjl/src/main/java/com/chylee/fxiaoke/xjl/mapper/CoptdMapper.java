package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Coptd;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CoptdMapper {
    @Select("select * from COPTC where TC001=#{db} and TC002=#{dh}")
    List<Coptd> listByDbAndDh(@Param("db") String db, @Param("dh") String dh);
}
