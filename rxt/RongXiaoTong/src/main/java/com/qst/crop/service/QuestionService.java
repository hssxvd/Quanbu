package com.qst.crop.service;

import com.github.pagehelper.PageInfo;
import com.qst.crop.entity.Question;

import java.util.List;

public interface QuestionService {

    void insert(Question record);

    Question selectById(Integer id);

    PageInfo<Question> selectByKeys(String keys, Integer pageNum);

    void updateById(Question record);

    List<Question> selectByQuestion(String type);

    void delete(Integer id);

}