package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.entity.Order;
import com.qst.crop.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "个人货源管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/search/{type}/{pageNum}")
    public Result<List<Order>> searchByTypeAndPage(@PathVariable String type, @PathVariable Integer pageNum) {
        int pageSize = 10;
        List<Order> orders = orderService.searchByTypeAndPage(type, pageNum, pageSize);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/selectMyOrders/{type}")
    public Result<List<Order>> selectMyOrders(@PathVariable String type) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.selectByOwnNameAndType(ownName, type);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/searchByCondition")
    public Result<List<Order>> searchByCondition(@RequestParam(required = false) String title,
                                                 @RequestParam(required = false) String type) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.searchByCondition(ownName, title, type);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @PostMapping("/add")
    public Result<Void> addOrder(@RequestBody Order order) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setOwnName(principal.getUsername());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.addOrder(order);
        return new Result<>(true, 20000, "添加成功");
    }

    @PutMapping("/{orderId}")
    public Result<Void> updateOrder(@RequestBody Order order, @PathVariable Integer orderId) {
        order.setOrderId(orderId);
        order.setUpdateTime(new Date());
        orderService.updateOrder(order);
        return new Result<>(true, 20000, "更新成功");
    }

    @DeleteMapping("/{orderId}")
    public Result<Void> deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
        return new Result<>(true, 20000, "删除成功");
    }

    @GetMapping("/selectById/{orderId}")
    public Result<Order> selectById(@PathVariable Integer orderId) {
        Order order = orderService.selectById(orderId);
        return new Result<>(true, 20000, "查询成功", order);
    }

    @GetMapping("/goods/{pageNum}")
    public Result<List<Order>> selectGoods(@PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.selectByOwnNameAndType(ownName, "goods");
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/needs/{pageNum}")
    public Result<List<Order>> selectNeeds(@PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.selectByOwnNameAndType(ownName, "needs");
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/All/{pageNum}")
    public Result<List<Order>> selectAll(@PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.searchByCondition(ownName, null, null);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/searchGoodsByKeys/{keys}/{pageNum}")
    public Result<List<Order>> searchGoodsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.searchByCondition(ownName, keys, "goods");
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/searchNeedsByKeys/{keys}/{pageNum}")
    public Result<List<Order>> searchNeedsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.searchByCondition(ownName, keys, "needs");
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/searchAllByKeys/{keys}/{pageNum}")
    public Result<List<Order>> searchAllByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.searchByCondition(ownName, keys, null);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/searchMyNeedsByKeys/{keys}/{pageNum}")
    public Result<List<Order>> searchMyNeedsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        int pageSize = 10;
        List<Order> orders = orderService.searchMyNeedsByKeys(ownName, keys, pageNum, pageSize);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/selectSells")
    public Result<List<Order>> selectSells() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.selectByOwnNameAndType(ownName, "goods");
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @PutMapping("/takeDownOrder/{orderId}")
    public Result<Void> takeDownOrder(@PathVariable String orderId) {
        orderService.takeDown(orderId);
        return new Result<>(true, 20000, "下架成功");
    }

    @PutMapping("/takeUpOrder/{orderId}")
    public Result<Void> takeUpOrder(@PathVariable String orderId) {
        orderService.takeUp(orderId);
        return new Result<>(true, 20000, "上架成功");
    }

}
