package com.qst.crop.dao;

import com.qst.crop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    User selectByPrimaryKey(@Param("userName") String userName);

}