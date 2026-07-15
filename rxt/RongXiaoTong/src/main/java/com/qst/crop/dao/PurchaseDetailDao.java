package com.qst.crop.dao;

import com.qst.crop.entity.PurchaseDetail;
import com.qst.crop.model.MyPurchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseDetailDao {

    int insertSelective(PurchaseDetail record);

    List<MyPurchase> selectByPurchase(String name);

}