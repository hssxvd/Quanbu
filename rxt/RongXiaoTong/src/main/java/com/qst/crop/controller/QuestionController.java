package com.qst.crop.controller;

import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.entity.Expert;
import com.qst.crop.entity.ExpertUser;
import com.qst.crop.entity.Question;
import com.qst.crop.service.ExpertService;
import com.qst.crop.service.QuestionService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@Tag(name = "问题模块接口")
@CrossOrigin
public class QuestionController {

    @Autowired
    private ExpertService expertService;

    @Autowired
    private QuestionService questionService;

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

    @Operation(summary = "根据ID查询询问情况")
    @GetMapping("/selectId/{id}")
    public Result selectById(@PathVariable("id") Integer id) {
        Question question = questionService.selectById(id);
        return new Result(true, StatusCode.OK, "查询成功", question);
    }

    @Operation(summary = "添加询问情报")
    @PostMapping("/add")
    public Result add(@RequestBody Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer stringBuffer = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            System.out.println(s);
            return new Result<String>(false, StatusCode.ERROR, "添加失败", s);
        }
        questionService.insert(question);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @Operation(summary = "分页条件查询所有问答情况")
    @GetMapping("/findPageQues/{keys}/{pageNum}")
    public Result<PageInfo<Question>> findPageQues(@PathVariable("keys") String keys, @PathVariable Integer pageNum) {
        PageInfo<Question> questionPageInfo = questionService.selectByKeys(keys, pageNum);
        return new Result(true, StatusCode.OK, "查询成功", questionPageInfo);
    }

    @Operation(summary = "分页查询所有问答情况")
    @GetMapping("/findAllQues/{pageNum}")
    public Result<PageInfo<Question>> findAllQues(@PathVariable Integer pageNum) {
        PageInfo<Question> questionPageInfo = questionService.selectByKeys(null, pageNum);
        return new Result(true, StatusCode.OK, "查询成功", questionPageInfo);
    }

    @Operation(summary = "查询个人问答")
    @GetMapping("/selectByUser")
    public Result selectByUser() {
        List<Question> questions = questionService.selectByQuestion("questioner");
        return new Result(true, StatusCode.OK, "查询成功", questions);
    }

    @Operation(summary = "根据角色查询问答")
    @GetMapping("/selectByKind/{kind}")
    public Result selectByKind(@PathVariable("kind") String kind) {
        String type = "expert".equals(kind) ? "expert" : "questioner";
        List<Question> questions = questionService.selectByQuestion(type);
        return new Result(true, StatusCode.OK, "查询成功", questions);
    }

    @Operation(summary = "根据id修改询问情报")
    @PutMapping("/update")
    public Result update(@RequestBody Question question, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer stringBuffer = new StringBuffer();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                stringBuffer.append(objectError.getDefaultMessage()).append("; ");
            }
            String s = stringBuffer.toString();
            System.out.println(s);
            return new Result<String>(false, StatusCode.ERROR, "修改失败", s);
        }
        questionService.updateById(question);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @Operation(summary = "根据id删除询问情报")
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        questionService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}