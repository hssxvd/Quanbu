package com.qst.crop.service;

import com.qst.crop.entity.SellPurchase;

import java.util.List;

public interface SellPurchaseService {
    void add(SellPurchase sellPurchase);
    List<SellPurchase> selectByName();
}