/**
 * IntellectualPropertyUpdateFrame.java
 * 该类用于创建知识产权更新窗口，实现更新知识产权数据功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.IntellectualProperty;
import com.achievement.service.IntellectualPropertyService;
import com.achievement.service.impl.IntellectualPropertyServiceImpl;
import com.achievement.dao.IntellectualPropertyDAO;
import com.achievement.dao.impl.IntellectualPropertyDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntellectualPropertyUpdateFrame extends JFrame {
    private User currentUser;
    private IntellectualPropertyService intellectualPropertyService;
    private IntellectualPropertyPanel parentPanel;
    private IntellectualProperty ip;
    
    private JTextField nameField;
    private JComboBox<String> typeComboBox;
    private JTextField applicationNumberField;
    private JTextField authorizationNumberField;
    private JTextField applicationDateField;
    private JTextField authorizationDateField;
    private JComboBox<String> statusComboBox;
    private JTextArea remarksTextArea;
    
    public IntellectualPropertyUpdateFrame(User currentUser, IntellectualPropertyPanel parentPanel, IntellectualProperty ip) {
        this.currentUser = currentUser;
        this.parentPanel = parentPanel;
        this.ip = ip;
        
        // 初始化服务层
        IntellectualPropertyDAO intellectualPropertyDAO = new IntellectualPropertyDAOImpl();
        intellectualPropertyService = new IntellectualPropertyServiceImpl(intellectualPropertyDAO);
        
        // 设置窗口属性
        setTitle("修改专利/软著");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentPanel);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件
        mainPanel.add(new JLabel("名称:"));
        nameField = new JTextField();
        mainPanel.add(nameField);
        
        mainPanel.add(new JLabel("类型:"));
        String[] types = {"专利", "软著"};
        typeComboBox = new JComboBox<>(types);
        mainPanel.add(typeComboBox);
        
        mainPanel.add(new JLabel("申请号:"));
        applicationNumberField = new JTextField();
        mainPanel.add(applicationNumberField);
        
        mainPanel.add(new JLabel("授权号:"));
        authorizationNumberField = new JTextField();
        mainPanel.add(authorizationNumberField);
        
        mainPanel.add(new JLabel("申请时间 (yyyy-MM-dd):"));
        applicationDateField = new JTextField();
        mainPanel.add(applicationDateField);
        
        mainPanel.add(new JLabel("授权时间 (yyyy-MM-dd):"));
        authorizationDateField = new JTextField();
        mainPanel.add(authorizationDateField);
        
        mainPanel.add(new JLabel("状态:"));
        String[] statuses = {"申请中", "已授权", "驳回"};
        statusComboBox = new JComboBox<>(statuses);
        mainPanel.add(statusComboBox);
        
        mainPanel.add(new JLabel("备注:"));
        remarksTextArea = new JTextArea(3, 20);
        remarksTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(remarksTextArea);
        mainPanel.add(scrollPane);
        
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
        
        // 填充现有数据
        fillData();
        
        // 设置可见
        setVisible(true);
    }
    
    // 填充现有数据
    private void fillData() {
        nameField.setText(ip.getName());
        typeComboBox.setSelectedItem(ip.getType());
        applicationNumberField.setText(ip.getApplicationNumber());
        authorizationNumberField.setText(ip.getAuthorizationNumber());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        applicationDateField.setText(ip.getApplicationDate() != null ? sdf.format(ip.getApplicationDate()) : "");
        authorizationDateField.setText(ip.getAuthorizationDate() != null ? sdf.format(ip.getAuthorizationDate()) : "");
        
        statusComboBox.setSelectedItem(ip.getStatus());
        remarksTextArea.setText(ip.getRemarks());
    }
    
    // 处理保存逻辑
    private void handleSave() {
        String name = nameField.getText().trim();
        String type = (String) typeComboBox.getSelectedItem();
        String applicationNumber = applicationNumberField.getText().trim();
        String authorizationNumber = authorizationNumberField.getText().trim();
        String applicationDateStr = applicationDateField.getText().trim();
        String authorizationDateStr = authorizationDateField.getText().trim();
        String status = (String) statusComboBox.getSelectedItem();
        String remarks = remarksTextArea.getText().trim();
        
        // 验证输入
        if (name.isEmpty() || type.isEmpty() || applicationNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "名称、类型和申请号不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 解析日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date applicationDate = null;
            if (!applicationDateStr.isEmpty()) {
                applicationDate = sdf.parse(applicationDateStr);
            }
            
            Date authorizationDate = null;
            if (!authorizationDateStr.isEmpty()) {
                authorizationDate = sdf.parse(authorizationDateStr);
            }
            
            // 更新专利/软著信息
            ip.setName(name);
            ip.setType(type);
            ip.setApplicationNumber(applicationNumber);
            ip.setAuthorizationNumber(authorizationNumber);
            ip.setApplicationDate(applicationDate);
            ip.setAuthorizationDate(authorizationDate);
            ip.setStatus(status);
            ip.setRemarks(remarks);
            
            // 保存到数据库
            intellectualPropertyService.updateIntellectualProperty(ip);
            
            JOptionPane.showMessageDialog(this, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            
            // 刷新父面板数据
            parentPanel.loadIntellectualProperties();
            
            // 关闭窗口
            dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "日期格式错误，请使用yyyy-MM-dd格式", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "修改失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}