/**
 * ReminderPanel.java
 * 该类用于创建提醒面板，实现提醒数据的管理功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.Reminder;
import com.achievement.service.ReminderService;
import com.achievement.service.impl.ReminderServiceImpl;
import com.achievement.dao.ReminderDAO;
import com.achievement.dao.impl.ReminderDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReminderPanel extends JPanel {
    private User currentUser;
    private ReminderService reminderService;
    private JTable reminderTable;
    private DefaultTableModel tableModel;
    
    public ReminderPanel(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        ReminderDAO reminderDAO = new ReminderDAOImpl();
        reminderService = new ReminderServiceImpl(reminderDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("添加");
        JButton markAsReadButton = new JButton("标记为已读");
        JButton deleteButton = new JButton("删除");
        JButton refreshButton = new JButton("刷新");
        
        buttonPanel.add(addButton);
        buttonPanel.add(markAsReadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        // 创建表格
        String[] columnNames = {"ID", "标题", "内容", "提醒时间", "状态", "创建时间"};
        tableModel = new DefaultTableModel(columnNames, 0);
        reminderTable = new JTable(tableModel);
        reminderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(reminderTable);
        
        // 添加事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addReminder();
            }
        });
        
        markAsReadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markAsRead();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteReminder();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReminders();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadReminders();
    }
    
    // 加载提醒数据
    public void loadReminders() {
        try {
            List<Reminder> reminders = reminderService.getRemindersByUserId(currentUser.getId());
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Reminder reminder : reminders) {
                Object[] rowData = {
                        reminder.getId(),
                        reminder.getTitle(),
                        reminder.getContent(),
                        reminder.getReminderTime() != null ? sdf.format(reminder.getReminderTime()) : "",
                        reminder.isRead() ? "已读" : "未读",
                        reminder.getCreatedAt() != null ? sdf.format(reminder.getCreatedAt()) : ""
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载提醒数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 标记为已读
    private void markAsRead() {
        int selectedRow = reminderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            reminderService.markReminderAsRead(id);
            JOptionPane.showMessageDialog(this, "标记成功", "提示", JOptionPane.INFORMATION_MESSAGE);
            loadReminders();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "标记失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 添加提醒
    private void addReminder() {
        ReminderAddFrame addFrame = new ReminderAddFrame(currentUser, this);
        addFrame.setVisible(true);
    }
    
    // 删除提醒
    private void deleteReminder() {
        int selectedRow = reminderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这条提醒吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                reminderService.deleteReminder(id);
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadReminders();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}