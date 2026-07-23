package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.OrderSpec;
import com.qst.crop.service.OrderSpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/spec")
@Tag(name = "商品规格接口")
public class OrderSpecController {

    @Autowired
    private OrderSpecService orderSpecService;

    @Operation(summary = "添加规格")
    @PostMapping("/add")
    public Result<Void> addSpec(@RequestBody OrderSpec spec) {
        if (spec.getCreateTime() == null) {
            spec.setCreateTime(new Date());
        }
        if (spec.getSpecSales() == null) {
            spec.setSpecSales(0);
        }
        orderSpecService.addSpec(spec);
        return new Result<>(true, StatusCode.OK, "添加成功");
    }

    @Operation(summary = "批量添加规格")
    @PostMapping("/addBatch")
    public Result<Void> addBatch(@RequestBody List<OrderSpec> specs) {
        if (specs != null) {
            for (OrderSpec spec : specs) {
                if (spec.getCreateTime() == null) {
                    spec.setCreateTime(new Date());
                }
                if (spec.getSpecSales() == null) {
                    spec.setSpecSales(0);
                }
            }
            orderSpecService.addSpecs(specs);
        }
        return new Result<>(true, StatusCode.OK, "添加成功");
    }

    @Operation(summary = "更新规格")
    @PutMapping("/update")
    public Result<Void> updateSpec(@RequestBody OrderSpec spec) {
        orderSpecService.updateSpec(spec);
        return new Result<>(true, StatusCode.OK, "更新成功");
    }

    @Operation(summary = "删除规格")
    @DeleteMapping("/delete/{specId}")
    public Result<Void> deleteSpec(@PathVariable Integer specId) {
        orderSpecService.deleteSpec(specId);
        return new Result<>(true, StatusCode.OK, "删除成功");
    }

    @Operation(summary = "根据商品ID删除所有规格")
    @DeleteMapping("/deleteByOrderId/{orderId}")
    public Result<Void> deleteByOrderId(@PathVariable Integer orderId) {
        orderSpecService.deleteByOrderId(orderId);
        return new Result<>(true, StatusCode.OK, "删除成功");
    }

    @Operation(summary = "查询商品规格")
    @GetMapping("/list/{orderId}")
    public Result<List<OrderSpec>> getByOrderId(@PathVariable Integer orderId) {
        List<OrderSpec> specs = orderSpecService.getByOrderId(orderId);
        return new Result<>(true, StatusCode.OK, "查询成功", specs);
    }
}