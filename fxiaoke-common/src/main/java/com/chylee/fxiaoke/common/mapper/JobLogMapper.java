package com.chylee.fxiaoke.common.mapper;

import com.chylee.fxiaoke.common.model.JobLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface JobLogMapper {

    @Select("select id,qrtz_id,type_id,start_time,end_time,count,message,success,fail from job_log where id=#{id}")
    JobLog selectById(int id);

    @Select("select max(id) from job_log where type_id = #{typeId}")
    Integer maxId(int typeId);

    @Update("update job_log set fail = fail -1 where id = #{id}")
    void updateFail(int id);

    @Select("select end_time,success,fail From job_log where count > 0 order by id desc limit 8")
    List<JobLog> lineChart();

    @Insert("insert into job_log(qrtz_id,type_id,start_time,end_time,count) values(#{qrtzId},#{typeId},#{startTime},#{endTime},#{count})")
    @Options(useGeneratedKeys = true, keyProperty="id", keyColumn="id")
    void insert(JobLog jobLog);

    int count(@Param("qrtzId") int qrtzId, @Param("valid") int valid);

    List<JobLog> listAllByPage(@Param("qrtzId") int qrtzId, @Param("valid") int valid, int offset, int pageSize);

    void updateById(JobLog jobLog);
}
