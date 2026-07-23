package com.qst.crop.service.impl;

import com.qst.crop.dao.PurchaseDao;
import com.qst.crop.entity.Purchase;
import com.qst.crop.model.MyPurchase;
import com.qst.crop.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        return purchaseDao.selectNewPurchaseId(ownName);
    }

    @Override
    public List<MyPurchase> selectBuys() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        return purchaseDao.selectByPurchase(name);
    }

    @Override
    public void updateStatus(Integer purchaseId, Integer purchaseStatus) {
        purchaseDao.updateStatus(purchaseId, purchaseStatus);
    }
}
