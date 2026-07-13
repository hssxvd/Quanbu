package com.qst.crop.dao;

import com.qst.crop.entity.Shoppingcart;
import com.qst.crop.model.ShoppingModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingcartDao {

    Shoppingcart selectByPrimaryKey(Integer shoppingId);

    int deleteByPrimaryKey(Integer shoppingId);

    int insertSelective(Shoppingcart shoppingcart);

    List<ShoppingModel> selectByShopping(Shoppingcart shoppingcart);

    int updateByPrimaryKeySelective(Shoppingcart shoppingcart);

}
