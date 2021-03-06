package com.chylee.fxiaoke.core.mapper;

import com.chylee.fxiaoke.core.model.QrtzTrigger;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QrtzTriggerMapper {

    int selectCount();

    List<QrtzTrigger> listPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    @Select("select * from qrtz_trigger where job_id > 0")
    List<QrtzTrigger> listAllByBindJob();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrtz_trigger
     *
     * @mbg.generated Thu Nov 28 17:10:09 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrtz_trigger
     *
     * @mbg.generated Thu Nov 28 17:10:09 CST 2019
     */
    int insert(QrtzTrigger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrtz_trigger
     *
     * @mbg.generated Thu Nov 28 17:10:09 CST 2019
     */
    int insertSelective(QrtzTrigger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrtz_trigger
     *
     * @mbg.generated Thu Nov 28 17:10:09 CST 2019
     */
    QrtzTrigger selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrtz_trigger
     *
     * @mbg.generated Thu Nov 28 17:10:09 CST 2019
     */
    int updateByPrimaryKeySelective(QrtzTrigger record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table qrtz_trigger
     *
     * @mbg.generated Thu Nov 28 17:10:09 CST 2019
     */
    int updateByPrimaryKey(QrtzTrigger record);
}