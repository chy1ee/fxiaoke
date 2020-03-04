package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Acrtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AcrtlMapper {
    List<Acrtl> listDetail(@Param("db") String db, @Param("dh") String dh);
}
