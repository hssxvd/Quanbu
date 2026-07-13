package com.qst.crop.dao;

import com.qst.crop.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao{

    List<Order> selectAll();

    List<Order> selectAllGoods(Order order);

    List<Order> selectAllNeeds(Order order);

    List<Order> selectByKeys(Order order);

    Order selectByPrimaryKey(@Param("orderId") Integer id);

}
