package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.dao.OrderDao;
import com.qst.crop.entity.Order;
import com.qst.crop.entity.OrderSpec;
import com.qst.crop.service.OrderService;
import com.qst.crop.service.OrderSpecService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Tag(name = "个人货源管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSpecService orderSpecService;

    @GetMapping("/search/{type}/{pageNum}")
    public Result<List<Order>> searchByTypeAndPage(@PathVariable String type, @PathVariable Integer pageNum) {
        int pageSize = 10;
        List<Order> orders = orderService.searchByTypeAndPage(type, pageNum, pageSize);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/search/searchGoodsByKeys/{keys}/{pageNum}")
    public Result<List<Order>> searchGoodsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        int pageSize = 10;
        List<Order> orders = orderService.searchMyGoodsByKeys(ownName, keys, pageNum, pageSize);
        return new Result<>(true, 20000, "查询成功", orders);
    }

    @GetMapping("/search/searchNeedsByKeys/{keys}/{pageNum}")
    public Result<List<Order>> searchSearchNeedsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ownName = principal.getUsername();
        List<Order> orders = orderService.searchByCondition(ownName, keys, "needs");
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
    public Result<Map<String, Object>> addOrder(@RequestBody Map<String, Object> payload) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("DEBUG: Full payload received = " + payload);
        System.out.println("DEBUG: payload keys = " + payload.keySet());
        
        Order order = new Order();
        order.setTitle((String) payload.get("title"));
        order.setPrice(new java.math.BigDecimal(String.valueOf(payload.get("price"))));
        order.setContent((String) payload.get("content"));
        order.setPicture((String) payload.get("picture"));
        order.setType((String) payload.get("type"));
        order.setCooperationName((String) payload.get("cooperationName"));
        order.setAddress((String) payload.get("address"));
        order.setOrderStatus(0);
        order.setOwnName(principal.getUsername());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        orderService.addOrder(order);

        System.out.println("DEBUG: orderId after insert = " + order.getOrderId());

        // 如果有规格，保存规格
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> specs = (List<Map<String, Object>>) payload.get("specs");
        System.out.println("DEBUG: specs received = " + (specs != null ? specs.size() : "null"));
        
        List<OrderSpec> specList = new java.util.ArrayList<>();
        if (specs != null && !specs.isEmpty()) {
            for (Map<String, Object> specMap : specs) {
                OrderSpec spec = new OrderSpec();
                spec.setOrderId(order.getOrderId());
                spec.setSpecName(String.valueOf(specMap.get("specName")));
                spec.setSpecPrice(new java.math.BigDecimal(String.valueOf(specMap.get("specPrice"))));
                String stockStr = specMap.get("specStock") != null ? specMap.get("specStock").toString().trim() : "";
                spec.setSpecStock(stockStr.isEmpty() || stockStr.equals("null") ? 999 : Integer.valueOf(stockStr));
                spec.setSpecSales(0);
                spec.setSpecSort(specs.indexOf(specMap));
                spec.setCreateTime(new Date());
                specList.add(spec);
            }
            System.out.println("DEBUG: saving " + specList.size() + " specs, first spec orderId = " + (specList.size() > 0 ? specList.get(0).getOrderId() : "N/A"));
            orderSpecService.addSpecs(specList);
            System.out.println("DEBUG: specs saved successfully");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getOrderId());
        return new Result<>(true, 20000, "添加成功", result);
    }

    @PutMapping("/{orderId}")
    public Result<Void> updateOrder(@RequestBody Map<String, Object> payload, @PathVariable Integer orderId) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setTitle((String) payload.get("title"));
        order.setPrice(new java.math.BigDecimal(String.valueOf(payload.get("price"))));
        order.setContent((String) payload.get("content"));
        order.setPicture((String) payload.get("picture"));
        order.setType((String) payload.get("type"));
        order.setUpdateTime(new Date());
        orderService.updateOrder(order);

        orderSpecService.deleteByOrderId(orderId);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> specs = (List<Map<String, Object>>) payload.get("specs");
        if (specs != null && !specs.isEmpty()) {
            List<OrderSpec> specList = new java.util.ArrayList<>();
            for (Map<String, Object> specMap : specs) {
                OrderSpec spec = new OrderSpec();
                spec.setOrderId(orderId);
                spec.setSpecName(String.valueOf(specMap.get("specName")));
                spec.setSpecPrice(new java.math.BigDecimal(String.valueOf(specMap.get("specPrice"))));
                String stockStr = specMap.get("specStock") != null ? specMap.get("specStock").toString().trim() : "";
                spec.setSpecStock(stockStr.isEmpty() || stockStr.equals("null") ? 999 : Integer.valueOf(stockStr));
                spec.setSpecSales(0);
                spec.setSpecSort(specs.indexOf(specMap));
                spec.setCreateTime(new Date());
                specList.add(spec);
            }
            orderSpecService.addSpecs(specList);
        }
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

    @GetMapping("/admin/goods/{pageNum}")
    public Result<Map<String, Object>> adminSearchGoods(@PathVariable Integer pageNum) {
        int pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        List<Order> orders = orderDao.selectByTypeAndPage("goods", offset, pageSize);
        int total = orderDao.countGoods();
        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        return new Result<>(true, 20000, "查询成功", result);
    }

    @GetMapping("/admin/searchGoodsByKeys/{keys}/{pageNum}")
    public Result<Map<String, Object>> adminSearchGoodsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        int pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        List<Order> orders = orderDao.searchGoodsByKeys(keys, offset, pageSize);
        int total = orderDao.countGoodsByKeys(keys);
        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        return new Result<>(true, 20000, "查询成功", result);
    }

    @GetMapping("/public/goods/{pageNum}")
    public Result<Map<String, Object>> publicSearchGoods(@PathVariable Integer pageNum) {
        int pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        List<Order> orders = orderDao.selectByTypeAndPageWithStatus("goods", offset, pageSize);
        int total = orderDao.countGoodsWithStatus();
        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        return new Result<>(true, 20000, "查询成功", result);
    }

    @GetMapping("/public/searchGoodsByKeys/{keys}/{pageNum}")
    public Result<Map<String, Object>> publicSearchGoodsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        int pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        List<Order> orders = orderDao.searchGoodsByKeysWithStatus(keys, offset, pageSize);
        int total = orderDao.countGoodsByKeysWithStatus(keys);
        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        return new Result<>(true, 20000, "查询成功", result);
    }

    @GetMapping("/public/needs/{pageNum}")
    public Result<Map<String, Object>> publicSearchNeeds(@PathVariable Integer pageNum) {
        int pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        List<Order> orders = orderDao.selectByTypeAndPageWithStatus("needs", offset, pageSize);
        int total = orderDao.countNeedsWithStatus();
        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        return new Result<>(true, 20000, "查询成功", result);
    }

    @GetMapping("/public/searchNeedsByKeys/{keys}/{pageNum}")
    public Result<Map<String, Object>> publicSearchNeedsByKeys(@PathVariable String keys, @PathVariable Integer pageNum) {
        int pageSize = 10;
        int offset = (pageNum - 1) * pageSize;
        List<Order> orders = orderDao.searchNeedsByKeysWithStatus(keys, offset, pageSize);
        int total = orderDao.countNeedsByKeysWithStatus(keys);
        Map<String, Object> result = new HashMap<>();
        result.put("list", orders);
        result.put("total", total);
        return new Result<>(true, 20000, "查询成功", result);
    }

}