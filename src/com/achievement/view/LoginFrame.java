/**
 * LoginFrame.java
 * 该类用于创建登录界面框架
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.service.UserService;
import com.achievement.service.impl.UserServiceImpl;
import com.achievement.dao.UserDAO;
import com.achievement.dao.impl.UserDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;
    
    public LoginFrame() {
        // 初始化服务层
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);
        
        // 设置窗口属性
        setTitle("学生成果管理系统 - 登录");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // 添加组件
        mainPanel.add(new JLabel("用户名:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);
        
        mainPanel.add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("登录");
        registerButton = new JButton("注册");
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        // 添加事件监听
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
            }
        });
        
        // 添加到主窗口
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 设置可见
        setVisible(true);
    }
    
    // 处理登录逻辑
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "用户名和密码不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            User user = userService.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "登录成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                // 根据用户角色打开相应的主界面
                String role = user.getRole();
                if ("学生".equals(role)) {
                    new StudentMainFrame(user);
                } else if ("教师".equals(role)) {
                    new TeachingAdminMainFrame(user);
                } else if ("管理员".equals(role)) {
                    new StudentAffairsMainFrame(user);
                } else {
                    JOptionPane.showMessageDialog(null, "未知角色: " + role + "，请联系系统管理员", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "登录失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 处理注册逻辑
    private void handleRegister() {
        new RegisterFrame(this);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame();
            }
        });
    }
}