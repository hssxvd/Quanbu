package com.qst.crop.dao;

import com.qst.crop.entity.SellPurchase;
import com.qst.crop.model.MySellPurchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellPurchaseDao {

    int insertSelective(SellPurchase record);

    List<SellPurchase> selectByName(@Param("ownName")String ownName);

    List<MySellPurchase> selectByOwnName(@Param("ownName")String ownName);

    int updateStatus(@Param("purchaseId")Integer purchaseId, @Param("purchaseStatus")Integer purchaseStatus);

}