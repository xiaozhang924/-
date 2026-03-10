/**
 * PaperPublicationUpdateFrame.java
 * 该类用于创建论文发表更新窗口，实现更新论文发表数据功能
 */
package com.achievement.view;

import com.achievement.model.User;
import com.achievement.model.PaperPublication;
import com.achievement.service.PaperPublicationService;
import com.achievement.service.impl.PaperPublicationServiceImpl;
import com.achievement.dao.PaperPublicationDAO;
import com.achievement.dao.impl.PaperPublicationDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaperPublicationUpdateFrame extends JFrame {
    private User currentUser;
    private PaperPublicationService paperPublicationService;
    private PaperPublicationPanel parentPanel;
    private PaperPublication paper;
    
    private JTextField paperTitleField;
    private JTextField journalConferenceNameField;
    private JComboBox<String> publicationStatusComboBox;
    private JTextField publicationTimeField;
    private JTextField authorRankField;
    private JComboBox<String> indexingStatusComboBox;
    private JTextField authorsField;
    private JTextArea remarksTextArea;
    
    public PaperPublicationUpdateFrame(User currentUser, PaperPublicationPanel parentPanel, PaperPublication paper) {
        this.currentUser = currentUser;
        this.parentPanel = parentPanel;
        this.paper = paper;
        
        // 初始化服务层
        PaperPublicationDAO paperPublicationDAO = new PaperPublicationDAOImpl();
        paperPublicationService = new PaperPublicationServiceImpl(paperPublicationDAO);
        
        // 设置窗口属性
        setTitle("修改论文发表");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentPanel);
        setLayout(new BorderLayout());
        
        // 创建主面板
        JPanel mainPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // 添加组件
        mainPanel.add(new JLabel("论文题目:"));
        paperTitleField = new JTextField();
        mainPanel.add(paperTitleField);
        
        mainPanel.add(new JLabel("期刊/会议名称:"));
        journalConferenceNameField = new JTextField();
        mainPanel.add(journalConferenceNameField);
        
        mainPanel.add(new JLabel("发表状态:"));
        String[] publicationStatuses = {"已发表", "已录用", "审稿中", "拒稿"};
        publicationStatusComboBox = new JComboBox<>(publicationStatuses);
        mainPanel.add(publicationStatusComboBox);
        
        mainPanel.add(new JLabel("发表时间 (yyyy-MM-dd):"));
        publicationTimeField = new JTextField();
        mainPanel.add(publicationTimeField);
        
        mainPanel.add(new JLabel("作者排名:"));
        authorRankField = new JTextField();
        mainPanel.add(authorRankField);
        
        mainPanel.add(new JLabel("收录情况:"));
        String[] indexingStatuses = {"SCI", "EI", "CSSCI", "中文核心", "普通期刊", "未收录"};
        indexingStatusComboBox = new JComboBox<>(indexingStatuses);
        mainPanel.add(indexingStatusComboBox);
        
        mainPanel.add(new JLabel("作者:"));
        authorsField = new JTextField();
        mainPanel.add(authorsField);
        
        mainPanel.add(new JLabel("备注:"));
        remarksTextArea = new JTextArea(3, 20);
        remarksTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(remarksTextArea);
        mainPanel.add(scrollPane);
        
        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // 添加事件监听
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // 添加到主窗口
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 填充现有数据
        fillData();
        
        // 设置可见
        setVisible(true);
    }
    
    // 填充现有数据
    private void fillData() {
        paperTitleField.setText(paper.getPaperTitle());
        journalConferenceNameField.setText(paper.getJournalConferenceName());
        publicationStatusComboBox.setSelectedItem(paper.getPublicationStatus());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        publicationTimeField.setText(paper.getPublicationTime() != null ? sdf.format(paper.getPublicationTime()) : "");
        
        authorRankField.setText(paper.getAuthorRank() != null ? paper.getAuthorRank().toString() : "");
        indexingStatusComboBox.setSelectedItem(paper.getIndexingStatus());
        authorsField.setText(paper.getAuthors());
        remarksTextArea.setText(paper.getRemarks());
    }
    
    // 处理保存逻辑
    private void handleSave() {
        String paperTitle = paperTitleField.getText().trim();
        String journalConferenceName = journalConferenceNameField.getText().trim();
        String publicationStatus = (String) publicationStatusComboBox.getSelectedItem();
        String publicationTimeStr = publicationTimeField.getText().trim();
        String authorRankStr = authorRankField.getText().trim();
        String indexingStatus = (String) indexingStatusComboBox.getSelectedItem();
        String authors = authorsField.getText().trim();
        String remarks = remarksTextArea.getText().trim();
        
        // 验证输入
        if (paperTitle.isEmpty() || journalConferenceName.isEmpty() || publicationStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "论文题目、期刊/会议名称和发表状态不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 解析日期和数字
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date publicationTime = null;
            if (!publicationTimeStr.isEmpty()) {
                publicationTime = sdf.parse(publicationTimeStr);
            }
            
            Integer authorRank = null;
            if (!authorRankStr.isEmpty()) {
                authorRank = Integer.parseInt(authorRankStr);
            }
            
            // 更新论文发表信息
            paper.setPaperTitle(paperTitle);
            paper.setJournalConferenceName(journalConferenceName);
            paper.setPublicationStatus(publicationStatus);
            paper.setPublicationTime(publicationTime);
            paper.setAuthorRank(authorRank);
            paper.setIndexingStatus(indexingStatus);
            paper.setAuthors(authors);
            paper.setRemarks(remarks);
            
            // 保存到数据库
            paperPublicationService.updatePaperPublication(paper);
            
            JOptionPane.showMessageDialog(this, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            
            // 刷新父面板数据
            parentPanel.loadPaperPublications();
            
            // 关闭窗口
            dispose();
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "日期格式错误，请使用yyyy-MM-dd格式", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "作者排名格式错误", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "修改失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}