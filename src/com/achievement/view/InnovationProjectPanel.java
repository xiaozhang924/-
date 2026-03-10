/**
 * InnovationProjectPanel.java
 * 该类用于创建创新项目面板，实现创新项目数据的管理功能
 */
package com.achievement.view;

import com.achievement.model.User;
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

public class InnovationProjectPanel extends JPanel {
    private User currentUser;
    private InnovationProjectService innovationProjectService;
    private JTable innovationProjectTable;
    private DefaultTableModel tableModel;
    
    public InnovationProjectPanel(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        InnovationProjectDAO innovationProjectDAO = new InnovationProjectDAOImpl();
        innovationProjectService = new InnovationProjectServiceImpl(innovationProjectDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("添加");
        JButton updateButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");
        JButton refreshButton = new JButton("刷新");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        // 创建表格
        String[] columnNames = {"ID", "项目名称", "项目级别", "负责人", "成员", "开始时间", "结束时间", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        innovationProjectTable = new JTable(tableModel);
        innovationProjectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(innovationProjectTable);
        
        // 添加事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开添加界面
                InnovationProjectAddFrame addFrame = new InnovationProjectAddFrame(currentUser, InnovationProjectPanel.this);
                addFrame.setVisible(true);
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInnovationProject();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteInnovationProject();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadInnovationProjects();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadInnovationProjects();
    }
    
    // 加载创新项目数据
    public void loadInnovationProjects() {
        try {
            List<InnovationProject> innovationProjects = innovationProjectService.getInnovationProjectsByStudentId(currentUser.getId());
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (InnovationProject project : innovationProjects) {
                Object[] rowData = {
                    project.getId(),
                    project.getProjectName(),
                    project.getProjectLevel(),
                    project.getProjectLeader(),
                    project.getTeamMembers(),
                    project.getStartTime() != null ? sdf.format(project.getStartTime()) : "",
                    project.getEndTime() != null ? sdf.format(project.getEndTime()) : "",
                    project.getRemarks()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "加载创新项目数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 修改创新项目
    private void updateInnovationProject() {
        int selectedRow = innovationProjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要修改的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        try {
            InnovationProject project = innovationProjectService.getInnovationProjectById(id);
            if (project != null) {
                // 打开修改界面
                InnovationProjectUpdateFrame updateFrame = new InnovationProjectUpdateFrame(currentUser, InnovationProjectPanel.this, project);
                updateFrame.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "获取创新项目数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 删除创新项目
    private void deleteInnovationProject() {
        int selectedRow = innovationProjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这条记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                innovationProjectService.deleteInnovationProject(id);
                JOptionPane.showMessageDialog(this, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadInnovationProjects();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}