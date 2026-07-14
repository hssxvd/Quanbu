package com.qst.crop.service;

import com.qst.crop.entity.Reserve;

import java.util.List;

public interface ReserveService {
    void insert(Reserve record);

    void delete(Integer id);

    Reserve selectById(Integer id);

    void updateById(Reserve record);

    List<Reserve> selectByReserve(String type);
}
