package com.coder520.user.service;

import com.coder520.user.entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/6/16.
 */
public interface UserService {


    User findUserByUserName(String username);

    void insertUser(User user, User user2);

    int createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

//    void createUser(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;
}
