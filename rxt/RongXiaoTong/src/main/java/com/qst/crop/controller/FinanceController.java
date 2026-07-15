package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Bank;
import com.qst.crop.entity.Finance;
import com.qst.crop.entity.Intention;
import com.qst.crop.entity.Recommend;
import com.qst.crop.service.BankService;
import com.qst.crop.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "融资模块接口")
@RestController
@RequestMapping("/finance")
@CrossOrigin
public class FinanceController {
    @Autowired
    private FinanceService financeService;
    @Autowired
    private BankService bankService;

    //添加单人贷款
    @Operation(summary = "添加单人贷款")
    @PostMapping("/add")
    public Result add(@RequestBody Finance finance) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        finance.setOwnName(name);
//        System.out.println("添加融资情报:"+finance.toString());
        financeService.add(finance);
        return new Result(true, StatusCode.OK, "申请成功");
    }
    //添加组合贷款
    @Operation(summary = "添加组合贷款")
    @PostMapping("/addFinanceMulti")
    public Result addFinanceMulti(@RequestBody Finance finance) {
        //获取登陆的用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        finance.setOwnName(name);
//        System.out.println(name);
        financeService.addMulti(finance);
        return new Result(true, StatusCode.OK, "申请成功");
    }

    //查询银行情报
    @Operation(summary = "查询银行情报")
    @GetMapping("/selectbank")
    public Result selectbank() {

        List<Bank> banks = bankService.selectAllBank();
        return new Result(true, StatusCode.OK, "查询成功", banks);

    }

    //查询融资意向
    @Operation(summary = "查询融资意向")
    @GetMapping("/selectIntentionByName")
    public Result selectIntentionByName(){
        //获取登陆的用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Intention> intentions = financeService.selectIntentionByName(name);
        return new Result(true, StatusCode.OK, "查询成功", intentions);
    }
    //添加融资意向
    @Operation(summary = "添加融资意向")
    @PostMapping("/insertIntentionByName")
    public Result insertIntentionByName(@RequestBody Intention intention){
        //获取登陆的用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        intention.setUserName(name);
        financeService.insertIntentionByName(intention);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    //删除融资意向
    @Operation(summary = "删除融资意向")
    @DeleteMapping("/deleteIntentionByName")
    public Result deleteIntentionByName(){
        //获取登陆的用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        financeService.deleteIntentionByName(name);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //修改融资意向
    @Operation(summary = "修改融资意向")
    @PutMapping("/updateIntentionByName")
    public Result updateIntentionByName(@RequestBody Intention intention){
        //获取登陆的用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        intention.setUserName(name);
//        System.out.println("修改融资");
//        System.out.println(intention);
        financeService.updateIntentionByName(intention);
        return new Result(true, StatusCode.OK, "修改成功");
    }
    //查询推荐融资人
    @Operation(summary = "查询推荐融资人")
    @GetMapping("/selectRecommendByName")
    public Result selectRecommendByName(){
        //获取登陆的用户名
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        List<Recommend> list = financeService.selectRecommend(name);

        return new Result(true, StatusCode.OK, "查询成功",list);
    }
}