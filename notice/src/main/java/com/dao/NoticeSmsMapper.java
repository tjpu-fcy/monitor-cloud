package com.dao;

import com.entity.NoticeSms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface NoticeSmsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeSms record);

    int insertSelective(NoticeSms record);

    NoticeSms selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeSms record);

    int updateByPrimaryKey(NoticeSms record);

    List<NoticeSms > selectByTime(@Param("start") Date start, @Param("end") Date end);

    void deleteByPrimaryKeys(int[] ids);
}