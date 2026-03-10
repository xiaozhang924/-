/**
 * PaperPublicationServiceImpl.java
 * 该类用于实现论文发表数据的业务逻辑操作
 */
package com.achievement.service.impl;

import com.achievement.dao.PaperPublicationDAO;
import com.achievement.model.PaperPublication;
import com.achievement.service.PaperPublicationService;

import java.util.List;
import java.util.Date;

public class PaperPublicationServiceImpl implements PaperPublicationService {
    private PaperPublicationDAO paperPublicationDAO;
    
    /**
     * 默认构造方法
     * <p>初始化PaperPublicationServiceImpl对象，使用默认的PaperPublicationDAOImpl实例</p>
     */
    public PaperPublicationServiceImpl() {
        this.paperPublicationDAO = new com.achievement.dao.impl.PaperPublicationDAOImpl();
    }
    
    /**
     * 构造方法
     * <p>初始化PaperPublicationServiceImpl对象，使用指定的PaperPublicationDAO实例（用于测试或依赖注入）</p>
     * 
     * @param paperPublicationDAO PaperPublicationDAO接口的实现类实例
     */
    public PaperPublicationServiceImpl(PaperPublicationDAO paperPublicationDAO) {
        this.paperPublicationDAO = paperPublicationDAO;
    }
    
    /**
     * 添加论文发表记录
     * <p>添加新的论文发表记录，并检查是否存在重复记录</p>
     * 
     * @param paperPublication 论文发表对象，包含论文发表的所有信息
     * @throws RuntimeException 当该论文发表记录已存在时抛出
     */
    @Override
    public void addPaperPublication(PaperPublication paperPublication) {
        if (checkDuplicatePaperPublication(paperPublication)) {
            throw new RuntimeException("该论文发表记录已存在");
        }
        paperPublicationDAO.addPaperPublication(paperPublication);
    }
    
    /**
     * 根据ID获取论文发表记录
     * <p>通过记录ID查询论文发表的详细信息</p>
     * 
     * @param id 论文发表记录的唯一标识符
     * @return 论文发表对象，如果未找到则返回null
     */
    @Override
    public PaperPublication getPaperPublicationById(Integer id) {
        return paperPublicationDAO.getPaperPublicationById(id);
    }
    
    /**
     * 根据学生ID获取论文发表记录列表
     * <p>查询指定学生发表的所有论文记录</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByStudentId(Integer studentId) {
        return paperPublicationDAO.getPaperPublicationsByStudentId(studentId);
    }
    
    /**
     * 根据院系获取论文发表记录列表
     * <p>查询指定院系的所有论文发表记录</p>
     * 
     * @param department 院系名称
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByDepartment(String department) {
        return paperPublicationDAO.getPaperPublicationsByDepartment(department);
    }
    
    /**
     * 根据专业获取论文发表记录列表
     * <p>查询指定专业的所有论文发表记录</p>
     * 
     * @param major 专业名称
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByMajor(String major) {
        return paperPublicationDAO.getPaperPublicationsByMajor(major);
    }
    
    /**
     * 根据年级获取论文发表记录列表
     * <p>查询指定年级的所有论文发表记录</p>
     * 
     * @param grade 年级信息
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByGrade(String grade) {
        return paperPublicationDAO.getPaperPublicationsByGrade(grade);
    }
    
    /**
     * 根据时间范围获取论文发表记录列表
     * <p>查询指定时间范围内的所有论文发表记录</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByTimeRange(Date startTime, Date endTime) {
        return paperPublicationDAO.getPaperPublicationsByTimeRange(startTime, endTime);
    }
    
    /**
     * 根据索引状态获取论文发表记录列表
     * <p>查询指定索引状态的所有论文发表记录（如SCI、EI、中文核心等）</p>
     * 
     * @param indexingStatus 索引状态
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByIndexingStatus(String indexingStatus) {
        return paperPublicationDAO.getPaperPublicationsByIndexingStatus(indexingStatus);
    }
    
    /**
     * 更新论文发表记录信息
     * <p>修改论文发表记录的详细信息</p>
     * 
     * @param paperPublication 包含更新信息的论文发表对象
     */
    @Override
    public void updatePaperPublication(PaperPublication paperPublication) {
        paperPublicationDAO.updatePaperPublication(paperPublication);
    }
    
    /**
     * 删除论文发表记录
     * <p>根据记录ID删除论文发表记录</p>
     * 
     * @param id 论文发表记录的唯一标识符
     */
    @Override
    public void deletePaperPublication(Integer id) {
        paperPublicationDAO.deletePaperPublication(id);
    }
    
    /**
     * 检查论文发表记录是否重复
     * <p>根据论文标题、学生ID、发表时间和期刊名称检查是否存在重复记录</p>
     * 
     * @param paperPublication 论文发表对象，包含要检查的论文发表信息
     * @return 如果存在重复记录则返回true，否则返回false
     */
    @Override
    public boolean checkDuplicatePaperPublication(PaperPublication paperPublication) {
        return paperPublicationDAO.checkDuplicatePaperPublication(paperPublication);
    }
    
    /**
     * 获取所有论文发表记录
     * <p>查询数据库中所有的论文发表记录</p>
     * 
     * @return 论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getAllPaperPublications() {
        return paperPublicationDAO.getAllPaperPublications();
    }
    
    /**
     * 根据多条件查询论文发表记录列表
     * <p>支持按院系、专业、年级、时间范围和索引状态组合查询论文发表记录</p>
     * 
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param indexingStatus 索引状态
     * @return 符合条件的论文发表记录列表，如果没有记录则返回空列表
     */
    @Override
    public List<PaperPublication> getPaperPublicationsByConditions(String department, String major, String grade, Date startTime, Date endTime, String indexingStatus) {
        return paperPublicationDAO.getPaperPublicationsByConditions(department, major, grade, startTime, endTime, indexingStatus);
    }
}