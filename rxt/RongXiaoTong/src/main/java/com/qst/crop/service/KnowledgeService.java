package com.qst.crop.service;

import com.github.pagehelper.PageInfo;
import com.qst.crop.entity.Knowledge;

public interface KnowledgeService {
    PageInfo<Knowledge> findPage(Integer pageNum);

    Knowledge selectById(Integer id);

    PageInfo<Knowledge> findPageByKeys(String keys, Integer pageNum);
}