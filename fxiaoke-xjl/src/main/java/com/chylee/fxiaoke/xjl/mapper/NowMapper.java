package com.chylee.fxiaoke.xjl.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface NowMapper {
    @Select("select getdate()")
    Date now();
}
