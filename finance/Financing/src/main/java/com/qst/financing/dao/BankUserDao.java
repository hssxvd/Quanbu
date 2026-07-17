package com.qst.financing.dao;

import com.qst.financing.entity.BankUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BankUserDao {

    BankUser selectByPrimaryKey(@Param("userName") String userName);

    int insert(BankUser bankUser);

    int updateByPrimaryKeySelective(BankUser bankUser);

    int updatePassword(@Param("userName") String userName, @Param("password") String password);

    List<BankUser> selectAll();

    int deleteByPrimaryKey(@Param("userName") String userName);

}