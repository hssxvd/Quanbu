package com.qst.crop.dao;

import com.qst.crop.entity.Reserve;

import java.util.List;

public interface ReserveDao {
    int insertSelective(Reserve record);

    int deleteByPrimaryKey(Integer id);

    Reserve selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reserve record);

    List<Reserve> selectByReserve(Reserve record);
}
