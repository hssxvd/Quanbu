package com.qst.crop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.crop.dao.KnowledgeDao;
import com.qst.crop.entity.Knowledge;
import com.qst.crop.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    private static final Integer pageSize = 30;
    @Autowired
    private KnowledgeDao knowledgeDao;

    @Override
    public PageInfo<Knowledge> findPage(Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Knowledge> knowledges = knowledgeDao.selectAll();
        PageInfo<Knowledge> knowledgePageInfo = new PageInfo<>(knowledges);
        return knowledgePageInfo;
    }

    @Override
    public Knowledge selectById(Integer id) {
        Knowledge knowledge = knowledgeDao.selectByPrimaryKey(id);
        return knowledge;
    }

    @Override
    public PageInfo<Knowledge> findPageByKeys(String keys, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Knowledge> knowledges = knowledgeDao.selectAllByKeys(keys);
        PageInfo<Knowledge> knowledgePageInfo = new PageInfo<>(knowledges);
        return knowledgePageInfo;
    }
}