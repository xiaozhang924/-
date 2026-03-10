/**
 * IntellectualPropertyPanel.java
 * 该类用于创建知识产权面板，实现知识产权数据的管理功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.IntellectualProperty;
import com.achievement.service.IntellectualPropertyService;
import com.achievement.service.impl.IntellectualPropertyServiceImpl;
import com.achievement.dao.IntellectualPropertyDAO;
import com.achievement.dao.impl.IntellectualPropertyDAOImpl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class IntellectualPropertyPanel extends JPanel {
    private User currentUser;
    private IntellectualPropertyService intellectualPropertyService;
    private JTable intellectualPropertyTable;
    private DefaultTableModel tableModel;
    
    public IntellectualPropertyPanel(User currentUser) {
        this.currentUser = currentUser;
        
        // 初始化服务层
        IntellectualPropertyDAO intellectualPropertyDAO = new IntellectualPropertyDAOImpl();
        intellectualPropertyService = new IntellectualPropertyServiceImpl(intellectualPropertyDAO);
        
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
        String[] columnNames = {"ID", "名称", "类型", "申请号", "授权号", "申请时间", "授权时间", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        intellectualPropertyTable = new JTable(tableModel);
        intellectualPropertyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(intellectualPropertyTable);
        
        // 添加事件监听
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开添加界面
                IntellectualPropertyAddFrame addFrame = new IntellectualPropertyAddFrame(currentUser, IntellectualPropertyPanel.this);
                addFrame.setVisible(true);
            }
        });
        
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateIntellectualProperty();
            }
        });
        
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteIntellectualProperty();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadIntellectualProperties();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadIntellectualProperties();
    }
    
    // 加载知识产权数据
    public void loadIntellectualProperties() {
        try {
            List<IntellectualProperty> intellectualProperties = intellectualPropertyService.getIntellectualPropertiesByStudentId(currentUser.getId());
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (IntellectualProperty ip : intellectualProperties) {
                Object[] rowData = {
                        ip.getId(),
                        ip.getName(),
                        ip.getType(),
                        ip.getApplicationNumber(),
                        ip.getAuthorizationNumber() != null ? ip.getAuthorizationNumber() : "",
                        ip.getApplicationDate() != null ? sdf.format(ip.getApplicationDate()) : "",
                        ip.getAuthorizationDate() != null ? sdf.format(ip.getAuthorizationDate()) : "",
                        ip.getRemarks() != null ? ip.getRemarks() : ""
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载知识产权数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 修改知识产权
    private void updateIntellectualProperty() {
        int selectedRow = intellectualPropertyTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        try {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            IntellectualProperty ip = intellectualPropertyService.getIntellectualPropertyById(id);
            if (ip != null) {
                // 打开修改界面
                IntellectualPropertyUpdateFrame updateFrame = new IntellectualPropertyUpdateFrame(currentUser, IntellectualPropertyPanel.this, ip);
                updateFrame.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "修改知识产权失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // 删除知识产权
    private void deleteIntellectualProperty() {
        int selectedRow = intellectualPropertyTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一条记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除这条记录吗？", "确认", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                intellectualPropertyService.deleteIntellectualProperty(id);
                JOptionPane.showMessageDialog(this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                loadIntellectualProperties();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "删除失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}