package com.qst.crop.controller;

import com.github.pagehelper.PageInfo;
import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.User;
import com.qst.crop.service.ExpertService;
import com.qst.crop.service.OrderService;
import com.qst.crop.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.StringJoiner;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private OrderService orderService;

    @Operation(summary = "分页查询所有用户")
    @GetMapping("/search/{pageNum}")
    @PreAuthorize("hasRole('admin')")
    public Result<PageInfo<User>> findPage(@PathVariable("pageNum") Integer pageNum) {
        PageInfo<User> pageInfo = userService.findPage(pageNum);
        return new Result<>(true, StatusCode.OK, "分页查询成功", pageInfo);
    }

    @Operation(summary = "根据用户名删除用户")
    @DeleteMapping("/{userName}")
    @PreAuthorize("hasRole('admin')")
    public Result<String> delete(@PathVariable("userName") String userName) {
        if (orderService.hasOrdersByOwnName(userName)) {
            return new Result<>(false, StatusCode.ERROR, "删除失败", "该用户有发布的订单，暂不能删除");
        }
        userService.delete(userName);
        expertService.delete(userName);
        return new Result<>(true, StatusCode.OK, "删除成功");
    }

    @Operation(summary = "根据用户名修改用户信息")
    @PutMapping("/{userName}")
    @PreAuthorize("hasRole('admin')")
    public Result<String> update(@Valid @RequestBody User user, BindingResult bindingResult, @PathVariable("userName") String userName) {
        if (bindingResult.hasErrors()) {
            StringJoiner stringJoiner = new StringJoiner(",");
            bindingResult.getAllErrors().forEach(objectError -> stringJoiner.add(objectError.getDefaultMessage()));
            String s = stringJoiner.toString();
            System.out.println(s);
            return new Result<>(false, StatusCode.ERROR, "信息修改失败", s);
        }
        user.setUserName(userName);
        userService.updateUser(user);
        return new Result<>(true, StatusCode.OK, "信息修改成功", "修改成功");
    }

    @Operation(summary = "根据用户名查询用户")
    @GetMapping("/{userName}")
    public Result<User> selectByUserName(@PathVariable("userName") String userName) {
        User user = userService.selectByUserName(userName);
        return new Result<>(true, StatusCode.OK, "查询成功", user);
    }

}