package com.qst.crop.service;

import com.github.pagehelper.PageInfo;
import com.qst.crop.entity.Question;

public interface QuestionService {

    void insert(Question record);

    Question selectById(Integer id);

    PageInfo<Question> selectByKeys(String keys, Integer pageNum);

}