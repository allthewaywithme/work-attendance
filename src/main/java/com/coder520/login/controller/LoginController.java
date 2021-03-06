package com.coder520.login.controller;

import com.coder520.common.utils.MD5Utils;
import com.coder520.user.entity.User;
import com.coder520.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/8 1:15
 */
@Controller
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private UserService userService;


    @RequestMapping
    public String login(){
        return "login";
    }

    /**
     * 校验用户登陆
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @RequestMapping("/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String username=request.getParameter("username");
        String pwd=request.getParameter("password");

        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
//        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(1800000);
        } catch (Exception e) {
            return "login_fail";
        }
        return "login_succ";

        /*User user = userService.findUserByUserName(username);
        if (user!=null){
            if (MD5Utils.checkPassword(pwd,user.getPassword())){
                request.getSession().setAttribute("userinfo",user);
                return "login_succ";
            }else {
                return "login_fail";
            }
        }else {
            return "login_fail";
        }
*/

    }

    @RequestMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        int i=userService.createUser(user);
        if (i>0){
            return "succ";
        }
        return "fail";
    }



}
