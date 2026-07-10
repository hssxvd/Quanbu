package com.qst.crop.dao;

import com.qst.crop.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User selectByPrimaryKey(String userName);

}