package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Reserve;
import com.qst.crop.service.ReserveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@Tag(name = "预约模块接口")
@CrossOrigin
public class ReserveController {
    @Autowired
    private ReserveService reserveService;


    //添加预约情报
    @Operation(summary = "添加预约")
    @PostMapping("/add")
    public Result add(@RequestBody Reserve reserve, BindingResult bindingResult) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = principal.getUsername();
        reserve.setQuestioner(name);
        if (reserve.getStatus() == null) {
            reserve.setStatus(0);
        }
        //检查项目
        if (bindingResult.hasErrors()) {
            StringBuffer stringBuffer = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            System.out.println(s);
            return new Result<String>(false, StatusCode.ERROR, "添加失败",s);
        }
        reserveService.insert(reserve);
        return new Result(true, StatusCode.OK, "添加成功");
    }
    //根据ID查询预约情况
    @Operation(summary = "根据ID查询预约情况")
    @GetMapping("/selectId/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        Reserve reserve = reserveService.selectById(id);
        return new Result(true, StatusCode.OK, "查询成功", reserve);
    }

    //根据id修改预约情报
    @Operation(summary = "更新预约状态")
    @PutMapping("/update")
    public Result update(@RequestBody Reserve reserve, BindingResult bindingResult) {

        //检查项目
        if (bindingResult.hasErrors()) {
            StringBuffer stringBuffer = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            System.out.println(s);
            return new Result<String>(false, StatusCode.ERROR, "修改失败",s);
        }
        reserveService.updateById(reserve);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    //根据id删除预约情报
    @Operation(summary = "删除预约")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        reserveService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    //根据用户查询预约情况
    @Operation(summary = "根据用户查询预约情况")
    @GetMapping("/selectByKind/{kind}")
    public Result selectByKind(@PathVariable("kind") String kind) {
        List<Reserve> reserves = reserveService.selectByReserve(kind);
        return new Result(true, StatusCode.OK, "查询成功", reserves);
    }
}