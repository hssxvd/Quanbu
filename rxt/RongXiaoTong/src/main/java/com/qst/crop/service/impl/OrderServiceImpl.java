package com.qst.crop.service.impl;

import com.qst.crop.dao.OrderDao;
import com.qst.crop.entity.Order;
import com.qst.crop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> searchByTypeAndPage(String type, Integer pageNum, Integer pageSize) {
        return orderDao.selectByTypeAndPage(type, pageNum, pageSize);
    }

    @Override
    public List<Order> selectByOwnNameAndType(String ownName, String type) {
        return orderDao.selectByOwnNameAndType(ownName, type);
    }

    @Override
    public List<Order> searchByCondition(String ownName, String title, String type) {
        return orderDao.searchByCondition(ownName, title, type);
    }

    @Override
    public void addOrder(Order order) {
        orderDao.insertSelective(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.updateByPrimaryKeySelective(order);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderDao.deleteByPrimaryKey(orderId);
    }

    @Override
    public Order selectById(Integer orderId) {
        return orderDao.selectById(orderId);
    }

    @Override
    public List<Order> searchMyNeedsByKeys(String ownName, String keys, Integer pageNum, Integer pageSize) {
        return orderDao.searchMyNeedsByKeys(ownName, keys, pageNum, pageSize);
    }

    @Override
    public void takeDown(String orderId) {
        orderDao.takeDown(orderId);
    }

    @Override
    public void takeUp(String orderId) {
        orderDao.takeUp(orderId);
    }

    @Override
    public boolean hasOrdersByOwnName(String ownName) {
        Order order = new Order();
        order.setOwnName(ownName);
        List<Order> orders = orderDao.selectByExample(order);
        return orders != null && !orders.isEmpty();
    }
}