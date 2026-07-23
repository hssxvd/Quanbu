package com.qst.crop.service;

import com.qst.crop.entity.OrderSpec;

import java.util.List;

public interface OrderSpecService {

    void addSpec(OrderSpec spec);

    void updateSpec(OrderSpec spec);

    void deleteSpec(Integer specId);

    void deleteByOrderId(Integer orderId);

    OrderSpec getById(Integer specId);

    List<OrderSpec> getByOrderId(Integer orderId);

    void addSpecs(List<OrderSpec> specs);

    void updateStock(Integer specId, Integer stock);
}