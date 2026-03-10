/**
 * IntellectualPropertyServiceImpl.java
 * 该类用于实现知识产权数据的业务逻辑操作
 */
package com.achievement.service.impl;

import com.achievement.dao.IntellectualPropertyDAO;
import com.achievement.model.IntellectualProperty;
import com.achievement.service.IntellectualPropertyService;

import java.util.List;
import java.util.Date;

public class IntellectualPropertyServiceImpl implements IntellectualPropertyService {
    private IntellectualPropertyDAO intellectualPropertyDAO;
    
    /**
     * 默认构造方法
     * <p>初始化IntellectualPropertyServiceImpl对象，使用默认的IntellectualPropertyDAOImpl实例</p>
     */
    public IntellectualPropertyServiceImpl() {
        this.intellectualPropertyDAO = new com.achievement.dao.impl.IntellectualPropertyDAOImpl();
    }
    
    /**
     * 构造方法
     * <p>初始化IntellectualPropertyServiceImpl对象，使用指定的IntellectualPropertyDAO实例（用于测试或依赖注入）</p>
     * 
     * @param intellectualPropertyDAO IntellectualPropertyDAO接口的实现类实例
     */
    public IntellectualPropertyServiceImpl(IntellectualPropertyDAO intellectualPropertyDAO) {
        this.intellectualPropertyDAO = intellectualPropertyDAO;
    }
    
    /**
     * 添加知识产权记录
     * <p>添加新的知识产权记录，并检查是否存在重复记录</p>
     * 
     * @param intellectualProperty 知识产权对象，包含知识产权的所有信息
     * @throws RuntimeException 当该知识产权记录已存在时抛出
     */
    @Override
    public void addIntellectualProperty(IntellectualProperty intellectualProperty) {
        if (checkDuplicateIntellectualProperty(intellectualProperty)) {
            throw new RuntimeException("该专利/软著记录已存在");
        }
        intellectualPropertyDAO.addIntellectualProperty(intellectualProperty);
    }
    
    /**
     * 根据ID获取知识产权记录
     * <p>通过记录ID查询知识产权的详细信息</p>
     * 
     * @param id 知识产权记录的唯一标识符
     * @return 知识产权对象，如果未找到则返回null
     */
    @Override
    public IntellectualProperty getIntellectualPropertyById(Integer id) {
        return intellectualPropertyDAO.getIntellectualPropertyById(id);
    }
    
    /**
     * 根据学生ID获取知识产权记录列表
     * <p>查询指定学生拥有的所有知识产权记录</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByStudentId(Integer studentId) {
        return intellectualPropertyDAO.getIntellectualPropertiesByStudentId(studentId);
    }
    
    /**
     * 根据院系获取知识产权记录列表
     * <p>查询指定院系的所有知识产权记录</p>
     * 
     * @param department 院系名称
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByDepartment(String department) {
        return intellectualPropertyDAO.getIntellectualPropertiesByDepartment(department);
    }
    
    /**
     * 根据专业获取知识产权记录列表
     * <p>查询指定专业的所有知识产权记录</p>
     * 
     * @param major 专业名称
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByMajor(String major) {
        return intellectualPropertyDAO.getIntellectualPropertiesByMajor(major);
    }
    
    /**
     * 根据年级获取知识产权记录列表
     * <p>查询指定年级的所有知识产权记录</p>
     * 
     * @param grade 年级信息
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByGrade(String grade) {
        return intellectualPropertyDAO.getIntellectualPropertiesByGrade(grade);
    }
    
    /**
     * 根据时间范围获取知识产权记录列表
     * <p>查询指定时间范围内的所有知识产权记录</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByTimeRange(Date startTime, Date endTime) {
        return intellectualPropertyDAO.getIntellectualPropertiesByTimeRange(startTime, endTime);
    }
    
    /**
     * 根据知识产权类型获取记录列表
     * <p>查询指定类型的所有知识产权记录（如专利、软件著作权等）</p>
     * 
     * @param type 知识产权类型
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByType(String type) {
        return intellectualPropertyDAO.getIntellectualPropertiesByType(type);
    }
    
    /**
     * 根据知识产权状态获取记录列表
     * <p>查询指定状态的所有知识产权记录（如申请中、已授权等）</p>
     * 
     * @param status 知识产权状态
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByStatus(String status) {
        return intellectualPropertyDAO.getIntellectualPropertiesByStatus(status);
    }
    
    /**
     * 更新知识产权记录信息
     * <p>修改知识产权记录的详细信息</p>
     * 
     * @param intellectualProperty 包含更新信息的知识产权对象
     */
    @Override
    public void updateIntellectualProperty(IntellectualProperty intellectualProperty) {
        intellectualPropertyDAO.updateIntellectualProperty(intellectualProperty);
    }
    
    /**
     * 删除知识产权记录
     * <p>根据记录ID删除知识产权记录</p>
     * 
     * @param id 知识产权记录的唯一标识符
     */
    @Override
    public void deleteIntellectualProperty(Integer id) {
        intellectualPropertyDAO.deleteIntellectualProperty(id);
    }
    
    /**
     * 检查知识产权记录是否重复
     * <p>根据知识产权名称、学生ID和申请日期检查是否存在重复记录</p>
     * 
     * @param intellectualProperty 知识产权对象，包含要检查的知识产权信息
     * @return 如果存在重复记录则返回true，否则返回false
     */
    @Override
    public boolean checkDuplicateIntellectualProperty(IntellectualProperty intellectualProperty) {
        return intellectualPropertyDAO.checkDuplicateIntellectualProperty(intellectualProperty);
    }
    
    /**
     * 获取所有知识产权记录
     * <p>查询数据库中所有的知识产权记录</p>
     * 
     * @return 知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getAllIntellectualProperties() {
        return intellectualPropertyDAO.getAllIntellectualProperties();
    }
    
    /**
     * 根据多条件查询知识产权记录列表
     * <p>支持按院系、专业、年级、时间范围、知识产权类型和状态组合查询知识产权记录</p>
     * 
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 知识产权类型
     * @param status 知识产权状态
     * @return 符合条件的知识产权记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByConditions(String department, String major, String grade, Date startTime, Date endTime, String type, String status) {
        return intellectualPropertyDAO.getIntellectualPropertiesByConditions(department, major, grade, startTime, endTime, type, status);
    }
}