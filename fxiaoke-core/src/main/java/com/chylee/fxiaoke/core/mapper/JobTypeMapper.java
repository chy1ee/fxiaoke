package com.chylee.fxiaoke.core.mapper;

import com.chylee.fxiaoke.core.model.JobType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JobTypeMapper {

    @Select("select * from job_type order by id")
    List<JobType> listAll();

    @Select("select count(*) from job_type")
    int count();
}
