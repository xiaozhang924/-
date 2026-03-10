/**
 * IntellectualPropertyService.java
 * 该接口用于定义知识产权数据的业务逻辑操作
 */
package com.achievement.service;

import com.achievement.model.IntellectualProperty;

import java.util.List;
import java.util.Date;

public interface IntellectualPropertyService {
    // 添加专利/软著
    void addIntellectualProperty(IntellectualProperty intellectualProperty);
    
    // 根据ID获取专利/软著
    IntellectualProperty getIntellectualPropertyById(Integer id);
    
    // 根据学生ID获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByStudentId(Integer studentId);
    
    // 根据院系获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByDepartment(String department);
    
    // 根据专业获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByMajor(String major);
    
    // 根据年级获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByGrade(String grade);
    
    // 根据时间段获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByTimeRange(Date startTime, Date endTime);
    
    // 根据类型获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByType(String type);
    
    // 根据状态获取专利/软著列表
    List<IntellectualProperty> getIntellectualPropertiesByStatus(String status);
    
    // 更新专利/软著
    void updateIntellectualProperty(IntellectualProperty intellectualProperty);
    
    // 删除专利/软著
    void deleteIntellectualProperty(Integer id);
    
    // 检查重复记录
    boolean checkDuplicateIntellectualProperty(IntellectualProperty intellectualProperty);
    
    // 获取所有知识产权列表
    List<IntellectualProperty> getAllIntellectualProperties();
    
    // 根据多条件组合查询知识产权列表
    List<IntellectualProperty> getIntellectualPropertiesByConditions(String department, String major, String grade, Date startTime, Date endTime, String type, String status);
}