package com.qst.crop.service.impl;

import com.qst.crop.dao.OrderSpecDao;
import com.qst.crop.entity.OrderSpec;
import com.qst.crop.service.OrderSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSpecServiceImpl implements OrderSpecService {

    @Autowired
    private OrderSpecDao orderSpecDao;

    @Override
    public void addSpec(OrderSpec spec) {
        orderSpecDao.insert(spec);
    }

    @Override
    public void updateSpec(OrderSpec spec) {
        orderSpecDao.updateByPrimaryKey(spec);
    }

    @Override
    public void deleteSpec(Integer specId) {
        orderSpecDao.deleteByPrimaryKey(specId);
    }

    @Override
    public void deleteByOrderId(Integer orderId) {
        orderSpecDao.deleteByOrderId(orderId);
    }

    @Override
    public OrderSpec getById(Integer specId) {
        return orderSpecDao.selectByPrimaryKey(specId);
    }

    @Override
    public List<OrderSpec> getByOrderId(Integer orderId) {
        return orderSpecDao.selectByOrderId(orderId);
    }

    @Override
    public void addSpecs(List<OrderSpec> specs) {
        if (specs != null) {
            for (OrderSpec spec : specs) {
                orderSpecDao.insert(spec);
            }
        }
    }

    @Override
    public void updateStock(Integer specId, Integer stock) {
        orderSpecDao.updateStock(specId, stock);
    }
}