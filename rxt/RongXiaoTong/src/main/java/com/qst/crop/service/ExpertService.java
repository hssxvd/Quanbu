package com.qst.crop.service;

import com.github.pagehelper.PageInfo;
import com.qst.crop.entity.Expert;
import com.qst.crop.entity.ExpertUser;

public interface ExpertService {

    PageInfo<Expert> findPage(Integer pageNum);

    PageInfo<Expert> findPageByKeys(String keys, Integer pageNum);

    PageInfo<ExpertUser> findPageExpertUser(Integer pageNum);

}