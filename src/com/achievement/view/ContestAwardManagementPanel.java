package com.achievement.view;

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

public class ContestAwardManagementPanel extends JPanel {
    private ContestAwardService contestAwardService;
    private JTable contestAwardTable;
    private DefaultTableModel tableModel;
    
    public ContestAwardManagementPanel() {
        // 初始化服务层
        ContestAwardDAO contestAwardDAO = new ContestAwardDAOImpl();
        contestAwardService = new ContestAwardServiceImpl(contestAwardDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        
        // 创建表格
        String[] columnNames = {"ID", "学生ID", "竞赛名称", "竞赛级别", "获奖等级", "获奖时间", "获奖证书", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        contestAwardTable = new JTable(tableModel);
        contestAwardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(contestAwardTable);
        
        // 添加事件监听
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
            List<ContestAward> contestAwards = contestAwardService.getAllContestAwards();
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (ContestAward award : contestAwards) {
                Object[] rowData = {
                    award.getId(),
                    award.getStudentId(),
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
}