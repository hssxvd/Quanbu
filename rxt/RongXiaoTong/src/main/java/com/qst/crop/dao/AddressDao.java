package com.qst.crop.dao;

import com.qst.crop.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressDao {

    int insert(Address address);

    List<Address> selectByUserName(@Param("userName") String userName);

    Address selectByAddressId(@Param("addressId") Integer addressId);

    int updateByPrimaryKeySelective(Address address);

    int deleteByAddressId(@Param("addressId") Integer addressId);

    int clearDefault(@Param("userName") String userName);
}