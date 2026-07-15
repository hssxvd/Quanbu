package com.qst.crop.service.impl;

import com.qst.crop.dao.FinanceDao;
import com.qst.crop.entity.Intention;
import com.qst.crop.entity.Recommend;
import com.qst.crop.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private FinanceDao financeDao;

    @Override
    public List<Intention> selectIntentionByName(String name) {
        List<Intention> intentions = financeDao.selectIntentionByName(name);
        return intentions;
    }

    @Override
    public void updateIntentionByName(Intention intention) {
        intention.setUpdateTime(new Date());
        financeDao.updateIntentionByName(intention);
    }

    @Override
    public void insertIntentionByName(Intention intention) {
        intention.setCreateTime(new Date());
        intention.setUpdateTime(new Date());
        financeDao.insertIntentionByName(intention);
    }

    @Override
    public void deleteIntentionByName(String name) {
        financeDao.deleteIntentionByName(name);
    }

    @Override
    public List<Recommend> selectRecommend(String name) {
        Intention intention = financeDao.selectIntention(name);
        if (intention == null) {
            System.out.println("无意向");
            List<Recommend> list = financeDao.selectWithNoneIntention(name);
            System.out.println(list);
            return list;
        } else {
            System.out.println("有意向");
            List<Recommend> list = financeDao.selectAllRecommend(name);
            System.out.println(list);
            return list;
        }
    }
}