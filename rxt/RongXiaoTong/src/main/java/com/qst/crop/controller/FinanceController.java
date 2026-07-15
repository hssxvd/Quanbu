package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Intention;
import com.qst.crop.entity.Recommend;
import com.qst.crop.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
@Tag(name = "融资模块接口")
@CrossOrigin
public class FinanceController {
    @Autowired
    private FinanceService financeService;

    @Operation(summary = "查询融资意向")
    @GetMapping("/selectIntentionByName")
    public Result selectIntentionByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Intention> intentions = financeService.selectIntentionByName(name);
        return new Result(true, StatusCode.OK, "查询成功", intentions);
    }

    @Operation(summary = "添加融资意向")
    @PostMapping("/insertIntentionByName")
    public Result insertIntentionByName(@RequestBody Intention intention) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        intention.setUserName(name);
        financeService.insertIntentionByName(intention);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @Operation(summary = "修改融资意向")
    @PutMapping("/updateIntentionByName")
    public Result updateIntentionByName(@RequestBody Intention intention) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        intention.setUserName(name);
        financeService.updateIntentionByName(intention);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @Operation(summary = "查询推荐融资人")
    @GetMapping("/selectRecommendByName")
    public Result selectRecommendByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Recommend> list = financeService.selectRecommend(name);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @Operation(summary = "删除融资意向")
    @DeleteMapping("/deleteIntentionByName")
    public Result deleteIntentionByName() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        financeService.deleteIntentionByName(name);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}