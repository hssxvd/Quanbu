package com.qst.crop.service;

import com.qst.crop.entity.SellPurchase;
import com.qst.crop.model.MySellPurchase;

import java.util.List;

public interface SellPurchaseService {
    void add(SellPurchase sellPurchase);
    List<SellPurchase> selectByName();
    List<MySellPurchase> selectByOwnName();

    void updateStatus(Integer purchaseId, Integer purchaseStatus);
}