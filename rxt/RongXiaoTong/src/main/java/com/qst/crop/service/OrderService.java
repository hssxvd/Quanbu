package com.qst.crop.service;

import com.github.pagehelper.PageInfo;
import com.qst.crop.entity.Order;

public interface OrderService {
    PageInfo<Order> selectAll(Integer pageNum);

    PageInfo<Order> selectAllGoods(Integer pageNum);

    PageInfo<Order> selectAllNeeds(Integer pageNum);

    PageInfo<Order> selectAllByKeys(String keys,Integer pageNum);

    PageInfo<Order> selectGoodsByKeys(String keys,Integer pageNum,String name);

    PageInfo<Order> selectNeedsByKeys(String keys,Integer pageNum,String name);

    Order selectById(Integer id);

}
