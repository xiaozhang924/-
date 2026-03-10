/**
 * PaperPublicationDAO.java
 * 该接口用于定义论文发表数据的数据库访问操作
 */
package com.achievement.dao;

import com.achievement.model.PaperPublication;

import java.util.List;
import java.util.Date;

public interface PaperPublicationDAO {
    // 添加论文发表
    void addPaperPublication(PaperPublication paperPublication);
    
    // 根据ID获取论文发表
    PaperPublication getPaperPublicationById(Integer id);
    
    // 根据学生ID获取论文发表列表
    List<PaperPublication> getPaperPublicationsByStudentId(Integer studentId);
    
    // 根据院系获取论文发表列表
    List<PaperPublication> getPaperPublicationsByDepartment(String department);
    
    // 根据专业获取论文发表列表
    List<PaperPublication> getPaperPublicationsByMajor(String major);
    
    // 根据年级获取论文发表列表
    List<PaperPublication> getPaperPublicationsByGrade(String grade);
    
    // 根据时间段获取论文发表列表
    List<PaperPublication> getPaperPublicationsByTimeRange(Date startTime, Date endTime);
    
    // 根据收录情况获取论文发表列表
    List<PaperPublication> getPaperPublicationsByIndexingStatus(String indexingStatus);
    
    // 根据多条件组合查询论文发表列表
    List<PaperPublication> getPaperPublicationsByConditions(String department, String major, String grade, Date startTime, Date endTime, String indexingStatus);
    
    // 更新论文发表
    void updatePaperPublication(PaperPublication paperPublication);
    
    // 删除论文发表
    void deletePaperPublication(Integer id);
    
    // 检查重复记录
    boolean checkDuplicatePaperPublication(PaperPublication paperPublication);
    
    // 获取所有论文发表记录
    List<PaperPublication> getAllPaperPublications();
}