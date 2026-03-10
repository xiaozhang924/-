/**
 * ReminderService.java
 * 该接口用于定义提醒数据的业务逻辑操作
 */
package com.achievement.service;

import com.achievement.model.Reminder;

import java.util.List;
import java.util.Date;

public interface ReminderService {
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