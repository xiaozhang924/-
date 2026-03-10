/**
 * UserDAO.java
 * 用户数据访问接口 - 定义用户数据的数据库访问操作
 * 
 * 功能说明：
 * 1. 定义用户数据的增删改查操作接口
 * 2. 提供多种查询方式（按ID、用户名、角色、院系等）
 * 3. 支持用户登录验证功能
 * 4. 遵循DAO设计模式，分离数据访问逻辑和业务逻辑
 * 
 * 实现类：UserDAOImpl
 * 
 * @author 学生成果管理系统开发组
 * @version 1.0
 */
package com.achievement.dao;

import com.achievement.model.User;

import java.util.List;

public interface UserDAO {
    /**
     * 添加新用户
     * 用于用户注册功能，将新用户信息插入数据库
     * 
     * @param user 用户对象，包含用户的完整信息
     */
    void addUser(User user);
    
    /**
     * 根据用户ID获取用户信息
     * 用于查询指定用户的详细信息
     * 
     * @param id 用户的唯一标识ID
     * @return 查询到的用户对象，不存在则返回null
     */
    User getUserById(Integer id);
    
    /**
     * 根据用户名获取用户信息
     * 用于登录验证和检查用户名是否已存在
     * 
     * @param username 用户名（唯一）
     * @return 查询到的用户对象，不存在则返回null
     */
    User getUserByUsername(String username);
    
    /**
     * 根据角色获取用户列表
     * 用于按角色筛选用户（学生、教师、管理员）
     * 
     * @param role 用户角色（如："学生"、"教师"、"管理员"）
     * @return 符合条件的用户列表
     */
    List<User> getUsersByRole(String role);
    
    /**
     * 根据院系获取学生列表
     * 用于按院系统计和管理学生信息
     * 
     * @param department 院系名称（如："计算机学院"）
     * @return 该院系的学生列表
     */
    List<User> getStudentsByDepartment(String department);

    /**
     * 根据专业获取学生列表
     * 用于按专业统计和管理学生信息
     * 
     * @param major 专业名称（如："计算机科学与技术"）
     * @return 该专业的学生列表
     */
    List<User> getStudentsByMajor(String major);
    
    /**
     * 根据年级获取学生列表
     * 用于按年级统计和管理学生信息
     * 
     * @param grade 年级信息（如："2022级"）
     * @return 该年级的学生列表
     */
    List<User> getStudentsByGrade(String grade);
    
    /**
     * 根据多个条件组合查询学生列表
     * 支持按姓名、院系、专业、年级等条件灵活组合查询
     * 条件参数可为null或空字符串，表示不作为筛选条件
     * 
     * @param name 学生姓名（模糊查询）
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @return 符合所有指定条件的学生列表
     */
    List<User> getStudentsByConditions(String name, String department, String major, String grade);
    
    /**
     * 更新用户信息
     * 用于修改用户的个人资料、密码等信息
     * 
     * @param user 包含更新后信息的用户对象（必须包含ID）
     */
    void updateUser(User user);
    
    /**
     * 删除用户
     * 根据用户ID删除用户记录（注意：会级联删除相关成果数据）
     * 
     * @param id 要删除的用户ID
     */
    void deleteUser(Integer id);
    
    /**
     * 验证用户登录
     * 根据用户名和密码验证用户身份，验证成功返回用户信息
     * 
     * @param username 用户名
     * @param password 密码（明文或加密后的密码）
     * @return 登录成功返回用户对象，失败返回null
     */
    User login(String username, String password);
}