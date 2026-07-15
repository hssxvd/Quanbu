package com.qst.crop.service.impl;

import com.qst.crop.dao.PurchaseDetailDao;
import com.qst.crop.entity.PurchaseDetail;
import com.qst.crop.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    @Autowired
    private PurchaseDetailDao purchaseDetailDao;

    @Override
    public void add(PurchaseDetail purchaseDetail) {
        purchaseDetailDao.insertSelective(purchaseDetail);
    }
}