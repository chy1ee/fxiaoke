package com.chylee.fxiaoke.xjl.mapper;

import com.chylee.fxiaoke.xjl.model.Coptc;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CoptcMapper {
    @Select("select TC001,TC002 from COPTC WHERE ((CREATE_DATE >= #{startTime} AND CREATE_DATE < #{endTime}) " +
            "OR (MODI_DATE >= #{startTime} AND MODI_DATE < #{endTime})) AND TC027 = 'Y' AND EXISTS(" +
            "SELECT NULL FROM COPTD WHERE TD018 LIKE '02%' AND TD001 = TC001 AND TD002 = TC002)")
    List<Coptc> list(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("select * from COPTC where TC001=#{db} and TC002=#{dh}")
    Coptc loadByDbAndDh(@Param("db") String db, @Param("dh") String dh);
}
