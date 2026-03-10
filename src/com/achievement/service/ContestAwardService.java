/**
 * ContestAwardService.java
 * 该接口用于定义竞赛获奖数据的业务逻辑操作
 */
package com.achievement.service;

import com.achievement.model.ContestAward;

import java.util.List;
import java.util.Date;

public interface ContestAwardService {
    // 添加竞赛获奖
    void addContestAward(ContestAward contestAward);
    
    // 根据ID获取竞赛获奖
    ContestAward getContestAwardById(Integer id);
    
    // 根据学生ID获取竞赛获奖列表
    List<ContestAward> getContestAwardsByStudentId(Integer studentId);
    
    // 根据院系获取竞赛获奖列表
    List<ContestAward> getContestAwardsByDepartment(String department);
    
    // 根据专业获取竞赛获奖列表
    List<ContestAward> getContestAwardsByMajor(String major);
    
    // 根据年级获取竞赛获奖列表
    List<ContestAward> getContestAwardsByGrade(String grade);
    
    // 根据时间段获取竞赛获奖列表
    List<ContestAward> getContestAwardsByTimeRange(Date startTime, Date endTime);
    
    // 根据级别获取竞赛获奖列表
    List<ContestAward> getContestAwardsByLevel(String level);
    
    // 更新竞赛获奖
    void updateContestAward(ContestAward contestAward);
    
    // 删除竞赛获奖
    void deleteContestAward(Integer id);
    
    // 检查重复记录
    boolean checkDuplicateContestAward(ContestAward contestAward);
    
    // 获取所有竞赛获奖列表
    List<ContestAward> getAllContestAwards();
    
    // 根据多条件组合查询竞赛获奖列表
    List<ContestAward> getContestAwardsByConditions(String department, String major, String grade, Date startTime, Date endTime, String level);
}