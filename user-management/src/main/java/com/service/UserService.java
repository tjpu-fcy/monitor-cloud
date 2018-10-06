package com.service;

import com.dao.UserMapper;
import com.dao.UserProfileMapper;
import com.entity.User;
import com.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserProfileMapper userProfileMapper;

    //用户注册成功需同时更新用户表和用户详情表
    @Transactional(rollbackFor = Exception.class)
    public  void insertRegisterUser(User user,UserProfile userProfile){
        userMapper.insert(user);
        userProfile.setUserId(user.getId());
        userProfileMapper.insert(userProfile);
    }

    //通过账户查找
    public  User findByUserAccount(String userAccount){
        return userMapper.findByUserAccount(userAccount);
    }

    //通过邮箱查找用户详情
    public  UserProfile findByEmail(String email){
        return userProfileMapper.findByEmail(email);
    }

    //通过主键查找用户
    public  User findById(Integer id ){
        return userMapper.selectByPrimaryKey(id);
    }



    //重置账户密码
    public  void resetUserAccountPassword(User user){
         userMapper.updateByPrimaryKeySelective(user);
    }
}
