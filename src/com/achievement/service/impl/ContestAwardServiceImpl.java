/**
 * ContestAwardServiceImpl.java
 * 该类用于实现竞赛获奖数据的业务逻辑操作
 */
package com.achievement.service.impl;

import com.achievement.dao.ContestAwardDAO;
import com.achievement.model.ContestAward;
import com.achievement.service.ContestAwardService;

import java.util.List;
import java.util.Date;

public class ContestAwardServiceImpl implements ContestAwardService {
    private ContestAwardDAO contestAwardDAO;
    
    /**
     * 默认构造方法
     * <p>初始化ContestAwardServiceImpl对象，使用默认的ContestAwardDAOImpl实例</p>
     */
    public ContestAwardServiceImpl() {
        this.contestAwardDAO = new com.achievement.dao.impl.ContestAwardDAOImpl();
    }
    
    /**
     * 构造方法
     * <p>初始化ContestAwardServiceImpl对象，使用指定的ContestAwardDAO实例（用于测试或依赖注入）</p>
     * 
     * @param contestAwardDAO ContestAwardDAO接口的实现类实例
     */
    public ContestAwardServiceImpl(ContestAwardDAO contestAwardDAO) {
        this.contestAwardDAO = contestAwardDAO;
    }
    
    /**
     * 添加竞赛获奖记录
     * <p>添加新的竞赛获奖记录，并检查是否存在重复记录</p>
     * 
     * @param contestAward 竞赛获奖对象，包含获奖的所有信息
     * @throws RuntimeException 当该竞赛获奖记录已存在时抛出
     */
    @Override
    public void addContestAward(ContestAward contestAward) {
        if (checkDuplicateContestAward(contestAward)) {
            throw new RuntimeException("该竞赛获奖记录已存在");
        }
        contestAwardDAO.addContestAward(contestAward);
    }
    
    /**
     * 根据ID获取竞赛获奖记录
     * <p>通过记录ID查询竞赛获奖的详细信息</p>
     * 
     * @param id 竞赛获奖记录的唯一标识符
     * @return 竞赛获奖对象，如果未找到则返回null
     */
    @Override
    public ContestAward getContestAwardById(Integer id) {
        return contestAwardDAO.getContestAwardById(id);
    }
    
    /**
     * 根据学生ID获取竞赛获奖记录列表
     * <p>查询指定学生获得的所有竞赛获奖记录</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByStudentId(Integer studentId) {
        return contestAwardDAO.getContestAwardsByStudentId(studentId);
    }
    
    /**
     * 根据院系获取竞赛获奖记录列表
     * <p>查询指定院系的所有竞赛获奖记录</p>
     * 
     * @param department 院系名称
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByDepartment(String department) {
        return contestAwardDAO.getContestAwardsByDepartment(department);
    }
    
    /**
     * 根据专业获取竞赛获奖记录列表
     * <p>查询指定专业的所有竞赛获奖记录</p>
     * 
     * @param major 专业名称
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByMajor(String major) {
        return contestAwardDAO.getContestAwardsByMajor(major);
    }
    
    /**
     * 根据年级获取竞赛获奖记录列表
     * <p>查询指定年级的所有竞赛获奖记录</p>
     * 
     * @param grade 年级信息
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByGrade(String grade) {
        return contestAwardDAO.getContestAwardsByGrade(grade);
    }
    
    /**
     * 根据时间范围获取竞赛获奖记录列表
     * <p>查询指定时间范围内的所有竞赛获奖记录</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByTimeRange(Date startTime, Date endTime) {
        return contestAwardDAO.getContestAwardsByTimeRange(startTime, endTime);
    }
    
    /**
     * 根据竞赛级别获取竞赛获奖记录列表
     * <p>查询指定级别的所有竞赛获奖记录</p>
     * 
     * @param level 竞赛级别（如国家级、省级、校级等）
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByLevel(String level) {
        return contestAwardDAO.getContestAwardsByLevel(level);
    }
    
    /**
     * 更新竞赛获奖记录信息
     * <p>修改竞赛获奖记录的详细信息</p>
     * 
     * @param contestAward 包含更新信息的竞赛获奖对象
     */
    @Override
    public void updateContestAward(ContestAward contestAward) {
        contestAwardDAO.updateContestAward(contestAward);
    }
    
    /**
     * 删除竞赛获奖记录
     * <p>根据记录ID删除竞赛获奖记录</p>
     * 
     * @param id 竞赛获奖记录的唯一标识符
     */
    @Override
    public void deleteContestAward(Integer id) {
        contestAwardDAO.deleteContestAward(id);
    }
    
    /**
     * 检查竞赛获奖记录是否重复
     * <p>根据竞赛名称、学生ID、获奖时间和获奖级别检查是否存在重复记录</p>
     * 
     * @param contestAward 竞赛获奖对象，包含要检查的获奖信息
     * @return 如果存在重复记录则返回true，否则返回false
     */
    @Override
    public boolean checkDuplicateContestAward(ContestAward contestAward) {
        return contestAwardDAO.checkDuplicateContestAward(contestAward);
    }
    
    /**
     * 获取所有竞赛获奖记录
     * <p>查询数据库中所有的竞赛获奖记录</p>
     * 
     * @return 竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getAllContestAwards() {
        return contestAwardDAO.getAllContestAwards();
    }
    
    /**
     * 根据多条件查询竞赛获奖记录列表
     * <p>支持按院系、专业、年级、时间范围、竞赛级别组合查询竞赛获奖记录</p>
     * 
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param level 竞赛级别
     * @return 符合条件的竞赛获奖记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<ContestAward> getContestAwardsByConditions(String department, String major, String grade, Date startTime, Date endTime, String level) {
        return contestAwardDAO.getContestAwardsByConditions(department, major, grade, startTime, endTime, level);
    }
}