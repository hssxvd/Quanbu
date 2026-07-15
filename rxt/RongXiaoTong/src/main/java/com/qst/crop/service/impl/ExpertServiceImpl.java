package com.qst.crop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qst.crop.dao.ExpertDao;
import com.qst.crop.entity.Expert;
import com.qst.crop.entity.ExpertUser;
import com.qst.crop.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertDao expertDao;

    private Integer pageSize = 10;

    @Override
    public PageInfo<Expert> findPage(Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Expert> experts = expertDao.selectAllExpert();
        PageInfo<Expert> expertPageInfo = new PageInfo<>(experts);
        return expertPageInfo;
    }

    @Override
    public PageInfo<Expert> findPageByKeys(String keys, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Expert> experts = expertDao.selectAllByKeys(keys);
        PageInfo<Expert> orderPageInfo = new PageInfo<>(experts);
        return orderPageInfo;
    }

    @Override
    public PageInfo<ExpertUser> findPageExpertUser(Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExpertUser> expertUser = expertDao.selectAllUserExpert();
        PageInfo<ExpertUser> expertUserPageInfo = new PageInfo<>(expertUser);
        return expertUserPageInfo;
    }

    @Override
    public Expert selectByUserName(String userName) {
        return expertDao.selectByUserName(userName);
    }

    @Override
    public int updateExpert(Expert expert) {
        return expertDao.updateByPrimaryKeySelective(expert);
    }

    @Override
    public void delete(String userName) {
        expertDao.deleteByPrimaryKey(userName);
    }
}