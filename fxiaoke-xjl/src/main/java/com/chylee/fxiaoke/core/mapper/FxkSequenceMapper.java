package com.chylee.fxiaoke.core.mapper;

import com.chylee.fxiaoke.core.model.FxkSequence;
import org.apache.ibatis.annotations.*;

public interface FxkSequenceMapper {
    @Select("select id,api,yf,xh,suffix,pattern,max,len from fxk_sequence where api=#{api} and yf=#{yf}")
    FxkSequence loadByApiAndYf(@Param("api") String api, @Param("yf") String yf);

    @Insert("insert into fxk_sequence(api,yf,xh,pattern,max,len) values(#{api},#{yf},#{xh},#{pattern},#{max},#{len})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(FxkSequence sequence);

    @Select("select * from fxk_sequence  where id=#{id}")
    FxkSequence selectById(int id);

    @Update("update fxk_sequence set xh=#{xh} where id=#{id}" )
    void updateById(FxkSequence sequence);
}
