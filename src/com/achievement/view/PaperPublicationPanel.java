/**
 * PaperPublicationPanel.java
 * 该类用于创建论文发表面板，实现论文发表数据的管理功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.PaperPublication;
import com.achievement.service.PaperPublicationService;
import com.achievement.service.impl.PaperPublicationServiceImpl;
import com.achievement.dao.PaperPublicationDAO;
import com.achievement.dao.impl.PaperPublicationDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class PaperPublicationPanel extends JPanel {
    private User currentUser;
    private PaperPublicationService paperPublicationService;
    private JTable paperPublicationTable;
    private DefaultTableModel tableModel;
    
    public PaperPublicationPanel(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        PaperPublicationDAO paperPublicationDAO = new PaperPublicationDAOImpl();
        paperPublicationService = new PaperPublicationServiceImpl(paperPublicationDAO);
        
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
        String[] columnNames = {"ID", "论文题目", "发表期刊", "发表时间", "作者", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        paperPublicationTable = new JTable(tableModel);
        paperPublicationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(paperPublicationTable);
        
        // 添加事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开添加界面
                PaperPublicationAddFrame addFrame = new PaperPublicationAddFrame(currentUser, PaperPublicationPanel.this);
                addFrame.setVisible(true);
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePaperPublication();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePaperPublication();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPaperPublications();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadPaperPublications();
    }
    
    // 加载论文发表数据
    public void loadPaperPublications() {
        try {
            List<PaperPublication> paperPublications = paperPublicationService.getPaperPublicationsByStudentId(currentUser.getId());
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (PaperPublication paper : paperPublications) {
                Object[] rowData = {
                    paper.getId(),
                    paper.getPaperTitle(),
                    paper.getJournalConferenceName(),
                    paper.getPublicationTime() != null ? sdf.format(paper.getPublicationTime()) : "",
                    paper.getAuthors(),
                    paper.getRemarks()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "加载论文发表数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 修改论文发表
    private void updatePaperPublication() {
        int selectedRow = paperPublicationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要修改的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        try {
            PaperPublication paper = paperPublicationService.getPaperPublicationById(id);
            if (paper != null) {
                // 打开修改界面
                PaperPublicationUpdateFrame updateFrame = new PaperPublicationUpdateFrame(currentUser, PaperPublicationPanel.this, paper);
                updateFrame.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "获取论文发表数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 删除论文发表
    private void deletePaperPublication() {
        int selectedRow = paperPublicationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这条记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                paperPublicationService.deletePaperPublication(id);
                JOptionPane.showMessageDialog(this, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadPaperPublications();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}