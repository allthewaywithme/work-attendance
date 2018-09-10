package com.coder520.attend.controller;

import com.coder520.attend.entity.Attend;
import com.coder520.attend.service.AttendService;
import com.coder520.common.page.PageQueryBean;
import com.coder520.user.entity.User;
import com.coder520.vo.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/8 16:08
 */
@Controller
@RequestMapping("/attend")
public class AttendController {

    @Autowired
    private AttendService attendService;

    @RequestMapping
    public String toAttend(){

        return "attend";
    }

    /**
     *
     * @param attend
     * @param session
     * @return
     * @throws Exception
     * 此处用postman 测试用post请求  参数 body  JSON格式
     */
    @RequestMapping("/sign")
    @ResponseBody
    public String signAttend(@RequestBody Attend attend, HttpSession session) throws Exception {

        attendService.signAttend(attend);

        return "succ";
    }


    /**
     * 考勤数据分页查询
     * @param queryCondition
     * @param session
     * @return
     */
    @RequestMapping("/attendList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition queryCondition,HttpSession session){
        User user =(User) session.getAttribute("userinfo");
        queryCondition.setUserId(user.getId());
        PageQueryBean result=attendService.listAttend(queryCondition);
        return result;
    }




}
