/**
 * UserService.java
 * 用户业务逻辑接口 - 定义用户相关的业务操作
 * 
 * 功能说明：
 * 1. 在DAO层之上提供业务逻辑处理
 * 2. 处理用户注册、登录等核心业务流程
 * 3. 进行业务规则验证和异常处理
 * 4. 为Controller层提供统一的服务接口
 * 
 * 设计模式：Service层（业务逻辑层）
 * 实现类：UserServiceImpl
 * 
 * @author 学生成果管理系统开发组
 * @version 1.0
 */
package com.achievement.service;

import com.achievement.model.User;
import java.util.List;

public interface UserService {
    /**
     * 用户注册
     * 处理用户注册业务逻辑，包括数据验证、用户名唯一性检查等
     * 
     * @param user 注册用户信息
     * @throws com.achievement.exception.UsernameExistsException 用户名已存在
     * @throws com.achievement.exception.ValidationException 数据验证失败
     */
    void register(User user);
    
    /**
     * 用户登录
     * 验证用户名和密码，返回登录成功的用户信息
     * 
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象
     * @throws com.achievement.exception.UserNotFoundException 用户不存在
     * @throws com.achievement.exception.PasswordMismatchException 密码错误
     */
    User login(String username, String password);
    
    /**
     * 根据ID获取用户信息
     * 
     * @param id 用户ID
     * @return 用户对象，不存在返回null
     */
    User getUserById(Integer id);
    
    /**
     * 根据用户名获取用户信息
     * 
     * @param username 用户名
     * @return 用户对象，不存在返回null
     */
    User getUserByUsername(String username);
    
    /**
     * 根据院系获取学生列表
     * 
     * @param department 院系名称
     * @return 学生列表
     */
    List<User> getStudentsByDepartment(String department);
    
    /**
     * 根据专业获取学生列表
     * 
     * @param major 专业名称
     * @return 学生列表
     */
    List<User> getStudentsByMajor(String major);
    
    /**
     * 根据年级获取学生列表
     * 
     * @param grade 年级信息
     * @return 学生列表
     */
    List<User> getStudentsByGrade(String grade);
    
    /**
     * 根据多条件查询学生列表
     * 
     * @param name 姓名（模糊查询）
     * @param department 院系
     * @param major 专业
     * @param grade 年级
     * @return 符合条件的学生列表
     */
    List<User> getStudentsByConditions(String name, String department, String major, String grade);
    
    /**
     * 更新用户信息
     * 
     * @param user 更新后的用户信息
     * @throws com.achievement.exception.ValidationException 数据验证失败
     */
    void updateUser(User user);
    
    /**
     * 删除用户
     * 注意：删除用户会同时删除其所有成果数据
     * 
     * @param id 用户ID
     */
    void deleteUser(Integer id);
}
