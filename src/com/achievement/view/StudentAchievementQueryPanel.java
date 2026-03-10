/**
 * StudentAchievementQueryPanel.java
 * 该类用于创建学生成果查询面板，实现学生成果数据的查询功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.ContestAward;
import com.achievement.model.InnovationProject;
import com.achievement.model.PaperPublication;
import com.achievement.model.IntellectualProperty;
import com.achievement.service.UserService;
import com.achievement.service.impl.UserServiceImpl;
import com.achievement.service.ContestAwardService;
import com.achievement.service.impl.ContestAwardServiceImpl;
import com.achievement.service.InnovationProjectService;
import com.achievement.service.impl.InnovationProjectServiceImpl;
import com.achievement.service.PaperPublicationService;
import com.achievement.service.impl.PaperPublicationServiceImpl;
import com.achievement.service.IntellectualPropertyService;
import com.achievement.service.impl.IntellectualPropertyServiceImpl;
import com.achievement.dao.UserDAO;
import com.achievement.dao.impl.UserDAOImpl;
import com.achievement.dao.ContestAwardDAO;
import com.achievement.dao.impl.ContestAwardDAOImpl;
import com.achievement.dao.InnovationProjectDAO;
import com.achievement.dao.impl.InnovationProjectDAOImpl;
import com.achievement.dao.PaperPublicationDAO;
import com.achievement.dao.impl.PaperPublicationDAOImpl;
import com.achievement.dao.IntellectualPropertyDAO;
import com.achievement.dao.impl.IntellectualPropertyDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentAchievementQueryPanel extends JPanel {
    private User currentUser;
    private UserService userService;
    private ContestAwardService contestAwardService;
    private InnovationProjectService innovationProjectService;
    private PaperPublicationService paperPublicationService;
    private IntellectualPropertyService intellectualPropertyService;
    
    private JTextField nameField;
    private JTextField departmentField;
    private JTextField majorField;
    private JTextField gradeField;
    private JTable resultTable;
    private DefaultTableModel resultTableModel;
    
    public StudentAchievementQueryPanel(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        UserDAO userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);
        
        ContestAwardDAO contestAwardDAO = new ContestAwardDAOImpl();
        contestAwardService = new ContestAwardServiceImpl(contestAwardDAO);
        
        InnovationProjectDAO innovationProjectDAO = new InnovationProjectDAOImpl();
        innovationProjectService = new InnovationProjectServiceImpl(innovationProjectDAO);
        
        PaperPublicationDAO paperPublicationDAO = new PaperPublicationDAOImpl();
        paperPublicationService = new PaperPublicationServiceImpl(paperPublicationDAO);
        
        IntellectualPropertyDAO intellectualPropertyDAO = new IntellectualPropertyDAOImpl();
        intellectualPropertyService = new IntellectualPropertyServiceImpl(intellectualPropertyDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // 创建查询条件面板
        JPanel queryPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        queryPanel.add(new JLabel("学生姓名:"));
        nameField = new JTextField();
        queryPanel.add(nameField);
        
        queryPanel.add(new JLabel("部门:"));
        departmentField = new JTextField();
        queryPanel.add(departmentField);
        
        queryPanel.add(new JLabel("专业:"));
        majorField = new JTextField();
        queryPanel.add(majorField);
        
        queryPanel.add(new JLabel("年级:"));
        gradeField = new JTextField();
        queryPanel.add(gradeField);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton queryButton = new JButton("查询");
        JButton resetButton = new JButton("重置");
        
        buttonPanel.add(queryButton);
        buttonPanel.add(resetButton);
        
        // 创建顶部面板（查询条件+按钮）
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(queryPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // 创建结果表格
        String[] columnNames = {"成果类型", "学生ID", "学生姓名", "部门", "专业", "年级", "成果名称", "成果详情", "时间"};
        resultTableModel = new DefaultTableModel(columnNames, 0);
        resultTable = new JTable(resultTableModel);
        resultTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        
        // 添加事件监听
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performQuery();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetQuery();
            }
        });
        
        // 添加到主面板
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * 执行查询操作
     */
    private void performQuery() {
        String name = nameField.getText().trim();
        String department = departmentField.getText().trim();
        String major = majorField.getText().trim();
        String grade = gradeField.getText().trim();
        
        try {
            // 根据条件查询学生
            List<User> students = userService.getStudentsByConditions(name, department, major, grade);
            
            // 清空结果表格
            resultTableModel.setRowCount(0);
            
            // 准备日期格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            // 遍历学生，查询他们的各类成果
            for (User student : students) {
                Integer studentId = student.getId();
                String studentName = student.getRealName();
                String studentDepartment = student.getDepartment();
                String studentMajor = student.getMajor();
                String studentGrade = student.getGrade();
                
                // 查询竞赛获奖
                List<ContestAward> contestAwards = contestAwardService.getContestAwardsByStudentId(studentId);
                for (ContestAward award : contestAwards) {
                    Object[] row = {
                        "竞赛获奖",
                        studentId,
                        studentName,
                        studentDepartment,
                        studentMajor,
                        studentGrade,
                        award.getContestName(),
                        award.getContestLevel() + "-" + award.getAwardLevel(),
                        sdf.format(award.getAwardTime())
                    };
                    resultTableModel.addRow(row);
                }
                
                // 查询大创项目
                List<InnovationProject> innovationProjects = innovationProjectService.getInnovationProjectsByStudentId(studentId);
                for (InnovationProject project : innovationProjects) {
                    Object[] row = {
                        "大创项目",
                        studentId,
                        studentName,
                        studentDepartment,
                        studentMajor,
                        studentGrade,
                        project.getProjectName(),
                        "级别: " + project.getProjectLevel() + ", 状态: " + project.getConclusionStatus(),
                        sdf.format(project.getStartTime())
                    };
                    resultTableModel.addRow(row);
                }
                
                // 查询论文发表
                List<PaperPublication> paperPublications = paperPublicationService.getPaperPublicationsByStudentId(studentId);
                for (PaperPublication paper : paperPublications) {
                    Object[] row = {
                        "论文发表",
                        studentId,
                        studentName,
                        studentDepartment,
                        studentMajor,
                        studentGrade,
                        paper.getPaperTitle(),
                        "期刊/会议: " + paper.getJournalConferenceName() + ", 索引: " + paper.getIndexingStatus(),
                        sdf.format(paper.getPublicationTime())
                    };
                    resultTableModel.addRow(row);
                }
                
                // 查询专利/软著
                List<IntellectualProperty> intellectualProperties = intellectualPropertyService.getIntellectualPropertiesByStudentId(studentId);
                for (IntellectualProperty ip : intellectualProperties) {
                    Object[] row = {
                        "专利/软著",
                        studentId,
                        studentName,
                        studentDepartment,
                        studentMajor,
                        studentGrade,
                        ip.getName(),
                        "类型: " + ip.getType() + ", 状态: " + ip.getStatus(),
                        sdf.format(ip.getApplicationDate())
                    };
                    resultTableModel.addRow(row);
                }
            }
            
            if (resultTableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "未找到匹配的成果记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "查询失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 重置查询条件
     */
    private void resetQuery() {
        nameField.setText("");
        departmentField.setText("");
        majorField.setText("");
        gradeField.setText("");
        resultTableModel.setRowCount(0);
    }
}