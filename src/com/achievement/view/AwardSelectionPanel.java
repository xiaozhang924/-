/**
 * AwardSelectionPanel.java
 * 该类用于创建奖励选择面板
 */
package com.achievement.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.achievement.model.*;
import com.achievement.service.*;
import com.achievement.service.impl.*;
import com.achievement.dao.*;
import com.achievement.dao.impl.*;

public class AwardSelectionPanel extends JPanel {
    private ContestAwardService contestAwardService;
    private PaperPublicationService paperPublicationService;
    private InnovationProjectService innovationProjectService;
    private IntellectualPropertyService intellectualPropertyService;
    private UserService userService;
    private JTable awardTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JComboBox<String> awardTypeComboBox;
    private JTextField yearTextField;

    public AwardSelectionPanel() {
        // 初始化服务层
        ContestAwardDAO contestAwardDAO = new ContestAwardDAOImpl();
        contestAwardService = new ContestAwardServiceImpl(contestAwardDAO);
        
        PaperPublicationDAO paperPublicationDAO = new PaperPublicationDAOImpl();
        paperPublicationService = new PaperPublicationServiceImpl(paperPublicationDAO);
        
        InnovationProjectDAO innovationProjectDAO = new InnovationProjectDAOImpl();
        innovationProjectService = new InnovationProjectServiceImpl(innovationProjectDAO);
        
        IntellectualPropertyDAO intellectualPropertyDAO = new IntellectualPropertyDAOImpl();
        intellectualPropertyService = new IntellectualPropertyServiceImpl(intellectualPropertyDAO);
        
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);

        // 设置布局
        setLayout(new BorderLayout());

