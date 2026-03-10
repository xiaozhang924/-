/**
 * InnovationProjectServiceImpl.java
 * 该类用于实现创新项目数据的业务逻辑操作
 */
package com.achievement.service.impl;

import com.achievement.dao.InnovationProjectDAO;
import com.achievement.model.InnovationProject;
import com.achievement.service.InnovationProjectService;

import java.util.List;
import java.util.Date;

public class InnovationProjectServiceImpl implements InnovationProjectService {
    private InnovationProjectDAO innovationProjectDAO;
    
    /**
     * 默认构造方法
     * <p>初始化InnovationProjectServiceImpl对象，使用默认的InnovationProjectDAOImpl实例</p>
     */
    public InnovationProjectServiceImpl() {
        this.innovationProjectDAO = new com.achievement.dao.impl.InnovationProjectDAOImpl();
    }
    
    /**
     * 构造方法
     * <p>初始化InnovationProjectServiceImpl对象，使用指定的InnovationProjectDAO实例（用于测试或依赖注入）</p>
     * 
     * @param innovationProjectDAO InnovationProjectDAO接口的实现类实例
     */
    public InnovationProjectServiceImpl(InnovationProjectDAO innovationProjectDAO) {
        this.innovationProjectDAO = innovationProjectDAO;
    }
    
    /**
     * 添加创新项目
     * <p>添加新的创新项目记录，并检查是否存在重复记录</p>
     * 
     * @param innovationProject 创新项目对象，包含项目的所有信息
     * @throws RuntimeException 当该创新项目记录已存在时抛出
     */
    @Override
    public void addInnovationProject(InnovationProject innovationProject) {
        if (checkDuplicateInnovationProject(innovationProject)) {
            throw new RuntimeException("该大创项目记录已存在");
        }
        innovationProjectDAO.addInnovationProject(innovationProject);
    }
    
    /**
     * 根据ID获取创新项目
     * <p>通过项目ID查询创新项目的详细信息</p>
     * 
     * @param id 创新项目的唯一标识符
     * @return 创新项目对象，如果未找到则返回null
     */
    @Override
    public InnovationProject getInnovationProjectById(Integer id) {
        return innovationProjectDAO.getInnovationProjectById(id);
    }
    
    /**
     * 根据学生ID获取创新项目列表
     * <p>查询指定学生参与的所有创新项目</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByStudentId(Integer studentId) {
        return innovationProjectDAO.getInnovationProjectsByStudentId(studentId);
    }
    
    /**
     * 根据院系获取创新项目列表
     * <p>查询指定院系的所有创新项目</p>
     * 
     * @param department 院系名称
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByDepartment(String department) {
        return innovationProjectDAO.getInnovationProjectsByDepartment(department);
    }
    
    /**
     * 根据专业获取创新项目列表
     * <p>查询指定专业的所有创新项目</p>
     * 
     * @param major 专业名称
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByMajor(String major) {
        return innovationProjectDAO.getInnovationProjectsByMajor(major);
    }
    
    /**
     * 根据年级获取创新项目列表
     * <p>查询指定年级的所有创新项目</p>
     * 
     * @param grade 年级信息
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByGrade(String grade) {
        return innovationProjectDAO.getInnovationProjectsByGrade(grade);
    }
    
    /**
     * 根据时间范围获取创新项目列表
     * <p>查询指定时间范围内的所有创新项目</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByTimeRange(Date startTime, Date endTime) {
        return innovationProjectDAO.getInnovationProjectsByTimeRange(startTime, endTime);
    }
    
    /**
     * 根据项目级别获取创新项目列表
     * <p>查询指定级别的所有创新项目</p>
     * 
     * @param level 项目级别（如国家级、省级、校级等）
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByLevel(String level) {
        return innovationProjectDAO.getInnovationProjectsByLevel(level);
    }
    
    /**
     * 根据项目状态获取创新项目列表
     * <p>查询指定状态的所有创新项目</p>
     * 
     * @param status 项目状态（如在研、已结题、已验收等）
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByStatus(String status) {
        return innovationProjectDAO.getInnovationProjectsByStatus(status);
    }
    
    /**
     * 更新创新项目信息
     * <p>修改创新项目的详细信息</p>
     * 
     * @param innovationProject 包含更新信息的创新项目对象
     */
    @Override
    public void updateInnovationProject(InnovationProject innovationProject) {
        innovationProjectDAO.updateInnovationProject(innovationProject);
    }
    
    /**
     * 删除创新项目
     * <p>根据项目ID删除创新项目记录</p>
     * 
     * @param id 创新项目的唯一标识符
     */
    @Override
    public void deleteInnovationProject(Integer id) {
        innovationProjectDAO.deleteInnovationProject(id);
    }
    
    /**
     * 检查创新项目记录是否重复
     * <p>根据项目名称、学生ID、立项年份和项目级别检查是否存在重复记录</p>
     * 
     * @param innovationProject 创新项目对象，包含要检查的项目信息
     * @return 如果存在重复记录则返回true，否则返回false
     */
    @Override
    public boolean checkDuplicateInnovationProject(InnovationProject innovationProject) {
        return innovationProjectDAO.checkDuplicateInnovationProject(innovationProject);
    }
    
    /**
     * 获取所有创新项目记录
     * <p>查询数据库中所有的创新项目记录</p>
     * 
     * @return 创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getAllInnovationProjects() {
        return innovationProjectDAO.getAllInnovationProjects();
    }
    
    /**
     * 根据多条件查询创新项目列表
     * <p>支持按院系、专业、年级、时间范围、项目级别和状态组合查询创新项目记录</p>
     * 
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param level 项目级别
     * @param status 项目状态
     * @return 符合条件的创新项目列表，如果没有记录则返回空列表
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByConditions(String department, String major, String grade, Date startTime, Date endTime, String level, String status) {
        return innovationProjectDAO.getInnovationProjectsByConditions(department, major, grade, startTime, endTime, level, status);
    }
}