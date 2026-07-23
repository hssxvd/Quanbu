package com.qst.crop.dao;

import com.qst.crop.entity.OrderComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderCommentDao {

    int insert(OrderComment record);

    OrderComment selectByPurchaseId(@Param("purchaseId") Integer purchaseId);

    List<OrderComment> selectByOrderId(@Param("orderId") Integer orderId);

    List<OrderComment> selectBySellerName(@Param("sellerName") String sellerName);

    Double selectAvgRatingBySellerName(@Param("sellerName") String sellerName);
}