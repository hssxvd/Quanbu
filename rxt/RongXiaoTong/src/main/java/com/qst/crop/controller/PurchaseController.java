package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Purchase;
import com.qst.crop.entity.PurchaseDetail;
import com.qst.crop.entity.SellPurchase;
import com.qst.crop.model.ShoppingModel;
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

    @Operation(summary = "提交订单")
    @PostMapping("/commitOrder/{addId}/{tMoney}")
    @Transactional
    public Result commitOrder(@PathVariable("addId") Integer addId,
                              @PathVariable("tMoney") String tMoney,
                              @RequestBody List<ShoppingModel> shoppingModelList) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();

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
}