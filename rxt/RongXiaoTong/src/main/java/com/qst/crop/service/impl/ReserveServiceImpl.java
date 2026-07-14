package com.qst.crop.service.impl;

import com.qst.crop.dao.ReserveDao;
import com.qst.crop.entity.Reserve;
import com.qst.crop.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    private ReserveDao reserveDao;
    @Override
    public void insert(Reserve reserve) {
        reserveDao.insertSelective(reserve);
    }
}