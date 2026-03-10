/**
 * RegisterFrame.java
 * 该类用于创建注册界面框架
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

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nameField;
    private JTextField studentNumberField;  // 学号字段
    private JTextField departmentField;
    private JTextField majorField;
    private JTextField gradeField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton;
    private JButton cancelButton;
    private UserService userService;
    private JFrame parentFrame;
    
    public RegisterFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        
        // 初始化服务层
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);
        
        // 设置窗口属性
        setTitle("学生成果管理系统 - 注册");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(9, 2, 10, 10));  // 改为9行
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件
        mainPanel.add(new JLabel("用户名:"));
        usernameField = new JTextField();
        mainPanel.add(usernameField);
        
        mainPanel.add(new JLabel("密码:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);
        
        mainPanel.add(new JLabel("确认密码:"));
        confirmPasswordField = new JPasswordField();
        mainPanel.add(confirmPasswordField);
        
        mainPanel.add(new JLabel("姓名:"));
        nameField = new JTextField();
        mainPanel.add(nameField);
        
        mainPanel.add(new JLabel("学号(10位数字):"));
        studentNumberField = new JTextField();
        mainPanel.add(studentNumberField);
        
        mainPanel.add(new JLabel("院系:"));
        departmentField = new JTextField();
        mainPanel.add(departmentField);
        
        mainPanel.add(new JLabel("专业:"));
        majorField = new JTextField();
        mainPanel.add(majorField);
        
        mainPanel.add(new JLabel("年级:"));
        gradeField = new JTextField();
        mainPanel.add(gradeField);
        
        mainPanel.add(new JLabel("角色:"));
        String[] roles = {"学生", "教师", "管理员"};
        roleComboBox = new JComboBox<>(roles);
        mainPanel.add(roleComboBox);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        registerButton = new JButton("注册");
        cancelButton = new JButton("取消");
        
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        
        // 添加事件监听
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegister();
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
    
    // 处理注册逻辑
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String name = nameField.getText().trim();
        String studentNumber = studentNumberField.getText().trim();
        String department = departmentField.getText().trim();
        String major = majorField.getText().trim();
        String grade = gradeField.getText().trim();
        String role = (String) roleComboBox.getSelectedItem();
        
        // 验证输入
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || department.isEmpty() || major.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 如果是学生角色，学号必填
        if ("学生".equals(role) && studentNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "学生角色必须填写学号", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // 验证学号格式：必须是10位数字
        if ("学生".equals(role) && !studentNumber.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "学号格式不正确，必须是10位数字", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 创建用户对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRealName(name);
            user.setStudentNumber("学生".equals(role) ? studentNumber : null);  // 仅学生设置学号
            user.setDepartment(department);
            user.setMajor(major);
            user.setGrade(grade);
            user.setRole(role);
            
            // 调用服务层注册用户
            userService.register(user);
            
            JOptionPane.showMessageDialog(this, "注册成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "注册失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}