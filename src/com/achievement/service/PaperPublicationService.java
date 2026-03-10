/**
 * PaperPublicationService.java
 * 该接口用于定义论文发表数据的业务逻辑操作
 */
package com.achievement.service;

import com.achievement.model.PaperPublication;

import java.util.List;
import java.util.Date;

public interface PaperPublicationService {
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
    
    // 根据索引状态获取论文发表列表
    List<PaperPublication> getPaperPublicationsByIndexingStatus(String indexingStatus);
    
    // 更新论文发表
    void updatePaperPublication(PaperPublication paperPublication);
    
    // 删除论文发表
    void deletePaperPublication(Integer id);
    
    // 检查重复记录
    boolean checkDuplicatePaperPublication(PaperPublication paperPublication);
    
    // 获取所有论文发表列表
    List<PaperPublication> getAllPaperPublications();
    
    // 根据多条件组合查询论文发表列表
    List<PaperPublication> getPaperPublicationsByConditions(String department, String major, String grade, Date startTime, Date endTime, String indexingStatus);
}