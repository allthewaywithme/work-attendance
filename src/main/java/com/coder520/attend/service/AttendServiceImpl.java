package com.coder520.attend.service;

import com.coder520.attend.dao.AttendMapper;
import com.coder520.attend.entity.Attend;
import com.coder520.common.page.PageQueryBean;
import com.coder520.common.utils.DateUtils;
import com.coder520.vo.QueryCondition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/8 16:26
 */
@Service
public class AttendServiceImpl implements AttendService {

    private Log log=LogFactory.getLog(AttendServiceImpl.class);

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 中午十二点 判定上下午
     */
    private static final int NOON_HOUR = 12 ;
    private static final int NOON_MINUTE = 00 ;

    /**
     * 早晚上班时间判定
     */
    private static final int MORNING_HOUR = 9;
    private static final int MORNING_MINUTE = 30;
    private static final int EVENING_HOUR = 18;
    private static final int EVENING_MINUTE = 30;

    /**
     * 缺勤一整天
     */
    private static final Integer ABSENCE_DAY =480 ;
    /**
     * 考勤异常状态
     */
    private static final Byte ATTEND_STATUS_ABNORMAL = 2;
    private static final Byte ATTEND_STATUS_NORMAL = 1;

    @Autowired
    private AttendMapper attendMapper;

    @Override
    public void signAttend(Attend attend) {

        try {
            Date today=new Date();
            //设置考情日期
            attend.setAttendDate(new Date());
            attend.setAttendWeek((byte)DateUtils.getTodayWeek());
            //获取当天打卡记录
            Attend todayRecord = attendMapper.selectTodaySignRecord(attend.getUserId());
            Date noon = DateUtils.getDate(NOON_HOUR, NOON_MINUTE);
            if (todayRecord==null){
                if (today.compareTo(noon)<=0){
                    //打卡时间 早于12点 上午打卡
                    attend.setAttendMoring(today);
                }else {
                    attend.setAttendEvening(today);
                }
                //插入打卡数据
                attendMapper.insertSelective(attend);
            }else {
                if(today.compareTo(noon)<=0){
                    //打卡时间 早于12点 上午打卡
                    return;
                }else {
                    //晚上打卡
                    todayRecord.setAttendEvening(today);
                    //判断打卡时间是不是18.30以后是不是早退
                    Date eveningAttend = DateUtils.getDate(EVENING_HOUR,EVENING_MINUTE);
                    if(today.compareTo(eveningAttend)<0){
                        //早于下午六点半 早退
                        todayRecord.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                        todayRecord.setAbsence(DateUtils.getMunite(today,eveningAttend));
                    }else {
                        todayRecord.setAttendStatus(ATTEND_STATUS_NORMAL);
                        todayRecord.setAbsence(0);
                    }
                    attendMapper.updateByPrimaryKeySelective(todayRecord);
//                    if (today.compareTo(noon)<=0){
//                        //打卡时间早上12点 上午打卡
//                        return;
//                    }else {
//                        //晚上打卡
//                        todayRecord.setAttendEvening(today);
//                        attendMapper.updateByPrimaryKeySelective(todayRecord);
//                    }

                }
            }



//            attendMapper.insertSelective(attend);
        } catch (Exception e) {
            log.error("用户签到失败",e);
            throw e;
        }
    }

    @Override
    public PageQueryBean listAttend(QueryCondition queryCondition) {
        //根据条件查询 count记录数目
        //如果有记录 才去查询分页数据 没有相关记录数目  没有必要查询分页数据、
       int count= attendMapper.countByCondition(queryCondition);
        PageQueryBean pageResult=new PageQueryBean();
       if (count>0){
           pageResult.setTotalRows(count);
           pageResult.setCurrentPage(queryCondition.getCurrentPage());
           pageResult.setPageSize(queryCondition.getPageSize());
           List<Attend> attends = attendMapper.selectAttendPage(queryCondition);
           pageResult.setItems(attends);

       }
        return pageResult;
    }

    @Override
    @Transactional
    public void checkAttend() {
        //查询缺勤用户ID 插入打卡记录  并且设置为异常 缺勤480分钟
        List<Long> userIdList =attendMapper.selectTodayAbsence();
        if(CollectionUtils.isNotEmpty(userIdList)){
            List<Attend> attendList = new ArrayList<>();
            for(Long userId:userIdList){
                Attend attend = new Attend();
                attend.setUserId(userId);
                attend.setAttendDate(new Date());
                attend.setAttendWeek((byte)DateUtils.getTodayWeek());
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendList.add(attend);
            }
            attendMapper.batchInsert(attendList);
        }
        // 检查晚打卡 将下班未打卡记录设置为异常
        List<Attend> absenceList = attendMapper.selectTodayEveningAbsence();
        if(CollectionUtils.isNotEmpty(absenceList)){
            for(Attend attend : absenceList){
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }

    }
}
