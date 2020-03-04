package com.chylee.fxiaoke.core.mapper;

import com.chylee.fxiaoke.core.model.QrtzLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface QrtzLogMapper {

    @Select("select count(*) from qrtz_log")
    int count();

    List<QrtzLog> listByPage(@Param("offset") int offset, @Param("pageSize") int pageSize);

    @Insert("insert into qrtz_log(job_id, job_group, job_name) values(#{jobId}, #{jobGroup}, #{jobName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(QrtzLog qrtzLog);

    void updateEndtimeById(@Param("id") int id, @Param("error") String error);
}
