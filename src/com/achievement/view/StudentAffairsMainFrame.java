/**
 * StudentAffairsMainFrame.java
 * 该类用于创建学生事务管理员主界面框架
 */
package com.achievement.view;

import com.achievement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentAffairsMainFrame extends JFrame {
    private User currentUser;
    private JTabbedPane tabbedPane;
    
    public StudentAffairsMainFrame(User currentUser) {
        this.currentUser = currentUser;
        
        // 设置窗口属性
        setTitle("学生成果管理系统 - 学生工作部门端");
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
        
        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StudentAffairsMainFrame.this, "学生成果管理系统 v1.0\n用于管理高校学生学科竞赛、大创项目及学术成果", "关于", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);
        
        // 设置菜单栏
        setJMenuBar(menuBar);
        
        // 创建标签面板
        tabbedPane = new JTabbedPane();
        
        // 添加学生成果查询标签
        StudentAchievementQueryPanel studentAchievementQueryPanel = new StudentAchievementQueryPanel(currentUser);
        tabbedPane.addTab("学生成果查询", studentAchievementQueryPanel);
        
        // 添加评奖评优标签
        AwardSelectionPanel awardSelectionPanel = new AwardSelectionPanel();
        tabbedPane.addTab("评奖评优", awardSelectionPanel);
        
        // 添加统计分析标签
        StatisticalAnalysisPanel statisticalAnalysisPanel = new StatisticalAnalysisPanel();
        tabbedPane.addTab("统计分析", statisticalAnalysisPanel);
        
        // 添加标签面板到主面板
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // 添加到主窗口
        add(mainPanel);
        
        // 设置可见
        setVisible(true);
    }
}