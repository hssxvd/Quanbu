package com.qst.crop.dao;

import com.qst.crop.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDao {

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    List<Question> selectAllByKeys(@Param("keys") String keys);

}
package com.qst.crop.dao;

import com.qst.crop.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionDao {

    int insertSelective(Question record);

    Question selectByPrimaryKey(Integer id);

    List<Question> selectAllByKeys(@Param("keys") String keys);

    int updateByPrimaryKeySelective(Question record);

    List<Question> selectByQuestion(Question record);

    int deleteByPrimaryKey(Integer id);

}