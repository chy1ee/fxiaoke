package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Copth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CopthMapper {
    @Select("SELECT TG003， TG006, TH017, TD017, TD018 FROM COPTG, COPTH, COPTD  WHERE TG001 = #{db} AND TG002 = #{dh} " +
            "AND TH001 = TG001 AND TH002 = TG002 AND TD001 = TH014 AND TD002 = TH015 AND TD003 = TH016")
    List<Copth> list(@Param("db") String db, @Param("dh") String dh);
}
