package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Expert;
import com.qst.crop.entity.ExpertUser;
import com.qst.crop.service.ExpertService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
@Tag(name = "问题模块接口")
@CrossOrigin
public class QuestionController {

    @Autowired
    private ExpertService expertService;

    @Operation(summary = "分页查询所有专家")
    @GetMapping("/findExpert/{pageNum}")
    public Result<PageInfo<Expert>> findPage(@PathVariable Integer pageNum) {
        PageInfo<Expert> expertPageInfo = expertService.findPage(pageNum);
        return new Result<PageInfo<Expert>>(true, StatusCode.OK, "查询成功", expertPageInfo);
    }

    @Operation(summary = "分页条件查询专家")
    @GetMapping("/findExpertByKeys/{keys}/{pageNum}")
    public Result<PageInfo<Expert>> findExpertByKeys(@PathVariable("keys") String keys, @PathVariable Integer pageNum) {
        PageInfo<Expert> expertPageInfo = expertService.findPageByKeys(keys, pageNum);
        return new Result<PageInfo<Expert>>(true, StatusCode.OK, "查询成功", expertPageInfo);
    }

    @Operation(summary = "分页条件查询专家")
    @GetMapping("/findExpertUser/{pageNum}")
    public Result<PageInfo<ExpertUser>> findExpertUser(@PathVariable Integer pageNum) {
        PageInfo<ExpertUser> ExpertUserPageInfo = expertService.findPageExpertUser(pageNum);
        return new Result<PageInfo<ExpertUser>>(true, StatusCode.OK, "查询成功", ExpertUserPageInfo);
    }

}