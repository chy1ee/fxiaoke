package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Coptg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CoptgMapper {
    List<Coptg> list(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
