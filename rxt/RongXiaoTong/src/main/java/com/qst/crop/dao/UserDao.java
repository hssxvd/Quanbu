package com.qst.crop.dao;

import com.qst.crop.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    User selectByPrimaryKey(@Param("userName") String userName);

    int insert(User user);

    int updateByPrimaryKeySelective(User user);

    int updatePassword(@Param("userName") String userName, @Param("password") String password);

    List<User> selectAll();

    int deleteByPrimaryKey(@Param("userName") String userName);

}