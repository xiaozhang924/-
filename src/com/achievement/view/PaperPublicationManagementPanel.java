package com.achievement.view;

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

public class PaperPublicationManagementPanel extends JPanel {
    private PaperPublicationService paperPublicationService;
    private JTable paperTable;
    private DefaultTableModel tableModel;
    
    public PaperPublicationManagementPanel() {
        // 初始化服务层
        PaperPublicationDAO paperDAO = new PaperPublicationDAOImpl();
        paperPublicationService = new PaperPublicationServiceImpl(paperDAO);
        
        // 设置布局
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = new JButton("刷新");
        buttonPanel.add(refreshButton);
        
        // 创建表格
        String[] columnNames = {"ID", "学生ID", "论文标题", "期刊/会议名称", "发表状态", "发表时间", "作者排名", "收录情况", "作者列表", "备注"};
        tableModel = new DefaultTableModel(columnNames, 0);
        paperTable = new JTable(tableModel);
        paperTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(paperTable);
        
        // 添加事件监听
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPapers();
            }
        });
        
        // 添加到面板
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // 加载数据
        loadPapers();
    }
    
    // 加载论文发表数据
    public void loadPapers() {
        try {
            List<PaperPublication> papers = paperPublicationService.getAllPaperPublications();
            tableModel.setRowCount(0);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (PaperPublication paper : papers) {
                Object[] rowData = {
                    paper.getId(),
                    paper.getStudentId(),
                    paper.getPaperTitle(),
                    paper.getJournalConferenceName(),
                    paper.getPublicationStatus(),
                    paper.getPublicationTime() != null ? sdf.format(paper.getPublicationTime()) : "",
                    paper.getAuthorRank(),
                    paper.getIndexingStatus(),
                    paper.getAuthors(),
                    paper.getRemarks()
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "加载论文发表数据失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}