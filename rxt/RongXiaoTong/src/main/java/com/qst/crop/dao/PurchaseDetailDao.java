package com.qst.crop.dao;

import com.qst.crop.entity.PurchaseDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchaseDetailDao {

    int insertSelective(PurchaseDetail record);

}