        // 创建顶部控制面板
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("成果类型:"));
        awardTypeComboBox = new JComboBox<>(new String[]{"所有成果", "竞赛获奖", "论文发表", "创新项目", "知识产权"});
        controlPanel.add(awardTypeComboBox);

        controlPanel.add(new JLabel("年份:"));
        yearTextField = new JTextField(10);
        controlPanel.add(yearTextField);

        searchButton = new JButton("查询");
        searchButton.addActionListener(e -> searchAchievements());
        controlPanel.add(searchButton);

        // 创建表格
        String[] columnNames = {"ID", "学生ID", "学号", "学生姓名", "成果类型", "成果名称", "级别/等级", "时间", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        awardTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(awardTable);

        // 添加组件
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // 加载数据
        loadAllAchievements();
    }

    private void loadAllAchievements() {
        // 清空表格
        tableModel.setRowCount(0);

        // 创建一个列表来存储所有行数据，用于排序
        java.util.List<Object[]> allRows = new java.util.ArrayList<>();

        // 查询所有竞赛获奖
        List<ContestAward> contestAwards = contestAwardService.getAllContestAwards();
        for (ContestAward award : contestAwards) {
            String studentName = getStudentName(award.getStudentId());
            String studentNumber = getStudentNumber(award.getStudentId());
            Object[] rowData = {
                    award.getId(),
                    award.getStudentId(),
                    studentNumber,
                    studentName,
                    "竞赛获奖",
                    award.getContestName(),
                    award.getContestLevel() + " - " + award.getAwardLevel(),
                    award.getAwardTime(),
                    award.getRemarks()
            };
            allRows.add(rowData);
        }
        
        // 查询所有论文发表
        List<PaperPublication> papers = paperPublicationService.getAllPaperPublications();
        for (PaperPublication paper : papers) {
            String studentName = getStudentName(paper.getStudentId());
            String studentNumber = getStudentNumber(paper.getStudentId());
            Object[] rowData = {
                    paper.getId(),
                    paper.getStudentId(),
                    studentNumber,
                    studentName,
                    "论文发表",
                    paper.getPaperTitle(),
                    paper.getJournalConferenceName(),
                    paper.getPublicationTime(),
                    paper.getRemarks()
            };
            allRows.add(rowData);
        }
        
        // 查询所有创新项目
        List<InnovationProject> projects = innovationProjectService.getAllInnovationProjects();
        for (InnovationProject project : projects) {
            String studentName = getStudentName(project.getStudentId());
            String studentNumber = getStudentNumber(project.getStudentId());
            Object[] rowData = {
                    project.getId(),
                    project.getStudentId(),
                    studentNumber,
                    studentName,
                    "创新项目",
                    project.getProjectName(),
                    project.getProjectLevel(),
                    project.getStartTime(),
                    project.getRemarks()
            };
            allRows.add(rowData);
        }
        
        // 查询所有知识产权
        List<IntellectualProperty> ips = intellectualPropertyService.getAllIntellectualProperties();
        for (IntellectualProperty ip : ips) {
            String studentName = getStudentName(ip.getStudentId());
            String studentNumber = getStudentNumber(ip.getStudentId());
            Object[] rowData = {
                    ip.getId(),
                    ip.getStudentId(),
                    studentNumber,
                    studentName,
                    "知识产权",
                    ip.getName(),
                    ip.getType(),
                    ip.getApplicationDate(),
                    ip.getRemarks()
            };
            allRows.add(rowData);
        }
        
        // 按学生ID排序
        allRows.sort((row1, row2) -> {
            Integer id1 = (Integer) row1[1];
            Integer id2 = (Integer) row2[1];
            return id1.compareTo(id2);
        });
        
        // 添加到表格
        for (Object[] row : allRows) {
            tableModel.addRow(row);
        }
    }

    private void searchAchievements() {
        // 清空表格
        tableModel.setRowCount(0);

        // 创建一个列表来存储所有行数据，用于排序
        java.util.List<Object[]> allRows = new java.util.ArrayList<>();

        // 获取查询条件
        String achievementType = (String) awardTypeComboBox.getSelectedItem();
        String year = yearTextField.getText();

        // 根据条件查询成果
        if ("所有成果".equals(achievementType) || "竞赛获奖".equals(achievementType)) {
            List<ContestAward> contestAwards = contestAwardService.getAllContestAwards();
            for (ContestAward award : contestAwards) {
                if (year.isEmpty() || award.getAwardTime().toString().contains(year)) {
                    String studentName = getStudentName(award.getStudentId());
                    String studentNumber = getStudentNumber(award.getStudentId());
                    Object[] rowData = {
                            award.getId(),
                            award.getStudentId(),
                            studentNumber,
                            studentName,
                            "竞赛获奖",
                            award.getContestName(),
                            award.getContestLevel() + " - " + award.getAwardLevel(),
                            award.getAwardTime(),
                            award.getRemarks()
                    };
                    allRows.add(rowData);
                }
            }
        }
        
        if ("所有成果".equals(achievementType) || "论文发表".equals(achievementType)) {
            List<PaperPublication> papers = paperPublicationService.getAllPaperPublications();
            for (PaperPublication paper : papers) {
                if (year.isEmpty() || paper.getPublicationTime().toString().contains(year)) {
                    String studentName = getStudentName(paper.getStudentId());
                    String studentNumber = getStudentNumber(paper.getStudentId());
                    Object[] rowData = {
                            paper.getId(),
                            paper.getStudentId(),
                            studentNumber,
                            studentName,
                            "论文发表",
                            paper.getPaperTitle(),
                            paper.getJournalConferenceName(),
                            paper.getPublicationTime(),
                            paper.getRemarks()
                    };
                    allRows.add(rowData);
                }
            }
        }
        
        if ("所有成果".equals(achievementType) || "创新项目".equals(achievementType)) {
            List<InnovationProject> projects = innovationProjectService.getAllInnovationProjects();
            for (InnovationProject project : projects) {
                if (year.isEmpty() || project.getStartTime().toString().contains(year)) {
                    String studentName = getStudentName(project.getStudentId());
                    String studentNumber = getStudentNumber(project.getStudentId());
                    Object[] rowData = {
                            project.getId(),
                            project.getStudentId(),
                            studentNumber,
                            studentName,
                            "创新项目",
                            project.getProjectName(),
                            project.getProjectLevel(),
                            project.getStartTime(),
                            project.getRemarks()
                    };
                    allRows.add(rowData);
                }
            }
        }
        
        if ("所有成果".equals(achievementType) || "知识产权".equals(achievementType)) {
            List<IntellectualProperty> ips = intellectualPropertyService.getAllIntellectualProperties();
            for (IntellectualProperty ip : ips) {
                if (year.isEmpty() || ip.getApplicationDate().toString().contains(year)) {
                    String studentName = getStudentName(ip.getStudentId());
                    String studentNumber = getStudentNumber(ip.getStudentId());
                    Object[] rowData = {
                            ip.getId(),
                            ip.getStudentId(),
                            studentNumber,
                            studentName,
                            "知识产权",
                            ip.getName(),
                            ip.getType(),
                            ip.getApplicationDate(),
                            ip.getRemarks()
                    };
                    allRows.add(rowData);
                }
            }
        }
        
        // 按学生ID排序
        allRows.sort((row1, row2) -> {
            Integer id1 = (Integer) row1[1];
            Integer id2 = (Integer) row2[1];
            return id1.compareTo(id2);
        });
        
        // 添加到表格
        for (Object[] row : allRows) {
            tableModel.addRow(row);
        }
    }
    
    /**
     * 根据学生ID获取学生姓名
     */
    private String getStudentName(Integer studentId) {
        try {
            User user = userService.getUserById(studentId);
            return user != null ? user.getRealName() : "未知";
        } catch (Exception e) {
            return "未知";
        }
    }
    
    /**
     * 根据学生ID获取学号
     */
    private String getStudentNumber(Integer studentId) {
        try {
            User user = userService.getUserById(studentId);
            return user != null && user.getStudentNumber() != null ? user.getStudentNumber() : "";
        } catch (Exception e) {
            return "";
        }
    }
}