package com.qst.crop.service;

import com.qst.crop.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    void add(Purchase purchase);

    Purchase selectNewPurchaseId(String ownName);
}