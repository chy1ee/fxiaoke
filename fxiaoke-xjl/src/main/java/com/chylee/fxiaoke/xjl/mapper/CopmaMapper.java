package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Copma;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

public interface CopmaMapper {
    @Select("select max(substring(MA001, 4, 6)) from COPMA where MA001 <> '21X999' and MA001 like '__${zm}___'")
    String khbm(@Param("zm") String zm);

    @Select("select MA101 from COPMA where MA001 = #{khbh}")
    BigDecimal loadShuilv(String khbh);

    void insert(Copma copma);
}
