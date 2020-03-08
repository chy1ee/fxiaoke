package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Copma;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CopmaMapper {
    @Select("select max(substring(MA001, 4, 6)) from COPMA where MA001 <> '21X999' and MA001 like '__${zm}___'")
    String khbm(@Param("zm") String zm);

    @Select("select MA001,MA002,MA003,MA005,MA006,MA007,MA008,MA016,MA023,MA027,MA085,MA101 from COPMA where MA001 = #{khbh}")
    Copma loadByKhbh(String khbh);

    void updateByKhbh(Copma copma);

    void insert(Copma copma);
}
