package com.qst.financing.controller;

import com.github.pagehelper.PageInfo;
import com.qst.financing.common.Result;
import com.qst.financing.common.StatusCode;
import com.qst.financing.entity.BankUser;
import com.qst.financing.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/user")
@CrossOrigin
public class BankUserController {

    @Autowired
    private BankUserService bankUserService;

    @PostMapping("/register")
    public Result<BankUser> register(@RequestBody BankUser bankUser) {
        int i = bankUserService.register(bankUser);
        if (i > 0) {
            return new Result<>(true, StatusCode.OK, "注册成功");
        } else {
            return new Result<>(false, StatusCode.ERROR, "注册失败");
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('admin')")
    public Result<PageInfo<BankUser>> list(@RequestParam("pageNum") Integer pageNum) {
        PageInfo<BankUser> pageInfo = bankUserService.findPage(pageNum);
        return new Result<>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    @GetMapping("/{userName}")
    @PreAuthorize("hasRole('admin')")
    public Result<BankUser> selectByUserName(@PathVariable("userName") String userName) {
        BankUser bankUser = bankUserService.selectByUserName(userName);
        if (bankUser != null) {
            return new Result<>(true, StatusCode.OK, "查询成功", bankUser);
        } else {
            return new Result<>(false, StatusCode.ERROR, "用户不存在");
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('admin')")
    public Result<BankUser> update(@RequestBody BankUser bankUser) {
        int i = bankUserService.updateUser(bankUser);
        if (i > 0) {
            return new Result<>(true, StatusCode.OK, "更新成功");
        } else {
            return new Result<>(false, StatusCode.ERROR, "更新失败");
        }
    }

    @DeleteMapping("/{userName}")
    @PreAuthorize("hasRole('admin')")
    public Result<BankUser> delete(@PathVariable("userName") String userName) {
        bankUserService.delete(userName);
        return new Result<>(true, StatusCode.OK, "删除成功");
    }

}