package com.qst.crop.dao;

import com.qst.crop.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {

    int insertSelective(Order record);

    int updateByPrimaryKeySelective(Order record);

    int deleteByPrimaryKey(Integer orderId);

    Order selectById(Integer orderId);

    List<Order> selectByExample(Order order);

    List<Order> selectByTypeAndPage(@Param("type") String type, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Order> selectByOwnNameAndType(@Param("ownName") String ownName, @Param("type") String type);

    List<Order> searchByCondition(@Param("ownName") String ownName, @Param("title") String title, @Param("type") String type);

    List<Order> searchMyNeedsByKeys(@Param("ownName") String ownName, @Param("keys") String keys, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    List<Order> searchGoodsByKeys(@Param("keys") String keys, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    List<Order> searchMyGoodsByKeys(@Param("ownName") String ownName, @Param("keys") String keys, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

void takeDown(@Param("orderId") String orderId);

void takeUp(@Param("orderId") String orderId);

int countGoods();

int countGoodsByKeys(@Param("keys") String keys);

List<Order> selectByTypeAndPageWithStatus(@Param("type") String type, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

int countGoodsWithStatus();

List<Order> searchGoodsByKeysWithStatus(@Param("keys") String keys, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

int countGoodsByKeysWithStatus(@Param("keys") String keys);

int countNeedsWithStatus();

List<Order> searchNeedsByKeysWithStatus(@Param("keys") String keys, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

int countNeedsByKeysWithStatus(@Param("keys") String keys);
}