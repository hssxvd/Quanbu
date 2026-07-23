package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.OrderComment;
import com.qst.crop.model.MyPurchase;
import com.qst.crop.service.OrderCommentService;
import com.qst.crop.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/comment")
@Tag(name = "订单评价接口")
public class OrderCommentController {

    @Autowired
    private OrderCommentService orderCommentService;

    @Autowired
    private PurchaseService purchaseService;

    @Operation(summary = "添加评价")
    @PostMapping("/add")
    public Result<Void> addComment(@RequestBody OrderComment comment) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String buyerName = principal.getUsername();

        // 验证订单是否属于当前用户
        List<MyPurchase> purchases = purchaseService.selectBuys();
        MyPurchase target = null;
        for (MyPurchase purchase : purchases) {
            if (purchase.getPurchaseId().equals(comment.getPurchaseId())) {
                target = purchase;
                break;
            }
        }
        if (target == null) {
            return new Result<>(false, StatusCode.ERROR, "订单不存在或不属于您");
        }

        // 验证订单状态是否为已收货
        if (target.getPurchaseStatus() == null || target.getPurchaseStatus() != 3) {
            return new Result<>(false, StatusCode.ERROR, "订单状态异常，无法评价");
        }

        // 检查是否已评价
        OrderComment existingComment = orderCommentService.getByPurchaseId(comment.getPurchaseId());
        if (existingComment != null) {
            return new Result<>(false, StatusCode.ERROR, "该订单已评价");
        }

        // 验证评分
        if (comment.getRating() == null || comment.getRating() < 1 || comment.getRating() > 5) {
            return new Result<>(false, StatusCode.ERROR, "评分必须在1-5之间");
        }

        // 验证评价内容
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            return new Result<>(false, StatusCode.ERROR, "评价内容不能为空");
        }

        comment.setBuyerName(buyerName);
        comment.setSellerName(target.getSellerName());
        comment.setOrderId(target.getOrderId());
        comment.setCreateTime(new Date());

        orderCommentService.addComment(comment);
        return new Result<>(true, StatusCode.OK, "评价成功");
    }

    @Operation(summary = "查询订单评价")
    @GetMapping("/purchase/{purchaseId}")
    public Result<OrderComment> getByPurchaseId(@PathVariable Integer purchaseId) {
        OrderComment comment = orderCommentService.getByPurchaseId(purchaseId);
        return new Result<>(true, StatusCode.OK, "查询成功", comment);
    }

    @Operation(summary = "查询商品评价列表")
    @GetMapping("/order/{orderId}")
    public Result<List<OrderComment>> getByOrderId(@PathVariable Integer orderId) {
        List<OrderComment> comments = orderCommentService.getByOrderId(orderId);
        return new Result<>(true, StatusCode.OK, "查询成功", comments);
    }

    @Operation(summary = "查询卖家所有评价")
    @GetMapping("/seller/{sellerName}")
    public Result<List<OrderComment>> getBySellerName(@PathVariable String sellerName) {
        List<OrderComment> comments = orderCommentService.getBySellerName(sellerName);
        return new Result<>(true, StatusCode.OK, "查询成功", comments);
    }

    @Operation(summary = "查询卖家平均评分")
    @GetMapping("/rating/{sellerName}")
    public Result<Double> getAvgRating(@PathVariable String sellerName) {
        Double avgRating = orderCommentService.getAvgRatingBySellerName(sellerName);
        return new Result<>(true, StatusCode.OK, "查询成功", avgRating);
    }
}