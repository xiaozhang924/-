/**
 * StatisticalAnalysisPanel.java
 * 该类用于创建统计分析面板，实现多维度统计分析功能
 */
package com.achievement.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticalAnalysisPanel extends JPanel {
    
    // 条件输入组件
    private JTextField departmentField;
    private JTextField majorField;
    private JTextField gradeField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JTextArea resultArea;
    
    // 服务层对象
    private com.achievement.service.ContestAwardService contestAwardService;
    private com.achievement.service.InnovationProjectService innovationProjectService;
    private com.achievement.service.IntellectualPropertyService intellectualPropertyService;
    private com.achievement.service.PaperPublicationService paperPublicationService;
    
    public StatisticalAnalysisPanel() {
        // 初始化服务层对象
        contestAwardService = new com.achievement.service.impl.ContestAwardServiceImpl();
        innovationProjectService = new com.achievement.service.impl.InnovationProjectServiceImpl();
        intellectualPropertyService = new com.achievement.service.impl.IntellectualPropertyServiceImpl();
        paperPublicationService = new com.achievement.service.impl.PaperPublicationServiceImpl();
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建标题
        JLabel titleLabel = new JLabel("统计分析功能", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        
        // 创建左侧面板（条件输入 + 统计按钮）
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        
        // 创建条件输入面板
        JPanel conditionPanel = new JPanel();
        conditionPanel.setLayout(new GridLayout(5, 2, 10, 10));
        conditionPanel.setBorder(BorderFactory.createTitledBorder("查询条件"));
        
        departmentField = new JTextField();
        majorField = new JTextField();
        gradeField = new JTextField();
        startTimeField = new JTextField();
        endTimeField = new JTextField();
        
        conditionPanel.add(new JLabel("院系:"));
        conditionPanel.add(departmentField);
        conditionPanel.add(new JLabel("专业:"));
        conditionPanel.add(majorField);
        conditionPanel.add(new JLabel("年级:"));
        conditionPanel.add(gradeField);
        conditionPanel.add(new JLabel("开始时间(yyyy-MM-dd):"));
        conditionPanel.add(startTimeField);
        conditionPanel.add(new JLabel("结束时间(yyyy-MM-dd):"));
        conditionPanel.add(endTimeField);
        
        // 创建统计按钮面板
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        JButton contestAwardStatsButton = new JButton("竞赛获奖统计");
        JButton projectStatsButton = new JButton("大创项目统计");
        JButton paperStatsButton = new JButton("论文发表统计");
        JButton ipStatsButton = new JButton("知识产权统计");
        
        // 设置按钮大小
        Dimension buttonSize = new Dimension(200, 40);
        contestAwardStatsButton.setPreferredSize(buttonSize);
        projectStatsButton.setPreferredSize(buttonSize);
        paperStatsButton.setPreferredSize(buttonSize);
        ipStatsButton.setPreferredSize(buttonSize);
        
        // 添加事件监听
        contestAwardStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performContestAwardStats();
            }
        });
        
        projectStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performInnovationProjectStats();
            }
        });
        
        paperStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performPaperPublicationStats();
            }
        });
        
        ipStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performIntellectualPropertyStats();
            }
        });
        
        // 将按钮添加到按钮面板
        buttonPanel.add(contestAwardStatsButton);
        buttonPanel.add(projectStatsButton);
        buttonPanel.add(paperStatsButton);
        buttonPanel.add(ipStatsButton);
        
        // 将条件面板和按钮面板添加到左侧面板
        leftPanel.add(conditionPanel);
        leftPanel.add(buttonPanel);
        
        // 创建统计结果显示区域
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        // 添加组件到主面板
        add(titleLabel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // 显示消息
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // 解析日期
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            showMessage("日期格式错误，请使用yyyy-MM-dd格式");
            return null;
        }
    }
    
    // 竞赛获奖统计
    private void performContestAwardStats() {
        try {
            String department = departmentField.getText().trim();
            String major = majorField.getText().trim();
            String grade = gradeField.getText().trim();
            Date startTime = parseDate(startTimeField.getText().trim());
            Date endTime = parseDate(endTimeField.getText().trim());
            
            java.util.List<com.achievement.model.ContestAward> awards = contestAwardService.getContestAwardsByConditions(
                department.isEmpty() ? null : department,
                major.isEmpty() ? null : major,
                grade.isEmpty() ? null : grade,
                startTime,
                endTime,
                null
            );
            
            StringBuilder stats = new StringBuilder();
            stats.append("=== 竞赛获奖统计结果 ===\n\n");
            stats.append("总获奖数量: " + awards.size() + "\n\n");
            
            // 按级别统计
            java.util.Map<String, Integer> levelStats = new java.util.HashMap<>();
            for (com.achievement.model.ContestAward award : awards) {
                String level = award.getContestLevel();
                levelStats.put(level, levelStats.getOrDefault(level, 0) + 1);
            }
            
            stats.append("按竞赛级别统计:\n");
            for (java.util.Map.Entry<String, Integer> entry : levelStats.entrySet()) {
                stats.append(entry.getKey() + ": " + entry.getValue() + "项\n");
            }
            
            // 按年份统计
            java.util.Map<Integer, Integer> yearStats = new java.util.HashMap<>();
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            for (com.achievement.model.ContestAward award : awards) {
                String year = yearFormat.format(award.getAwardTime());
                Integer yearInt = Integer.parseInt(year);
                yearStats.put(yearInt, yearStats.getOrDefault(yearInt, 0) + 1);
            }
            
            stats.append("\n按年份统计:\n");
            java.util.List<Integer> years = new java.util.ArrayList<>(yearStats.keySet());
            java.util.Collections.sort(years);
            for (Integer year : years) {
                stats.append(year + "年: " + yearStats.get(year) + "项\n");
            }
            
            resultArea.setText(stats.toString());
        } catch (Exception e) {
            showMessage("统计失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 大创项目统计
    private void performInnovationProjectStats() {
        try {
            String department = departmentField.getText().trim();
            String major = majorField.getText().trim();
            String grade = gradeField.getText().trim();
            Date startTime = parseDate(startTimeField.getText().trim());
            Date endTime = parseDate(endTimeField.getText().trim());
            
            java.util.List<com.achievement.model.InnovationProject> projects = innovationProjectService.getInnovationProjectsByConditions(
                department.isEmpty() ? null : department,
                major.isEmpty() ? null : major,
                grade.isEmpty() ? null : grade,
                startTime,
                endTime,
                null,
                null
            );
            
            StringBuilder stats = new StringBuilder();
            stats.append("=== 大创项目统计结果 ===\n\n");
            stats.append("总项目数量: " + projects.size() + "\n\n");
            
            // 按级别统计
            java.util.Map<String, Integer> levelStats = new java.util.HashMap<>();
            for (com.achievement.model.InnovationProject project : projects) {
                String level = project.getProjectLevel();
                levelStats.put(level, levelStats.getOrDefault(level, 0) + 1);
            }
            
            stats.append("按项目级别统计:\n");
            for (java.util.Map.Entry<String, Integer> entry : levelStats.entrySet()) {
                stats.append(entry.getKey() + ": " + entry.getValue() + "项\n");
            }
            
            // 按状态统计
            java.util.Map<String, Integer> statusStats = new java.util.HashMap<>();
            for (com.achievement.model.InnovationProject project : projects) {
                String status = project.getProjectStatus();
                statusStats.put(status, statusStats.getOrDefault(status, 0) + 1);
            }
            
            stats.append("\n按项目状态统计:\n");
            for (java.util.Map.Entry<String, Integer> entry : statusStats.entrySet()) {
                stats.append(entry.getKey() + ": " + entry.getValue() + "项\n");
            }
            
            resultArea.setText(stats.toString());
        } catch (Exception e) {
            showMessage("统计失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 论文发表统计
    private void performPaperPublicationStats() {
        try {
            String department = departmentField.getText().trim();
            String major = majorField.getText().trim();
            String grade = gradeField.getText().trim();
            Date startTime = parseDate(startTimeField.getText().trim());
            Date endTime = parseDate(endTimeField.getText().trim());
            
            java.util.List<com.achievement.model.PaperPublication> papers = paperPublicationService.getPaperPublicationsByConditions(
                department.isEmpty() ? null : department,
                major.isEmpty() ? null : major,
                grade.isEmpty() ? null : grade,
                startTime,
                endTime,
                null
            );
            
            StringBuilder stats = new StringBuilder();
            stats.append("=== 论文发表统计结果 ===\n\n");
            stats.append("总发表数量: " + papers.size() + "\n\n");
            
            // 按索引状态统计
            java.util.Map<String, Integer> indexingStats = new java.util.HashMap<>();
            for (com.achievement.model.PaperPublication paper : papers) {
                String indexing = paper.getIndexingStatus();
                indexingStats.put(indexing, indexingStats.getOrDefault(indexing, 0) + 1);
            }
            
            stats.append("按索引状态统计:\n");
            for (java.util.Map.Entry<String, Integer> entry : indexingStats.entrySet()) {
                stats.append(entry.getKey() + ": " + entry.getValue() + "篇\n");
            }
            
            // 按年份统计
            java.util.Map<Integer, Integer> yearStats = new java.util.HashMap<>();
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            for (com.achievement.model.PaperPublication paper : papers) {
                String year = yearFormat.format(paper.getPublicationTime());
                Integer yearInt = Integer.parseInt(year);
                yearStats.put(yearInt, yearStats.getOrDefault(yearInt, 0) + 1);
            }
            
            stats.append("\n按年份统计:\n");
            java.util.List<Integer> years = new java.util.ArrayList<>(yearStats.keySet());
            java.util.Collections.sort(years);
            for (Integer year : years) {
                stats.append(year + "年: " + yearStats.get(year) + "篇\n");
            }
            
            resultArea.setText(stats.toString());
        } catch (Exception e) {
            showMessage("统计失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 知识产权统计
    private void performIntellectualPropertyStats() {
        try {
            String department = departmentField.getText().trim();
            String major = majorField.getText().trim();
            String grade = gradeField.getText().trim();
            Date startTime = parseDate(startTimeField.getText().trim());
            Date endTime = parseDate(endTimeField.getText().trim());
            
            java.util.List<com.achievement.model.IntellectualProperty> ips = intellectualPropertyService.getIntellectualPropertiesByConditions(
                department.isEmpty() ? null : department,
                major.isEmpty() ? null : major,
                grade.isEmpty() ? null : grade,
                startTime,
                endTime,
                null,
                null
            );
            
            StringBuilder stats = new StringBuilder();
            stats.append("=== 知识产权统计结果 ===\n\n");
            stats.append("总知识产权数量: " + ips.size() + "\n\n");
            
            // 按类型统计
            java.util.Map<String, Integer> typeStats = new java.util.HashMap<>();
            for (com.achievement.model.IntellectualProperty ip : ips) {
                String type = ip.getType();
                typeStats.put(type, typeStats.getOrDefault(type, 0) + 1);
            }
            
            stats.append("按知识产权类型统计:\n");
            for (java.util.Map.Entry<String, Integer> entry : typeStats.entrySet()) {
                stats.append(entry.getKey() + ": " + entry.getValue() + "项\n");
            }
            
            // 按年份统计
            java.util.Map<Integer, Integer> yearStats = new java.util.HashMap<>();
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            for (com.achievement.model.IntellectualProperty ip : ips) {
                String year = yearFormat.format(ip.getApplicationDate());
                Integer yearInt = Integer.parseInt(year);
                yearStats.put(yearInt, yearStats.getOrDefault(yearInt, 0) + 1);
            }
            
            stats.append("\n按年份统计:\n");
            java.util.List<Integer> years = new java.util.ArrayList<>(yearStats.keySet());
            java.util.Collections.sort(years);
            for (Integer year : years) {
                stats.append(year + "年: " + yearStats.get(year) + "项\n");
            }
            
            resultArea.setText(stats.toString());
        } catch (Exception e) {
            showMessage("统计失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}