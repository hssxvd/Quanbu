package com.qst.crop.service.impl;

import com.qst.crop.dao.PurchaseDao;
import com.qst.crop.entity.Purchase;
import com.qst.crop.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseDao purchaseDao;

    @Override
    public void add(Purchase purchase) {
        purchaseDao.insertSelective(purchase);
    }

    @Override
    public Purchase selectNewPurchaseId(String ownName) {
        Purchase purchase = purchaseDao.selectNewPurchaseId(ownName);
        return purchase;
    }
}