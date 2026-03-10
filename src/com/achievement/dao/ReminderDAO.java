/**
 * ReminderDAO.java
 * 该接口用于定义提醒数据的数据库访问操作
 */
package com.achievement.dao;

import com.achievement.model.Reminder;

import java.util.List;
import java.util.Date;

public interface ReminderDAO {
    // 添加提醒
    void addReminder(Reminder reminder);
    
    // 根据ID获取提醒
    Reminder getReminderById(Integer id);
    
    // 根据用户ID获取提醒列表
    List<Reminder> getRemindersByUserId(Integer userId);
    
    // 根据用户ID获取未读提醒列表
    List<Reminder> getUnreadRemindersByUserId(Integer userId);
    
    // 更新提醒为已读
    void markReminderAsRead(Integer id);
    
    // 删除提醒
    void deleteReminder(Integer id);
    
    // 获取指定时间范围内的提醒
    List<Reminder> getRemindersByTimeRange(Date startTime, Date endTime);
}