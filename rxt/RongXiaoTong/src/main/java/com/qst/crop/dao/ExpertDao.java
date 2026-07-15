package com.qst.crop.dao;

import com.qst.crop.entity.Expert;
import com.qst.crop.entity.ExpertUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExpertDao {

    List<Expert> selectAllExpert();

    List<Expert> selectAllByKeys(@Param("keys") String keys);

    List<ExpertUser> selectAllUserExpert();

    Expert selectByUserName(@Param("userName") String userName);

    int updateByPrimaryKeySelective(Expert expert);

    int deleteByPrimaryKey(@Param("userName") String userName);

}