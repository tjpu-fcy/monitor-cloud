package com.dao;

import com.entity.NoticeWeixin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface NoticeWeixinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NoticeWeixin record);

    int insertSelective(NoticeWeixin record);

    NoticeWeixin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NoticeWeixin record);

    int updateByPrimaryKey(NoticeWeixin record);

    List<NoticeWeixin> selectByTime(@Param("start") Date start, @Param("end") Date end);

    void deleteByPrimaryKeys(int[] ids);
}