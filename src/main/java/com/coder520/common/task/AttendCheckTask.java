package com.coder520.common.task;

import com.coder520.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/9 1:16
 */
public class AttendCheckTask {

    @Autowired
    private AttendService attendService;

    public  void checkAttend(){

        attendService.checkAttend();

    }


}
