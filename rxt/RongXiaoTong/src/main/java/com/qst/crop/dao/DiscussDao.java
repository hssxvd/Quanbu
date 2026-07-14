package com.qst.crop.dao;

import com.qst.crop.entity.Discuss;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussDao {

    int insertSelective(Discuss record);

    List<Discuss> selectByKnowledgeId(@Param("knowledgeId") Integer knowledgeId);

}