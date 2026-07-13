package com.qst.crop.controller;

import com.github.pagehelper.PageInfo;
import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Order;
import com.qst.crop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;


    //查询所有商品
    @Operation(summary = "查询所有商品")
    @GetMapping({"/All/{pageNum}", "/all/{pageNum}"})
    public Result<PageInfo> selectAll(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAll(pageNum);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }

    //查询所有商品（货源)商品
    @Operation(summary = "分页查询所有货源(商品)商品")
    @GetMapping("/goods/{pageNum}")
    public Result<PageInfo> selectAllGoods(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllGoods(pageNum);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }

    //查询所有需求
    @Operation(summary = "分页查询所有需求")
    @GetMapping("/needs/{pageNum}")
    public Result<PageInfo> selectAllNeeds(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllNeeds(pageNum);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }




    //根据id查询商品
    @Operation(summary = "根据id查询商品")
    @GetMapping("/selectById/{id}")
    public Result<Order> selectById(@PathVariable("id") Integer id) {
        Order order = orderService.selectById(id);
        return new Result<Order>(true, StatusCode.OK, "查询成功", order);

    }

    //根据id查询商品
    @Operation(summary = "根据id查询商品")
    @GetMapping("/selectById")
    public Result<Order> selectByIdByParam(@RequestParam("id") Integer id) {
        Order order = orderService.selectById(id);
        return new Result<Order>(true, StatusCode.OK, "查询成功", order);

    }


    //分页条件搜索商品（货源）商品
    @Operation(summary = "分页条件搜索商品（货源）订单")
    @GetMapping({
            "/searchGoodsByKeys/{keys}/{pageNum}",
            "/searchGoodsByKeys/{keys}/{pageNum}/",
            "/searchgoodsbykeys/{keys}/{pageNum}",
            "/searchgoodsbykeys/{keys}/{pageNum}/"
    })
    public Result<PageInfo> searchGoodsByKeys(@PathVariable("keys") String keys,@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectGoodsByKeys(keys,pageNum,null);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }

    //分页条件搜索商品（货源）商品
    @Operation(summary = "分页条件搜索商品（货源）订单")
    @GetMapping({
            "/searchGoodsByKeys",
            "/searchGoodsByKeys/",
            "/searchgoodsbykeys",
            "/searchgoodsbykeys/"
    })
    public Result<PageInfo> searchGoodsByKeysByParam(@RequestParam("keys") String keys,@RequestParam("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectGoodsByKeys(keys,pageNum,null);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }

    //分页条件搜索需求
    @Operation(summary = "分页条件搜索需求订单")
    @GetMapping("/searchNeedsByKeys/{keys}/{pageNum}")
    public Result<PageInfo> searchNeedsByKeys(@PathVariable("keys") String keys,@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectNeedsByKeys(keys,pageNum,null);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }

    //分页条件搜索需求
    @Operation(summary = "分页条件搜索需求订单")
    @GetMapping("/searchNeedsByKeys")
    public Result<PageInfo> searchNeedsByKeysByParam(@RequestParam("keys") String keys,@RequestParam("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectNeedsByKeys(keys,pageNum,null);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }



    //分页条件搜索所有商品
    @Operation(summary = "分页条件搜索所有商品")
    @GetMapping({
            "/searchAllByKeys/{keys}/{pageNum}",
            "/searchAllByKeys/{keys}/{pageNum}/",
            "/searchallbykeys/{keys}/{pageNum}",
            "/searchallbykeys/{keys}/{pageNum}/"
    })
    public Result<PageInfo> searchAllByKeys(@PathVariable("keys") String keys,@PathVariable("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllByKeys(keys,pageNum);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }

    //分页条件搜索所有商品
    @Operation(summary = "分页条件搜索所有商品")
    @GetMapping({
            "/searchAllByKeys",
            "/searchAllByKeys/",
            "/searchallbykeys",
            "/searchallbykeys/"
    })
    public Result<PageInfo> searchAllByKeysByParam(@RequestParam("keys") String keys,@RequestParam("pageNum") Integer pageNum) {
        PageInfo<Order> orders = orderService.selectAllByKeys(keys,pageNum);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", orders);
    }


}
