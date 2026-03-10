package com.achievement.view;

import com.achievement.model.InnovationProject;
import com.achievement.service.InnovationProjectService;
import com.achievement.service.impl.InnovationProjectServiceImpl;
import com.achievement.dao.InnovationProjectDAO;
import com.achievement.dao.impl.InnovationProjectDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class InnovationProjectManagementPanel extends JPanel {
    private InnovationProjectService innovationProjectService;
    private JTable projectTable;
    private DefaultTableModel tableModel;
    
    public InnovationProjectManagementPanel() {
        // 初始化服务层
        InnovationProjectDAO projectDAO = new InnovationProjectDAOImpl();
        innovationProjectService = new InnovationProjectServiceImpl(projectDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        
        // 创建表格
        String[] columnNames = {"ID", "学生ID", "项目名称", "项目类型", "项目级别", "项目状态", "负责人", "参与成员", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        projectTable = new JTable(tableModel);
        projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(projectTable);
        
        // 添加事件监听
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProjects();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadProjects();
    }
    
    // 加载创新项目数据
    public void loadProjects() {
        try {
            List<InnovationProject> projects = innovationProjectService.getAllInnovationProjects();
            tableModel.setRowCount(0);
            
            for (InnovationProject project : projects) {
                Object[] rowData = {
                    project.getId(),
                    project.getStudentId(),
                    project.getProjectName(),
                    project.getProjectType(),
                    project.getProjectLevel(),
                    project.getProjectStatus(),
                    project.getProjectLeader(),
                    project.getTeamMembers(),
                    project.getRemarks()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "加载创新项目数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}