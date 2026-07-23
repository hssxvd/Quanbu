package com.qst.crop.service.impl;

import com.qst.crop.dao.OrderCommentDao;
import com.qst.crop.entity.OrderComment;
import com.qst.crop.service.OrderCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderCommentServiceImpl implements OrderCommentService {

    @Autowired
    private OrderCommentDao orderCommentDao;

    @Override
    public void addComment(OrderComment comment) {
        orderCommentDao.insert(comment);
    }

    @Override
    public OrderComment getByPurchaseId(Integer purchaseId) {
        return orderCommentDao.selectByPurchaseId(purchaseId);
    }

    @Override
    public List<OrderComment> getByOrderId(Integer orderId) {
        return orderCommentDao.selectByOrderId(orderId);
    }

    @Override
    public List<OrderComment> getBySellerName(String sellerName) {
        return orderCommentDao.selectBySellerName(sellerName);
    }

    @Override
    public Double getAvgRatingBySellerName(String sellerName) {
        Double avg = orderCommentDao.selectAvgRatingBySellerName(sellerName);
        return avg != null ? Math.round(avg * 10) / 10.0 : 0.0;
    }
}