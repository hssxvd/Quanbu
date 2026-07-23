package com.qst.crop.service;

import com.qst.crop.entity.Purchase;
import com.qst.crop.model.MyPurchase;

import java.util.List;

public interface PurchaseService {

    void add(Purchase purchase);

    Purchase selectNewPurchaseId(String ownName);

    List<MyPurchase> selectBuys();

    void updateStatus(Integer purchaseId, Integer purchaseStatus);
}