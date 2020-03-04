package com.chylee.fxiaoke.common.mapper;

import com.chylee.fxiaoke.common.model.FxkReport;
import org.apache.ibatis.annotations.Insert;

public interface FxkReportMapper {
    @Insert("insert into fxk_report(type,no,owner,error) value(#{type},#{no},#{owner},#{error})")
    void insert(FxkReport fxkReport);
}
