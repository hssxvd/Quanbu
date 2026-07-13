package com.qst.crop.service;

import com.qst.crop.model.ShoppingModel;

import java.util.List;

public interface ShoppingcartService {

    void addToCart(Integer orderId);

    boolean delete(Integer shoppingId);

    boolean updateCount(Integer shoppingId, Integer count);

    List<ShoppingModel> selectByUsername();

}
