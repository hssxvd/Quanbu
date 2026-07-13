package com.qst.crop.service.impl;

import com.qst.crop.dao.SellPurchaseDao;
import com.qst.crop.entity.SellPurchase;
import com.qst.crop.service.SellPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellPurchaseServiceImpl implements SellPurchaseService {
    @Autowired
    private SellPurchaseDao sellPurchaseDao;

    @Override
    public void add(SellPurchase sellPurchase) {
        sellPurchaseDao.insertSelective(sellPurchase);
    }
}