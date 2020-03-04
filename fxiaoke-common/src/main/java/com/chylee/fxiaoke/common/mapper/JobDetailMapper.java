package com.chylee.fxiaoke.common.mapper;

import com.chylee.fxiaoke.common.model.JobDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface JobDetailMapper {
    @Select("select id,log_id,data_id,status,last_time,error from job_detail where id = #{id}")
    JobDetail selectById(int id);

    @Update("update job_detail set status=#{status},error=#{error},last_time=current_timestamp where id=#{id}")
    void updateStatusById(JobDetail detail);

    @Select("select count(*) from job_detail where status = -1")
    int errorCount();

    void insertBatch(@Param("queueList") List<JobDetail> jobQueueList);

    List<JobDetail> listByStatus0(@Param("pageSize") int pageSize);

    int count(@Param("logId") int logId, @Param("valid") int valid);

    List<JobDetail> listByLogId(@Param("logId") int logId, @Param("valid") int valid, @Param("offset") int offset, @Param("pageSize") int pageSize);
}
