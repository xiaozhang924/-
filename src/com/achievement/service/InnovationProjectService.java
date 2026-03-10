/**
 * InnovationProjectService.java
 * 该接口用于定义创新项目数据的业务逻辑操作
 */
package com.achievement.service;

import com.achievement.model.InnovationProject;

import java.util.List;
import java.util.Date;

public interface InnovationProjectService {
    // 添加大创项目
    void addInnovationProject(InnovationProject innovationProject);
    
    // 根据ID获取大创项目
    InnovationProject getInnovationProjectById(Integer id);
    
    // 根据学生ID获取大创项目列表
    List<InnovationProject> getInnovationProjectsByStudentId(Integer studentId);
    
    // 根据院系获取大创项目列表
    List<InnovationProject> getInnovationProjectsByDepartment(String department);
    
    // 根据专业获取大创项目列表
    List<InnovationProject> getInnovationProjectsByMajor(String major);
    
    // 根据年级获取大创项目列表
    List<InnovationProject> getInnovationProjectsByGrade(String grade);
    
    // 根据时间段获取大创项目列表
    List<InnovationProject> getInnovationProjectsByTimeRange(Date startTime, Date endTime);
    
    // 根据级别获取大创项目列表
    List<InnovationProject> getInnovationProjectsByLevel(String level);
    
    // 根据结题状态获取大创项目列表
    List<InnovationProject> getInnovationProjectsByStatus(String status);
    
    // 更新大创项目
    void updateInnovationProject(InnovationProject innovationProject);
    
    // 删除大创项目
    void deleteInnovationProject(Integer id);
    
    // 检查重复记录
    boolean checkDuplicateInnovationProject(InnovationProject innovationProject);
    
    // 获取所有大创项目列表
    List<InnovationProject> getAllInnovationProjects();
    
    // 根据多条件组合查询大创项目列表
    List<InnovationProject> getInnovationProjectsByConditions(String department, String major, String grade, Date startTime, Date endTime, String level, String status);
}