package com.achievement.view;

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

public class IntellectualPropertyManagementPanel extends JPanel {
    private IntellectualPropertyService intellectualPropertyService;
    private JTable ipTable;
    private DefaultTableModel tableModel;
    
    public IntellectualPropertyManagementPanel() {
        // 初始化服务层
        IntellectualPropertyDAO ipDAO = new IntellectualPropertyDAOImpl();
        intellectualPropertyService = new IntellectualPropertyServiceImpl(ipDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        
        // 创建表格
        String[] columnNames = {"ID", "学生ID", "产权名称", "产权类型", "申请号", "授权号", "申请日期", "授权日期", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        ipTable = new JTable(tableModel);
        ipTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ipTable);
        
        // 添加事件监听
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadIPs();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadIPs();
    }
    
    // 加载知识产权数据
    public void loadIPs() {
        try {
            List<IntellectualProperty> ips = intellectualPropertyService.getAllIntellectualProperties();
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (IntellectualProperty ip : ips) {
                Object[] rowData = {
                    ip.getId(),
                    ip.getStudentId(),
                    ip.getPropertyName(),
                    ip.getPropertyType(),
                    ip.getApplicationNumber(),
                    ip.getAuthorizationNumber(),
                    ip.getApplicationDate() != null ? sdf.format(ip.getApplicationDate()) : "",
                    ip.getAuthorizationDate() != null ? sdf.format(ip.getAuthorizationDate()) : "",
                    ip.getRemarks()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "加载知识产权数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}