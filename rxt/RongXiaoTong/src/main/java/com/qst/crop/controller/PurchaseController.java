package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Order;
import com.qst.crop.entity.OrderSpec;
import com.qst.crop.entity.Purchase;
import com.qst.crop.entity.PurchaseDetail;
import com.qst.crop.entity.SellPurchase;
import com.qst.crop.model.MyPurchase;
import com.qst.crop.model.MySellPurchase;
import com.qst.crop.model.ShoppingModel;
import com.qst.crop.service.OrderService;
import com.qst.crop.service.OrderSpecService;
import com.qst.crop.service.PurchaseDetailService;
import com.qst.crop.service.PurchaseService;
import com.qst.crop.service.SellPurchaseService;
import com.qst.crop.service.ShoppingcartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/purchase")
@Tag(name = "采购订单接口")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private SellPurchaseService sellPurchaseService;

    @Autowired
    private ShoppingcartService shoppingcartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderSpecService orderSpecService;

    @Operation(summary = "提交订单")
    @PostMapping("/commitOrder/{addId}/{tMoney}")
    @Transactional
    public Result commitOrder(@PathVariable("addId") Integer addId,
                              @PathVariable("tMoney") String tMoney,
                              @RequestBody List<ShoppingModel> shoppingModelList) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        if (shoppingModelList != null && !shoppingModelList.isEmpty()) {
            for (ShoppingModel shoppingModel : shoppingModelList) {
                Order order = orderService.selectById(shoppingModel.getOrderId());
                if (order != null && name.equals(order.getOwnName())) {
                    return new Result(false, StatusCode.ERROR, "不能购买自己发布的商品：" + order.getTitle());
                }
            }
        }

        Purchase purchase = new Purchase();
        purchase.setPurchaseType(1);
        purchase.setOwnName(name);
        purchase.setAddress(addId.toString());
        purchase.setPurchaseStatus(1);
        purchase.setTotalPrice(new BigDecimal(tMoney));
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());

        purchaseService.add(purchase);

        Purchase purchaseGetId = purchaseService.selectNewPurchaseId(name);
        Integer purchaseId = purchaseGetId.getPurchaseId();

        if (shoppingModelList != null && shoppingModelList.size() > 0) {
            for (ShoppingModel shoppingModel : shoppingModelList) {
                PurchaseDetail purchaseDetail = new PurchaseDetail();
                purchaseDetail.setCount(shoppingModel.getCount());
                purchaseDetail.setOrderId(shoppingModel.getOrderId());
                purchaseDetail.setPurchaseId(purchaseId);
                purchaseDetail.setSumPrice(new BigDecimal(Double.parseDouble(shoppingModel.getPrice()) * shoppingModel.getCount()));
                purchaseDetail.setUninPrice(new BigDecimal(shoppingModel.getPrice()));
                purchaseDetailService.add(purchaseDetail);

                SellPurchase sellPurchase = new SellPurchase();
                sellPurchase.setOrderId(shoppingModel.getOrderId());
                sellPurchase.setAddress(addId.toString());
                sellPurchase.setOwnName(shoppingModel.getOwnName());
                sellPurchase.setPurchaseId(purchaseId);
                sellPurchase.setPurchaseStatus(1);
                sellPurchase.setPurchaseType(2);
                sellPurchase.setSumPrice(new BigDecimal(Double.parseDouble(shoppingModel.getPrice()) * shoppingModel.getCount()));
                sellPurchase.setUninPricee(new BigDecimal(shoppingModel.getPrice()));
                sellPurchase.setCreateTime(new Date());
                sellPurchase.setUpdateTime(new Date());
                sellPurchaseService.add(sellPurchase);

                shoppingcartService.delete(shoppingModel.getShoppingId());
            }
        }

        return new Result(true, StatusCode.OK, "提交成功");
    }

    @Operation(summary = "直接购买")
    @PostMapping("/directBuy/{orderId}")
    @Transactional
    public Result directBuy(@PathVariable("orderId") Integer orderId, @RequestParam(required = false) Integer specId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        Order order = orderService.selectById(orderId);
        if (order == null) {
            return new Result(false, StatusCode.ERROR, "商品不存在");
        }
        if (name.equals(order.getOwnName())) {
            return new Result(false, StatusCode.ERROR, "不能购买自己发布的商品");
        }

        // 解析规格和价格
        BigDecimal finalPrice = order.getPrice();
        Integer realSpecId = null;
        if (specId != null) {
            OrderSpec spec = orderSpecService.getById(specId);
            if (spec != null && spec.getOrderId().equals(orderId)) {
                if (spec.getSpecStock() == null || spec.getSpecStock() <= 0) {
                    return new Result(false, StatusCode.ERROR, "该规格库存不足");
                }
                finalPrice = spec.getSpecPrice();
                realSpecId = specId;
            }
        }

        Purchase purchase = new Purchase();
        purchase.setPurchaseType(1);
        purchase.setOwnName(name);
        purchase.setAddress("");
        purchase.setPurchaseStatus(1);
        purchase.setTotalPrice(finalPrice);
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
        purchaseService.add(purchase);

        Purchase purchaseGetId = purchaseService.selectNewPurchaseId(name);
        Integer purchaseId = purchaseGetId.getPurchaseId();

        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setCount(1);
        purchaseDetail.setOrderId(orderId);
        purchaseDetail.setPurchaseId(purchaseId);
        purchaseDetail.setSumPrice(finalPrice);
        purchaseDetail.setUninPrice(finalPrice);
        purchaseDetailService.add(purchaseDetail);

        SellPurchase sellPurchase = new SellPurchase();
        sellPurchase.setOrderId(orderId);
        sellPurchase.setAddress("");
        sellPurchase.setOwnName(order.getOwnName());
        sellPurchase.setPurchaseId(purchaseId);
        sellPurchase.setPurchaseStatus(1);
        sellPurchase.setPurchaseType(2);
        sellPurchase.setSumPrice(finalPrice);
        sellPurchase.setUninPricee(finalPrice);
        sellPurchase.setCreateTime(new Date());
        sellPurchase.setUpdateTime(new Date());
        sellPurchaseService.add(sellPurchase);

        // 扣减规格库存
        if (realSpecId != null) {
            OrderSpec spec = orderSpecService.getById(realSpecId);
            if (spec != null && spec.getSpecStock() != null) {
                orderSpecService.updateStock(realSpecId, spec.getSpecStock() - 1);
            }
        }

        return new Result(true, StatusCode.OK, "购买成功");
    }

    @Operation(summary = "查询我的买入订单")
    @GetMapping("/buys")
    public Result<List<MyPurchase>> selectBuys() {
        List<MyPurchase> purchases = purchaseService.selectBuys();
        return new Result<>(true, StatusCode.OK, "查询成功", purchases);
    }

    @Operation(summary = "查询我的卖出订单")
    @GetMapping("/sells")
    public Result<List<MySellPurchase>> selectSells() {
        List<MySellPurchase> sells = sellPurchaseService.selectByOwnName();
        return new Result<>(true, StatusCode.OK, "查询成功", sells);
    }

    @Operation(summary = "卖家发货")
    @PostMapping("/deliver/{purchaseId}")
    @Transactional
    public Result deliver(@PathVariable("purchaseId") Integer purchaseId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        List<MySellPurchase> sells = sellPurchaseService.selectByOwnName();
        MySellPurchase target = null;
        for (MySellPurchase sell : sells) {
            if (sell.getPurchaseId().equals(purchaseId)) {
                target = sell;
                break;
            }
        }
        if (target == null) {
            return new Result(false, StatusCode.ERROR, "订单不存在或不属于您");
        }
        if (target.getPurchaseStatus() != null && target.getPurchaseStatus() == 2) {
            return new Result(false, StatusCode.ERROR, "该订单已发货");
        }

        sellPurchaseService.updateStatus(purchaseId, 2);
        purchaseService.updateStatus(purchaseId, 2);
        return new Result(true, StatusCode.OK, "发货成功");
    }

    @Operation(summary = "买家确认收货")
    @PostMapping("/confirm/{purchaseId}")
    @Transactional
    public Result confirmReceive(@PathVariable("purchaseId") Integer purchaseId) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

        List<MyPurchase> purchases = purchaseService.selectBuys();
        MyPurchase target = null;
        for (MyPurchase purchase : purchases) {
            if (purchase.getPurchaseId().equals(purchaseId)) {
                target = purchase;
                break;
            }
        }
        if (target == null) {
            return new Result(false, StatusCode.ERROR, "订单不存在或不属于您");
        }
        if (target.getPurchaseStatus() == null || target.getPurchaseStatus() != 2) {
            return new Result(false, StatusCode.ERROR, "订单状态异常，无法确认收货");
        }

        purchaseService.updateStatus(purchaseId, 3);
        sellPurchaseService.updateStatus(purchaseId, 3);
        return new Result(true, StatusCode.OK, "确认收货成功");
    }
}