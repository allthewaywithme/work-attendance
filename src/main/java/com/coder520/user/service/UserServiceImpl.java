package com.coder520.user.service;

import com.coder520.common.utils.MD5Utils;
import com.coder520.user.dao.UserMapper;
import com.coder520.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/6/16.
 */
@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;


    /**
     *@Author JackWang [www.coder520.com]
     *@Date 2017/6/18 12:48
     *@Description 根据用户名查询用户
     */
    @Override
    public User findUserByUserName(String username) {
        User user=null;
        try {
             user =userMapper.selectByUserName(username);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return user;
    }

    /**
     * 测试下事物，插入数据时出现错误，同时回滚，没出错都提交
     * @param user
     * @param user2
     */
    @Override
    @Transactional
    public void insertUser(User user, User user2) {

        userMapper.insertSelective(user);
        userMapper.insertSelective(user2);
    }

    @Override
    public int createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setPassword(MD5Utils.encryptPassword(user.getPassword()));
        int i = userMapper.insertSelective(user);
        return i;
    }


   /* @Override
    public void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        user.setPassword(MD5Utils.encryptPassword(user.getPassword()));

        userMapper.insertSelective(user);
    }*/
}
