package com.qst.crop.service;

import com.qst.crop.entity.OrderComment;

import java.util.List;

public interface OrderCommentService {

    void addComment(OrderComment comment);

    OrderComment getByPurchaseId(Integer purchaseId);

    List<OrderComment> getByOrderId(Integer orderId);

    List<OrderComment> getBySellerName(String sellerName);

    Double getAvgRatingBySellerName(String sellerName);
}