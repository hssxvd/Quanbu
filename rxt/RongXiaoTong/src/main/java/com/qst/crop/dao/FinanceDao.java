package com.qst.crop.dao;

import com.qst.crop.entity.Finance;
import com.qst.crop.entity.Intention;
import com.qst.crop.entity.Recommend;

import java.util.List;

public interface FinanceDao {
    Intention selectIntention(String name);

    List<Intention> selectIntentionByName(String name);

    void updateIntentionByName(Intention intention);

    void insertIntentionByName(Intention intention);

    void deleteIntentionByName(String name);

    List<Recommend> selectAllRecommend(String name);

    List<Recommend> selectWithNoneIntention(String name);

    int insertSelective(Finance record);

    void insertMulti(Finance finance);

    List<Finance> selectByFinance(Finance finance);

    int deleteByPrimaryKey(Integer financeId);

    Finance selectByPrimaryKey(Integer financeId);

    int updateByPrimaryKeySelective(Finance record);}