package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Discuss;
import com.qst.crop.service.DiscussService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "知识讨论模块接口")
@RestController
@RequestMapping("/discuss")
@CrossOrigin
public class DiscussController {
    @Autowired
    private DiscussService discussService;

    @Operation(summary = "根据知识id查询讨论")
    @GetMapping("/{knowledgeId}")
    public Result selectByKnowledgeId(@PathVariable("knowledgeId") Integer knowledgeId) {
        List<Discuss> discusses = discussService.selectByKnowledgeId(knowledgeId);
        return new Result(true, StatusCode.OK, "查询成功", discusses);
    }

    @Operation(summary = "添加讨论")
    @PostMapping()
    public Result add(@RequestBody Discuss discuss) {
        discussService.add(discuss);
        return new Result(true, StatusCode.OK, "添加成功");
    }
}