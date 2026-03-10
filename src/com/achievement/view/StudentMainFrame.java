/**
 * StudentMainFrame.java
 * 该类用于创建学生主界面框架
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.Reminder;
import com.achievement.service.ReminderService;
import com.achievement.service.impl.ReminderServiceImpl;
import com.achievement.service.UserService;
import com.achievement.service.impl.UserServiceImpl;
import com.achievement.dao.ReminderDAO;
import com.achievement.dao.impl.ReminderDAOImpl;
import com.achievement.dao.UserDAO;
import com.achievement.dao.impl.UserDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StudentMainFrame extends JFrame {
    private User currentUser;
    private ReminderService reminderService;
    private UserService userService;
    private JTabbedPane tabbedPane;
    
    public StudentMainFrame(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        ReminderDAO reminderDAO = new ReminderDAOImpl();
        reminderService = new ReminderServiceImpl(reminderDAO);
        
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);
        
        // 设置窗口属性
        setTitle("学生成果管理系统 - 学生端");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        
        // 文件菜单
        JMenu fileMenu = new JMenu("文件");
        JMenuItem logoutMenuItem = new JMenuItem("退出登录");
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前窗口
                new LoginFrame(); // 打开登录界面
            }
        });
        fileMenu.add(logoutMenuItem);
        
        JMenuItem exitMenuItem = new JMenuItem("退出");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        
        // 个人信息菜单
        JMenu infoMenu = new JMenu("个人信息");
        JMenuItem viewInfoMenuItem = new JMenuItem("查看信息");
        viewInfoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserInfo();
            }
        });
        JMenuItem changePasswordMenuItem = new JMenuItem("修改密码");
        changePasswordMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
        infoMenu.add(viewInfoMenuItem);
        infoMenu.add(changePasswordMenuItem);
        menuBar.add(infoMenu);
        
        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StudentMainFrame.this, "学生成果管理系统 v1.0\n用于管理高校学生学科竞赛、大创项目及学术成果", "关于", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);
        
        // 设置菜单栏
        setJMenuBar(menuBar);
        
        // 创建标签面板
        tabbedPane = new JTabbedPane();
        
        // 添加竞赛获奖标签
        ContestAwardPanel contestAwardPanel = new ContestAwardPanel(currentUser);
        tabbedPane.addTab("竞赛获奖", contestAwardPanel);
        
        // 添加大创项目标签
        InnovationProjectPanel innovationProjectPanel = new InnovationProjectPanel(currentUser);
        tabbedPane.addTab("大创项目", innovationProjectPanel);
        
        // 添加论文发表标签
        PaperPublicationPanel paperPublicationPanel = new PaperPublicationPanel(currentUser);
        tabbedPane.addTab("论文发表", paperPublicationPanel);
        
        // 添加专利/软著标签
        IntellectualPropertyPanel intellectualPropertyPanel = new IntellectualPropertyPanel(currentUser);
        tabbedPane.addTab("专利/软著", intellectualPropertyPanel);
        
        // 添加提醒标签
        ReminderPanel reminderPanel = new ReminderPanel(currentUser);
        tabbedPane.addTab("提醒", reminderPanel);
        
        // 添加标签面板到主面板
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // 添加到主窗口
        add(mainPanel);
        
        // 检查未读提醒
        checkUnreadReminders();
        
        // 设置可见
        setVisible(true);
    }
    
    // 显示用户信息
    private void showUserInfo() {
        String info = "用户信息\n" +
                     "用户名: " + currentUser.getUsername() + "\n" +
                     "姓名: " + currentUser.getName() + "\n" +
                     "院系: " + currentUser.getDepartment() + "\n" +
                     "专业: " + currentUser.getMajor() + "\n" +
                     "年级: " + currentUser.getGrade() + "\n" +
                     "角色: " + currentUser.getRole();
        JOptionPane.showMessageDialog(this, info, "个人信息", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // 修改密码
    private void changePassword() {
        // 创建修改密码对话框
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        
        panel.add(new JLabel("旧密码:"));
        panel.add(oldPasswordField);
        panel.add(new JLabel("新密码:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("确认新密码:"));
        panel.add(confirmPasswordField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "修改密码", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // 验证输入
            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "所有密码字段不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "两次输入的新密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // 验证旧密码
                User user = userService.login(currentUser.getUsername(), oldPassword);
                if (user != null) {
                    // 更新密码
                    user.setPassword(newPassword);
                    userService.updateUser(user);
                    JOptionPane.showMessageDialog(this, "密码修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "旧密码错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "修改密码失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // 检查未读提醒
    private void checkUnreadReminders() {
        try {
            List<Reminder> unreadReminders = reminderService.getUnreadRemindersByUserId(currentUser.getId());
            if (!unreadReminders.isEmpty()) {
                JOptionPane.showMessageDialog(this, "您有 " + unreadReminders.size() + " 条未读提醒", "未读提醒", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "获取提醒失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}