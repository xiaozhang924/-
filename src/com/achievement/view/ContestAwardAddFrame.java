/**
 * ContestAwardAddFrame.java
 * 该类用于创建竞赛获奖添加窗口，实现添加竞赛获奖数据功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.ContestAward;
import com.achievement.service.ContestAwardService;
import com.achievement.service.impl.ContestAwardServiceImpl;
import com.achievement.dao.ContestAwardDAO;
import com.achievement.dao.impl.ContestAwardDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContestAwardAddFrame extends JFrame {
    private User currentUser;
    private ContestAwardService contestAwardService;
    private ContestAwardPanel parentPanel;
    
    private JTextField contestNameField;
    private JComboBox<String> contestLevelComboBox;
    private JComboBox<String> awardLevelComboBox;
    private JTextField awardTimeField;
    private JTextField certificatePathField;
    private JTextArea remarksTextArea;
    
    public ContestAwardAddFrame(ContestAwardPanel parentPanel, User currentUser) {
        this.parentPanel = parentPanel;
        this.currentUser = currentUser;
        
        // 初始化服务层
        ContestAwardDAO contestAwardDAO = new ContestAwardDAOImpl();
        contestAwardService = new ContestAwardServiceImpl(contestAwardDAO);
        
        // 设置窗口属性
        setTitle("添加竞赛获奖");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentPanel);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件
        mainPanel.add(new JLabel("竞赛名称:"));
        contestNameField = new JTextField();
        mainPanel.add(contestNameField);
        
        mainPanel.add(new JLabel("竞赛级别:"));
        String[] contestLevels = {"国家级", "省级", "市级", "校级", "院级"};
        contestLevelComboBox = new JComboBox<>(contestLevels);
        mainPanel.add(contestLevelComboBox);
        
        mainPanel.add(new JLabel("获奖等级:"));
        String[] awardLevels = {"一等奖", "二等奖", "三等奖", "优秀奖", "参与奖"};
        awardLevelComboBox = new JComboBox<>(awardLevels);
        mainPanel.add(awardLevelComboBox);
        
        mainPanel.add(new JLabel("获奖时间 (yyyy-MM-dd):"));
        awardTimeField = new JTextField();
        mainPanel.add(awardTimeField);
        
        mainPanel.add(new JLabel("获奖证书路径:"));
        certificatePathField = new JTextField();
        mainPanel.add(certificatePathField);
        
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
        
        // 设置可见
        setVisible(true);
    }
    
    // 处理保存逻辑
    private void handleSave() {
        String contestName = contestNameField.getText().trim();
        String contestLevel = (String) contestLevelComboBox.getSelectedItem();
        String awardLevel = (String) awardLevelComboBox.getSelectedItem();
        String awardTimeStr = awardTimeField.getText().trim();
        String certificatePath = certificatePathField.getText().trim();
        String remarks = remarksTextArea.getText().trim();
        
        // 验证输入
        if (contestName.isEmpty() || awardTimeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "竞赛名称和获奖时间不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 解析日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date awardTime = sdf.parse(awardTimeStr);
            
            // 创建竞赛获奖对象
            ContestAward contestAward = new ContestAward();
            contestAward.setStudentId(currentUser.getId());
            contestAward.setContestName(contestName);
            contestAward.setContestLevel(contestLevel);
            contestAward.setAwardLevel(awardLevel);
            contestAward.setAwardTime(awardTime);
            contestAward.setCertificatePath(certificatePath);
            contestAward.setRemarks(remarks);
            
            // 保存到数据库
            contestAwardService.addContestAward(contestAward);
            
            JOptionPane.showMessageDialog(this, "添加成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            
            // 刷新父面板数据
            parentPanel.loadContestAwards();
            
            // 关闭窗口
            dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "获奖时间格式错误，请使用yyyy-MM-dd格式", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "添加失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}