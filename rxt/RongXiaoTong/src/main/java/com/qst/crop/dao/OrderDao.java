package com.qst.crop.dao;

import com.qst.crop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    int deleteByPrimaryKey(Integer orderId);

    Order selectById(Integer orderId);

    List<Order> selectByExample(Order order);

    List<Order> selectByTypeAndPage(@Param("type") String type, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    List<Order> selectByOwnNameAndType(@Param("ownName") String ownName, @Param("type") String type);

    List<Order> searchByCondition(@Param("ownName") String ownName, @Param("title") String title, @Param("type") String type);
}