package com.qst.crop.dao;

import com.qst.crop.entity.OrderSpec;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderSpecDao {

    int insert(OrderSpec record);

    int updateByPrimaryKey(OrderSpec record);

    int deleteByPrimaryKey(@Param("specId") Integer specId);

    int deleteByOrderId(@Param("orderId") Integer orderId);

    OrderSpec selectByPrimaryKey(@Param("specId") Integer specId);

    List<OrderSpec> selectByOrderId(@Param("orderId") Integer orderId);

    int updateStock(@Param("specId") Integer specId, @Param("stock") Integer stock);

    int increaseSales(@Param("specId") Integer specId, @Param("count") Integer count);
}