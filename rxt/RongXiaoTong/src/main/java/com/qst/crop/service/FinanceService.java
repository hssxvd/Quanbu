package com.qst.crop.service;

import com.qst.crop.entity.Finance;
import com.qst.crop.entity.Intention;
import com.qst.crop.entity.Recommend;

import java.util.List;

public interface FinanceService {
    List<Intention> selectIntentionByName(String name);

    void updateIntentionByName(Intention intention);

    void insertIntentionByName(Intention intention);

    void deleteIntentionByName(String name);

    List<Recommend> selectRecommend(String name);

    void add(Finance finance);

    void addMulti(Finance finance);
}