package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.model.ShoppingModel;
import com.qst.crop.service.ShoppingcartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Tag(name = "购物车接口")
public class CartController {
    @Autowired
    private ShoppingcartService shoppingcartService;

    @Operation(summary = "添加商品到购物车")
    @PostMapping("/add/{orderId}")
    public Result add(@PathVariable("orderId") Integer orderId) {
        shoppingcartService.addToCart(orderId);
        return new Result(true, StatusCode.OK, "添加商品到购物车成功");
    }

    @Operation(summary = "从购物车删除商品")
    @DeleteMapping("/delete/{shoppingId}")
    public Result delete(@PathVariable("shoppingId") Integer shoppingId) {
        boolean success = shoppingcartService.delete(shoppingId);
        if (!success) {
            return new Result(false, StatusCode.ERROR, "购物车记录不存在或无权操作");
        }
        return new Result(true, StatusCode.OK, "删除商品成功");
    }

    @Operation(summary = "展示购物车列表")
    @GetMapping("/show")
    public Result<List<ShoppingModel>> show() {
        List<ShoppingModel> shoppingModel = shoppingcartService.selectByUsername();
        return new Result<>(true, StatusCode.OK, "查询成功", shoppingModel);
    }

    @Operation(summary = "更新购物车数量")
    @PutMapping("/update/{shoppingId}/{count}")
    public Result update(@PathVariable("shoppingId") Integer shoppingId, @PathVariable("count") Integer count) {
        boolean success = shoppingcartService.updateCount(shoppingId, count);
        if (!success) {
            return new Result(false, StatusCode.ERROR, "更新失败，请检查数量或购物车记录是否存在");
        }
        return new Result(true, StatusCode.OK, "更新商品数量成功");
    }
}
