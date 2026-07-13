package com.qst.crop.service;

import com.qst.crop.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> searchByTypeAndPage(String type, Integer pageNum, Integer pageSize);

    List<Order> selectByOwnNameAndType(String ownName, String type);

    List<Order> searchByCondition(String ownName, String title, String type);

    void addOrder(Order order);

    void updateOrder(Order order);

    void deleteOrder(Integer orderId);

    Order selectById(Integer orderId);
}