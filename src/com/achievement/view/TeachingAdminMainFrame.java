/**
 * TeachingAdminMainFrame.java
 * 该类用于创建教学管理员主界面框架
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.view.IntellectualPropertyManagementPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeachingAdminMainFrame extends JFrame {
    private User currentUser;
    private JTabbedPane tabbedPane;
    
    public TeachingAdminMainFrame(User currentUser) {
        this.currentUser = currentUser;
        
        // 设置窗口属性
        setTitle("学生成果管理系统 - 教学教务部门端");
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
        
        // 管理菜单
        JMenu manageMenu = new JMenu("管理");
        JMenuItem userManageMenuItem = new JMenuItem("用户管理");
        userManageMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 实现用户管理功能
                JOptionPane.showMessageDialog(TeachingAdminMainFrame.this, "用户管理功能待实现", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        manageMenu.add(userManageMenuItem);
        menuBar.add(manageMenu);
        
        // 帮助菜单
        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aboutMenuItem = new JMenuItem("关于");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(TeachingAdminMainFrame.this, "学生成果管理系统 v1.0\n用于管理高校学生学科竞赛、大创项目及学术成果", "关于", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);
        
        // 设置菜单栏
        setJMenuBar(menuBar);
        
        // 创建标签面板
        tabbedPane = new JTabbedPane();
        
        // 添加竞赛获奖管理标签
        ContestAwardManagementPanel contestAwardManagementPanel = new ContestAwardManagementPanel();
        tabbedPane.addTab("竞赛获奖管理", contestAwardManagementPanel);
        
        // 添加大创项目管理标签
        InnovationProjectManagementPanel innovationProjectManagementPanel = new InnovationProjectManagementPanel();
        tabbedPane.addTab("大创项目管理", innovationProjectManagementPanel);
        
        // 添加论文发表管理标签
        PaperPublicationManagementPanel paperPublicationManagementPanel = new PaperPublicationManagementPanel();
        tabbedPane.addTab("论文发表管理", paperPublicationManagementPanel);
        
        // 添加专利/软著管理标签
        IntellectualPropertyManagementPanel intellectualPropertyManagementPanel = new IntellectualPropertyManagementPanel();
        tabbedPane.addTab("专利/软著管理", intellectualPropertyManagementPanel);
        
        // 添加报表导出标签
        ReportExportPanel reportExportPanel = new ReportExportPanel();
        tabbedPane.addTab("报表导出", reportExportPanel);
        
        // 添加标签面板到主面板
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // 添加到主窗口
        add(mainPanel);
        
        // 设置可见
        setVisible(true);
    }
}