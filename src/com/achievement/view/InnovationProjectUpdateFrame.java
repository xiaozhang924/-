/**
 * InnovationProjectUpdateFrame.java
 * 该类用于创建创新项目更新窗口，实现更新创新项目数据功能
 */
/**
 * InnovationProjectUpdateFrame.java
 * 该类用于创建创新项目更新窗口，实现更新创新项目数据功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.InnovationProject;
import com.achievement.service.InnovationProjectService;
import com.achievement.service.impl.InnovationProjectServiceImpl;
import com.achievement.dao.InnovationProjectDAO;
import com.achievement.dao.impl.InnovationProjectDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InnovationProjectUpdateFrame extends JFrame {
    private User currentUser;
    private InnovationProjectService innovationProjectService;
    private InnovationProjectPanel parentPanel;
    private InnovationProject project;
    
    private JTextField projectNameField;
    private JTextField projectTypeField;
    private JComboBox<String> projectLevelComboBox;
    private JComboBox<String> projectStatusComboBox;
    private JTextField startYearField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JComboBox<String> conclusionStatusComboBox;
    private JTextField projectLeaderField;
    private JTextField teamMembersField;
    private JTextField roleField;
    private JTextField advisorField;
    private JTextArea remarksTextArea;
    
    public InnovationProjectUpdateFrame(User currentUser, InnovationProjectPanel parentPanel, InnovationProject project) {
        this.currentUser = currentUser;
        this.parentPanel = parentPanel;
        this.project = project;
        
        // 初始化服务层
        InnovationProjectDAO innovationProjectDAO = new InnovationProjectDAOImpl();
        innovationProjectService = new InnovationProjectServiceImpl(innovationProjectDAO);
        
        // 设置窗口属性
        setTitle("修改大创项目");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentPanel);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridLayout(13, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件
        mainPanel.add(new JLabel("项目名称:"));
        projectNameField = new JTextField();
        mainPanel.add(projectNameField);
        
        mainPanel.add(new JLabel("项目类型:"));
        projectTypeField = new JTextField();
        mainPanel.add(projectTypeField);
        
        mainPanel.add(new JLabel("项目级别:"));
        String[] projectLevels = {"国家级", "省级", "校级", "院级"};
        projectLevelComboBox = new JComboBox<>(projectLevels);
        mainPanel.add(projectLevelComboBox);
        
        mainPanel.add(new JLabel("项目状态:"));
        String[] projectStatuses = {"进行中", "已结题", "已中止"};
        projectStatusComboBox = new JComboBox<>(projectStatuses);
        mainPanel.add(projectStatusComboBox);
        
        mainPanel.add(new JLabel("开始年份:"));
        startYearField = new JTextField();
        mainPanel.add(startYearField);
        
        mainPanel.add(new JLabel("开始时间 (yyyy-MM-dd):"));
        startTimeField = new JTextField();
        mainPanel.add(startTimeField);
        
        mainPanel.add(new JLabel("结束时间 (yyyy-MM-dd):"));
        endTimeField = new JTextField();
        mainPanel.add(endTimeField);
        
        mainPanel.add(new JLabel("结题状态:"));
        String[] conclusionStatuses = {"优秀", "良好", "合格", "不合格"};
        conclusionStatusComboBox = new JComboBox<>(conclusionStatuses);
        mainPanel.add(conclusionStatusComboBox);
        
        mainPanel.add(new JLabel("项目负责人:"));
        projectLeaderField = new JTextField();
        mainPanel.add(projectLeaderField);
        
        mainPanel.add(new JLabel("团队成员:"));
        teamMembersField = new JTextField();
        mainPanel.add(teamMembersField);
        
        mainPanel.add(new JLabel("担任角色:"));
        roleField = new JTextField();
        mainPanel.add(roleField);
        
        mainPanel.add(new JLabel("指导教师:"));
        advisorField = new JTextField();
        mainPanel.add(advisorField);
        
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
        projectNameField.setText(project.getProjectName());
        projectTypeField.setText(project.getProjectType());
        projectLevelComboBox.setSelectedItem(project.getProjectLevel());
        projectStatusComboBox.setSelectedItem(project.getProjectStatus());
        startYearField.setText(project.getStartYear() != null ? project.getStartYear().toString() : "");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        startTimeField.setText(project.getStartTime() != null ? sdf.format(project.getStartTime()) : "");
        endTimeField.setText(project.getEndTime() != null ? sdf.format(project.getEndTime()) : "");
        
        conclusionStatusComboBox.setSelectedItem(project.getConclusionStatus());
        projectLeaderField.setText(project.getProjectLeader());
        teamMembersField.setText(project.getTeamMembers());
        roleField.setText(project.getRole());
        advisorField.setText(project.getAdvisor());
        remarksTextArea.setText(project.getRemarks());
    }
    
    // 处理保存逻辑
    private void handleSave() {
        String projectName = projectNameField.getText().trim();
        String projectType = projectTypeField.getText().trim();
        String projectLevel = (String) projectLevelComboBox.getSelectedItem();
        String projectStatus = (String) projectStatusComboBox.getSelectedItem();
        String startYearStr = startYearField.getText().trim();
        String startTimeStr = startTimeField.getText().trim();
        String endTimeStr = endTimeField.getText().trim();
        String conclusionStatus = (String) conclusionStatusComboBox.getSelectedItem();
        String projectLeader = projectLeaderField.getText().trim();
        String teamMembers = teamMembersField.getText().trim();
        String role = roleField.getText().trim();
        String advisor = advisorField.getText().trim();
        String remarks = remarksTextArea.getText().trim();
        
        // 验证输入
        if (projectName.isEmpty() || projectType.isEmpty() || startYearStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "项目名称、类型和开始年份不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 解析日期和数字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Integer startYear = Integer.parseInt(startYearStr);
            Date startTime = null;
            Date endTime = null;
            
            if (!startTimeStr.isEmpty()) {
                startTime = sdf.parse(startTimeStr);
            }
            
            if (!endTimeStr.isEmpty()) {
                endTime = sdf.parse(endTimeStr);
            }
            
            // 更新项目信息
            project.setProjectName(projectName);
            project.setProjectType(projectType);
            project.setProjectLevel(projectLevel);
            project.setProjectStatus(projectStatus);
            project.setStartYear(startYear);
            project.setStartTime(startTime);
            project.setEndTime(endTime);
            project.setConclusionStatus(conclusionStatus);
            project.setProjectLeader(projectLeader);
            project.setTeamMembers(teamMembers);
            project.setRole(role);
            project.setAdvisor(advisor);
            project.setRemarks(remarks);
            
            // 保存到数据库
            innovationProjectService.updateInnovationProject(project);
            
            JOptionPane.showMessageDialog(this, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            
            // 刷新父面板数据
            parentPanel.loadInnovationProjects();
            
            // 关闭窗口
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "开始年份格式错误", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "日期格式错误，请使用yyyy-MM-dd格式", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "修改失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}