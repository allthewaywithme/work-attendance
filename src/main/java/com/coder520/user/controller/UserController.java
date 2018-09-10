package com.coder520.user.controller;

import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by JackWangon[www.aiprogram.top] 2017/6/16.
 */
@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;




    @RequestMapping("/home")
    public String home(){
        return "home";
    }
/*
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("aaaaaaaaaaaa");
        User laowang = userService.findUserByUserName("laowang");
        model.addAttribute("user",laowang);
        return "home";
    }*/

    /*@RequestMapping("/save")
    public String insertUser(){
        User user=new User();
        user.setRealName("wangjianbing");
        user.setUsername("laowang");
        user.setMobile("123456");
        user.setHeadImage("/url");
        user.setPassword("dddd");
        User user2=new User();
//        user2.setId(1l);
        user2.setRealName("wangjianbing");
        user2.setUsername("laowang");
        user2.setMobile("123456");
        user2.setHeadImage("/url");
        user2.setPassword("dddd");
        userService.insertUser(user,user2);
        return "attend";
    }*/


    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping("/userinfo")
    @ResponseBody
    public User getUser(HttpSession session){
        User user = (User)session.getAttribute("userinfo");
        return user;
    }

/**
     *@Author JackWang [www.coder520.com]
     *@Date 2017/6/19 21:51
     *@Description  获取用户信息
     *//*

    @RequestMapping("/userinfo")
    @ResponseBody
    public User getUser(){
       User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userinfo");
       return user;
    }


    */

    /**
     * 登出方法
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //摧毁session 返回登陆页面
        session.invalidate();
        return "login/lk";
    }

}
