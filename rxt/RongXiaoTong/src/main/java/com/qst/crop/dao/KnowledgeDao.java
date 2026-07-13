package com.qst.crop.dao;

import com.qst.crop.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KnowledgeDao {

    List<Knowledge> selectAll();

    Knowledge selectByPrimaryKey(Integer id);

    List<Knowledge> selectAllByKeys(@Param("keys") String keys);

}