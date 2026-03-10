/**
 * ReminderServiceImpl.java
 * 该类用于实现提醒数据的业务逻辑操作
 */
package com.achievement.service.impl;

import com.achievement.dao.ReminderDAO;
import com.achievement.model.Reminder;
import com.achievement.service.ReminderService;

import java.util.List;
import java.util.Date;

public class ReminderServiceImpl implements ReminderService {
    private ReminderDAO reminderDAO;
    
    /**
     * 构造方法
     * <p>初始化ReminderServiceImpl对象，使用指定的ReminderDAO实例</p>
     * 
     * @param reminderDAO ReminderDAO接口的实现类实例
     */
    public ReminderServiceImpl(ReminderDAO reminderDAO) {
        this.reminderDAO = reminderDAO;
    }
    
    /**
     * 添加提醒记录
     * <p>添加新的提醒记录到数据库</p>
     * 
     * @param reminder 提醒对象，包含提醒的所有信息
     */
    @Override
    public void addReminder(Reminder reminder) {
        reminderDAO.addReminder(reminder);
    }
    
    /**
     * 根据ID获取提醒记录
     * <p>通过记录ID查询提醒的详细信息</p>
     * 
     * @param id 提醒记录的唯一标识符
     * @return 提醒对象，如果未找到则返回null
     */
    @Override
    public Reminder getReminderById(Integer id) {
        return reminderDAO.getReminderById(id);
    }
    
    /**
     * 根据用户ID获取提醒记录列表
     * <p>查询指定用户的所有提醒记录</p>
     * 
     * @param userId 用户的唯一标识符
     * @return 提醒记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<Reminder> getRemindersByUserId(Integer userId) {
        return reminderDAO.getRemindersByUserId(userId);
    }
    
    /**
     * 根据用户ID获取未读提醒记录列表
     * <p>查询指定用户的所有未读提醒记录</p>
     * 
     * @param userId 用户的唯一标识符
     * @return 未读提醒记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<Reminder> getUnreadRemindersByUserId(Integer userId) {
        return reminderDAO.getUnreadRemindersByUserId(userId);
    }
    
    /**
     * 将提醒标记为已读
     * <p>根据提醒ID将提醒状态更新为已读</p>
     * 
     * @param id 提醒记录的唯一标识符
     */
    @Override
    public void markReminderAsRead(Integer id) {
        reminderDAO.markReminderAsRead(id);
    }
    
    /**
     * 删除提醒记录
     * <p>根据提醒ID删除提醒记录</p>
     * 
     * @param id 提醒记录的唯一标识符
     */
    @Override
    public void deleteReminder(Integer id) {
        reminderDAO.deleteReminder(id);
    }
    
    /**
     * 根据时间范围获取提醒记录列表
     * <p>查询指定时间范围内的所有提醒记录</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 提醒记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<Reminder> getRemindersByTimeRange(Date startTime, Date endTime) {
        return reminderDAO.getRemindersByTimeRange(startTime, endTime);
    }
}