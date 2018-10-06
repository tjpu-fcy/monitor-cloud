package com.dao;

import com.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User findByUserAccount(String userAccount);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}