package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Acrtk;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcrtkMapper {
    Acrtk loadByDbAndDh(@Param("db") String db, @Param("dh") String dh);
    List<Acrtk> listSummary(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
