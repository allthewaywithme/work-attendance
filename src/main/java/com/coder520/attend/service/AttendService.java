package com.coder520.attend.service;

import com.coder520.attend.entity.Attend;
import com.coder520.common.page.PageQueryBean;
import com.coder520.vo.QueryCondition;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/8 16:25
 */
public interface AttendService {
    public void signAttend(Attend attend);


    PageQueryBean listAttend(QueryCondition queryCondition);

    void checkAttend();
}
