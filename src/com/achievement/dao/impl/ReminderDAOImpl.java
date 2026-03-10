/**
 * ReminderDAOImpl.java
 * 该类用于实现提醒数据的数据库访问操作
 */
package com.achievement.dao.impl;

import com.achievement.dao.ReminderDAO;
import com.achievement.model.Reminder;
import com.achievement.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReminderDAOImpl implements ReminderDAO {
    @Override
    public void addReminder(Reminder reminder) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO reminder (user_id, title, content, reminder_time, is_read) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reminder.getUserId());
            ps.setString(2, reminder.getTitle());
            ps.setString(3, reminder.getContent());
            ps.setTimestamp(4, new java.sql.Timestamp(reminder.getReminderTime().getTime()));
            ps.setBoolean(5, reminder.isRead());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加提醒失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    @Override
    public Reminder getReminderById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM reminder WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToReminder(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取提醒失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<Reminder> getRemindersByUserId(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reminder> reminders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM reminder WHERE user_id = ? ORDER BY reminder_time DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                reminders.add(mapResultSetToReminder(rs));
            }
            return reminders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取提醒列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<Reminder> getUnreadRemindersByUserId(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reminder> reminders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM reminder WHERE user_id = ? AND is_read = ? ORDER BY reminder_time DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setBoolean(2, false);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                reminders.add(mapResultSetToReminder(rs));
            }
            return reminders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取未读提醒列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public void markReminderAsRead(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE reminder SET is_read = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, true);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新提醒状态失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    @Override
    public void deleteReminder(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM reminder WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除提醒失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    @Override
    public List<Reminder> getRemindersByTimeRange(Date startTime, Date endTime) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reminder> reminders = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM reminder WHERE reminder_time BETWEEN ? AND ? ORDER BY reminder_time DESC";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
            rs = ps.executeQuery();
            
            while (rs.next()) {
                reminders.add(mapResultSetToReminder(rs));
            }
            return reminders;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取时间范围内的提醒失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    // 辅助方法：将ResultSet映射为Reminder对象
    private Reminder mapResultSetToReminder(ResultSet rs) throws SQLException {
        Reminder reminder = new Reminder();
        reminder.setId(rs.getInt("id"));
        reminder.setUserId(rs.getInt("user_id"));
        reminder.setTitle(rs.getString("title"));
        reminder.setContent(rs.getString("content"));
        reminder.setReminderTime(rs.getTimestamp("reminder_time"));
        reminder.setRead(rs.getBoolean("is_read"));
        reminder.setCreatedAt(rs.getTimestamp("created_at"));
        reminder.setUpdatedAt(rs.getTimestamp("updated_at"));
        return reminder;
    }
}