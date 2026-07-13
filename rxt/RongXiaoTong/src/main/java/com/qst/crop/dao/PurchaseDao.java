package com.qst.crop.dao;

import com.qst.crop.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseDao {

    int insertSelective(Purchase record);

    Purchase selectNewPurchaseId(@Param("ownName")String ownName);

}
