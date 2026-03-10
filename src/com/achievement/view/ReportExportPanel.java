/**
 * ReportExportPanel.java
 * 该类用于创建报表导出面板，实现数据报表导出功能
 */
package com.achievement.view;

import com.achievement.dao.ContestAwardDAO;
import com.achievement.dao.InnovationProjectDAO;
import com.achievement.dao.IntellectualPropertyDAO;
import com.achievement.dao.PaperPublicationDAO;
import com.achievement.dao.impl.ContestAwardDAOImpl;
import com.achievement.dao.impl.InnovationProjectDAOImpl;
import com.achievement.dao.impl.IntellectualPropertyDAOImpl;
import com.achievement.dao.impl.PaperPublicationDAOImpl;
import com.achievement.model.ContestAward;
import com.achievement.model.InnovationProject;
import com.achievement.model.IntellectualProperty;
import com.achievement.model.PaperPublication;
import com.achievement.service.ContestAwardService;
import com.achievement.service.InnovationProjectService;
import com.achievement.service.IntellectualPropertyService;
import com.achievement.service.PaperPublicationService;
import com.achievement.service.impl.ContestAwardServiceImpl;
import com.achievement.service.impl.InnovationProjectServiceImpl;
import com.achievement.service.impl.IntellectualPropertyServiceImpl;
import com.achievement.service.impl.PaperPublicationServiceImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportExportPanel extends JPanel {
    private ContestAwardService contestAwardService;
    private InnovationProjectService innovationProjectService;
    private PaperPublicationService paperPublicationService;
    private IntellectualPropertyService intellectualPropertyService;
    
    private JComboBox<String> exportTypeCombo;
    private JComboBox<String> formatCombo;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField departmentField;
    private JTextField majorField;
    private JTextField gradeField;
    private JTextField specificField;
    
    public ReportExportPanel() {
        // 初始化DAO
        ContestAwardDAO contestAwardDAO = new ContestAwardDAOImpl();
        InnovationProjectDAO innovationProjectDAO = new InnovationProjectDAOImpl();
        PaperPublicationDAO paperPublicationDAO = new PaperPublicationDAOImpl();
        IntellectualPropertyDAO intellectualPropertyDAO = new IntellectualPropertyDAOImpl();
        
        // 初始化服务
        contestAwardService = new ContestAwardServiceImpl(contestAwardDAO);
        innovationProjectService = new InnovationProjectServiceImpl(innovationProjectDAO);
        paperPublicationService = new PaperPublicationServiceImpl(paperPublicationDAO);
        intellectualPropertyService = new IntellectualPropertyServiceImpl(intellectualPropertyDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建标题
        JLabel titleLabel = new JLabel("报表导出功能", SwingConstants.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        
        // 创建导出选项面板
        JPanel optionsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        optionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // 导出类型选择
        optionsPanel.add(new JLabel("导出类型:"));
        exportTypeCombo = new JComboBox<>(new String[]{
            "竞赛获奖报表", "大创项目报表", "论文发表报表", "知识产权报表"
        });
        optionsPanel.add(exportTypeCombo);
        
        // 院系
        optionsPanel.add(new JLabel("院系:"));
        departmentField = new JTextField(15);
        optionsPanel.add(departmentField);
        
        // 专业
        optionsPanel.add(new JLabel("专业:"));
        majorField = new JTextField(15);
        optionsPanel.add(majorField);
        
        // 年级
        optionsPanel.add(new JLabel("年级:"));
        gradeField = new JTextField(15);
        optionsPanel.add(gradeField);
        
        // 时间范围选择
        optionsPanel.add(new JLabel("时间范围:"));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(new JLabel("从:"));
        startDateField = new JTextField(10);
        datePanel.add(startDateField);
        datePanel.add(new JLabel("到:"));
        endDateField = new JTextField(10);
        datePanel.add(endDateField);
        optionsPanel.add(datePanel);
        
        // 特定条件（根据类型不同显示不同）
        optionsPanel.add(new JLabel("特定条件:"));
        specificField = new JTextField(15);
        optionsPanel.add(specificField);
        
        // 导出格式选择
        optionsPanel.add(new JLabel("导出格式:"));
        formatCombo = new JComboBox<>(new String[]{
            "Excel (.xlsx)", "PDF (.pdf)", "CSV (.csv)"
        });
        optionsPanel.add(formatCombo);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton exportButton = new JButton("导出报表");
        JButton cancelButton = new JButton("取消");
        
        // 设置按钮大小
        Dimension buttonSize = new Dimension(120, 40);
        exportButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        
        // 添加事件监听
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户选择和过滤条件
                String reportType = (String) exportTypeCombo.getSelectedItem();
                String format = (String) formatCombo.getSelectedItem();
                
                // 获取多维度过滤条件
                String department = departmentField.getText().trim();
                String major = majorField.getText().trim();
                String grade = gradeField.getText().trim();
                Date startTime = parseDate(startDateField.getText().trim());
                Date endTime = parseDate(endDateField.getText().trim());
                String specificCondition = specificField.getText().trim();
                
                // 调用导出方法
                try {
                    if (reportType != null && format != null) {
                        if (format.equals("Excel (.xlsx)") || format.equals("CSV (.csv)")) {
                            exportReport(reportType, department, major, grade, startTime, endTime, specificCondition, format);
                        } else {
                            showMessage("其他格式导出功能待实现");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showMessage("导出失败：" + ex.getMessage());
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage("操作已取消");
            }
        });
        
        buttonPanel.add(exportButton);
        buttonPanel.add(cancelButton);
        
        // 添加组件到主面板
        add(titleLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // 显示消息
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "提示", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // 日期解析辅助方法
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
    
    // 通用导出方法
    private void exportReport(String reportType, String department, String major, String grade, Date startTime, Date endTime, String specificCondition, String format) throws Exception {
        // 根据报表类型调用不同的服务获取数据
        switch (reportType) {
            case "竞赛获奖报表":
                exportContestAwardReport(department, major, grade, startTime, endTime, specificCondition, format);
                break;
            case "大创项目报表":
                exportInnovationProjectReport(department, major, grade, startTime, endTime, specificCondition, format);
                break;
            case "论文发表报表":
                exportPaperPublicationReport(department, major, grade, startTime, endTime, specificCondition, format);
                break;
            case "知识产权报表":
                exportIntellectualPropertyReport(department, major, grade, startTime, endTime, specificCondition, format);
                break;
            default:
                showMessage("不支持的报表类型");
        }
    }
    
    // 导出竞赛获奖报表
    private void exportContestAwardReport(String department, String major, String grade, Date startTime, Date endTime, String level, String format) throws Exception {
        // 使用多条件查询获取数据
        List<ContestAward> awards = contestAwardService.getContestAwardsByConditions(
            department.isEmpty() ? null : department,
            major.isEmpty() ? null : major,
            grade.isEmpty() ? null : grade,
            startTime,
            endTime,
            level.isEmpty() ? null : level
        );
        
        // 创建文件选择器
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("竞赛获奖报表.csv"));
        int option = fileChooser.showSaveDialog(this);
        
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                // 写入CSV表头
                writer.write("ID,学生ID,竞赛名称,竞赛级别,获奖等级,获奖时间\n");
                
                // 写入数据
                for (ContestAward award : awards) {
                    writer.write(String.format("%d,%d,%s,%s,%s,%s\n", 
                        award.getId(),
                        award.getStudentId(),
                        award.getContestName(),
                        award.getContestLevel(),
                        award.getAwardLevel(),
                        award.getAwardTime()
                    ));
                }
                
                showMessage("竞赛获奖报表导出成功！");
            } catch (IOException e) {
                throw new Exception("导出失败：" + e.getMessage(), e);
            }
        }
    }
    
    // 导出大创项目报表
    private void exportInnovationProjectReport(String department, String major, String grade, Date startTime, Date endTime, String status, String format) throws Exception {
        // 使用多条件查询获取数据
        List<InnovationProject> projects = innovationProjectService.getInnovationProjectsByConditions(
            department.isEmpty() ? null : department,
            major.isEmpty() ? null : major,
            grade.isEmpty() ? null : grade,
            startTime,
            endTime,
            status.isEmpty() ? null : status,
            status.isEmpty() ? null : status
        );
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("大创项目报表.csv"));
        int option = fileChooser.showSaveDialog(this);
        
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                // 写入CSV表头
                writer.write("ID,学生ID,项目名称,项目级别,项目类型,项目状态,开始时间,结束时间\n");
                
                // 写入数据
                for (InnovationProject project : projects) {
                    writer.write(String.format("%d,%d,%s,%s,%s,%s,%s,%s\n", 
                        project.getId(),
                        project.getStudentId(),
                        project.getProjectName(),
                        project.getProjectLevel(),
                        project.getProjectType(),
                        project.getProjectStatus(),
                        project.getStartTime(),
                        project.getEndTime()
                    ));
                }
                
                showMessage("大创项目报表导出成功！");
            } catch (IOException e) {
                throw new Exception("导出失败：" + e.getMessage(), e);
            }
        }
    }
    
    // 导出论文发表报表
    private void exportPaperPublicationReport(String department, String major, String grade, Date startTime, Date endTime, String indexingStatus, String format) throws Exception {
        // 使用多条件查询获取数据
        List<PaperPublication> papers = paperPublicationService.getPaperPublicationsByConditions(
            department.isEmpty() ? null : department,
            major.isEmpty() ? null : major,
            grade.isEmpty() ? null : grade,
            startTime,
            endTime,
            indexingStatus.isEmpty() ? null : indexingStatus
        );
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("论文发表报表.csv"));
        int option = fileChooser.showSaveDialog(this);
        
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                // 写入CSV表头
                writer.write("ID,学生ID,论文标题,期刊/会议名称,发表时间,作者排序,索引情况\n");
                
                // 写入数据
                for (PaperPublication paper : papers) {
                    writer.write(String.format("%d,%d,%s,%s,%s,%s,%s\n", 
                        paper.getId(),
                        paper.getStudentId(),
                        paper.getPaperTitle(),
                        paper.getJournalConferenceName(),
                        paper.getPublicationTime(),
                        paper.getAuthorRank(),
                        paper.getIndexingStatus()
                    ));
                }
                
                showMessage("论文发表报表导出成功！");
            } catch (IOException e) {
                throw new Exception("导出失败：" + e.getMessage(), e);
            }
        }
    }
    
    // 导出知识产权报表
    private void exportIntellectualPropertyReport(String department, String major, String grade, Date startTime, Date endTime, String type, String format) throws Exception {
        // 使用多条件查询获取数据
        List<IntellectualProperty> ips = intellectualPropertyService.getIntellectualPropertiesByConditions(
            department.isEmpty() ? null : department,
            major.isEmpty() ? null : major,
            grade.isEmpty() ? null : grade,
            startTime,
            endTime,
            type.isEmpty() ? null : type,
            type.isEmpty() ? null : type
        );
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("知识产权报表.csv"));
        int option = fileChooser.showSaveDialog(this);
        
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                // 写入CSV表头
                writer.write("ID,学生ID,名称,类型,申请号,授权时间\n");
                
                // 写入数据
                for (IntellectualProperty ip : ips) {
                    writer.write(String.format("%d,%d,%s,%s,%s,%s\n", 
                        ip.getId(),
                        ip.getStudentId(),
                        ip.getName(),
                        ip.getType(),
                        ip.getApplicationNumber(),
                        ip.getAuthorizationDate()
                    ));
                }
                
                showMessage("知识产权报表导出成功！");
            } catch (IOException e) {
                throw new Exception("导出失败：" + e.getMessage(), e);
            }
        }
    }
}