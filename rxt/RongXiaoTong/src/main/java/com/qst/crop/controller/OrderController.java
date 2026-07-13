package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.model.MyPurchase;
import com.qst.crop.service.PurchaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "订单管理")
public class OrderController {

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/selectBuys")
    public Result<List<MyPurchase>> selectBuys() {
        List<MyPurchase> purchases = purchaseService.selectBuys();
        return new Result<>(true, 20000, "查询成功", purchases);
    }
}
