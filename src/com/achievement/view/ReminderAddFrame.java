/**
 * ReminderAddFrame.java
 * 该类用于创建提醒添加窗口，实现添加提醒数据功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.Reminder;
import com.achievement.service.ReminderService;
import com.achievement.service.impl.ReminderServiceImpl;
import com.achievement.dao.ReminderDAO;
import com.achievement.dao.impl.ReminderDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReminderAddFrame extends JFrame {
    private User currentUser;
    private ReminderService reminderService;
    private ReminderPanel parentPanel;
    
    private JTextField titleField;
    private JTextArea contentTextArea;
    private JTextField reminderTimeField;
    
    public ReminderAddFrame(User currentUser, ReminderPanel parentPanel) {
        this.currentUser = currentUser;
        this.parentPanel = parentPanel;
        
        // 初始化服务层
        ReminderDAO reminderDAO = new ReminderDAOImpl();
        reminderService = new ReminderServiceImpl(reminderDAO);
        
        // 设置窗口属性
        setTitle("添加提醒");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentPanel);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // 添加组件
        mainPanel.add(new JLabel("标题:"));
        titleField = new JTextField();
        mainPanel.add(titleField);
        
        mainPanel.add(new JLabel("内容:"));
        contentTextArea = new JTextArea(5, 20);
        contentTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(contentTextArea);
        mainPanel.add(scrollPane);
        
        mainPanel.add(new JLabel("提醒时间 (yyyy-MM-dd HH:mm):"));
        reminderTimeField = new JTextField();
        mainPanel.add(reminderTimeField);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // 添加事件监听
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // 添加到主窗口
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 设置可见
        setVisible(true);
    }
    
    // 处理保存逻辑
    private void handleSave() {
        String title = titleField.getText().trim();
        String content = contentTextArea.getText().trim();
        String reminderTimeStr = reminderTimeField.getText().trim();
        
        // 验证输入
        if (title.isEmpty() || content.isEmpty() || reminderTimeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "标题、内容和提醒时间不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 解析日期时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date reminderTime = sdf.parse(reminderTimeStr);
            
            // 创建提醒对象
            Reminder reminder = new Reminder();
            reminder.setUserId(currentUser.getId());
            reminder.setTitle(title);
            reminder.setContent(content);
            reminder.setReminderTime(reminderTime);
            reminder.setRead(false);
            
            // 保存到数据库
            reminderService.addReminder(reminder);
            
            JOptionPane.showMessageDialog(this, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            
            // 刷新父面板数据
            parentPanel.loadReminders();
            
            // 关闭窗口
            dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "日期时间格式错误，请使用yyyy-MM-dd HH:mm格式", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "添加失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}