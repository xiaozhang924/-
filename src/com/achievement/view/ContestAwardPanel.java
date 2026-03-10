/**
 * ContestAwardPanel.java
 * 该类用于创建竞赛获奖面板，实现竞赛获奖数据的管理功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.ContestAward;
import com.achievement.service.ContestAwardService;
import com.achievement.service.impl.ContestAwardServiceImpl;
import com.achievement.dao.ContestAwardDAO;
import com.achievement.dao.impl.ContestAwardDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContestAwardPanel extends JPanel {
    private User currentUser;
    private ContestAwardService contestAwardService;
    private JTable contestAwardTable;
    private DefaultTableModel tableModel;
    
    public ContestAwardPanel(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        ContestAwardDAO contestAwardDAO = new ContestAwardDAOImpl();
        contestAwardService = new ContestAwardServiceImpl(contestAwardDAO);
        
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
        String[] columnNames = {"ID", "竞赛名称", "竞赛级别", "获奖等级", "获奖时间", "获奖证书", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        contestAwardTable = new JTable(tableModel);
        contestAwardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(contestAwardTable);
        
        // 添加事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ContestAwardAddFrame(ContestAwardPanel.this, currentUser);
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContestAward();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContestAward();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadContestAwards();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadContestAwards();
    }
    
    // 加载竞赛获奖数据
    public void loadContestAwards() {
        try {
            List<ContestAward> contestAwards = contestAwardService.getContestAwardsByStudentId(currentUser.getId());
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ContestAward award : contestAwards) {
                Object[] rowData = {
                    award.getId(),
                    award.getContestName(),
                    award.getContestLevel(),
                    award.getAwardLevel(),
                    sdf.format(award.getAwardTime()),
                    award.getCertificatePath(),
                    award.getRemarks()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "加载竞赛获奖数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 修改竞赛获奖
    private void updateContestAward() {
        int selectedRow = contestAwardTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要修改的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        try {
            ContestAward award = contestAwardService.getContestAwardById(id);
            if (award != null) {
                new ContestAwardUpdateFrame(this, award);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "获取竞赛获奖数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 删除竞赛获奖
    private void deleteContestAward() {
        int selectedRow = contestAwardTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择要删除的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这条记录吗？", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                contestAwardService.deleteContestAward(id);
                JOptionPane.showMessageDialog(this, "删除成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadContestAwards();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}