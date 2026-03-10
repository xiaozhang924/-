/**
 * User.java
 * 用户实体类 - 用于定义用户数据模型
 * 
 * 功能说明：
 * 1. 封装用户的基本信息，包括个人资料、角色权限等
 * 2. 支持学生、教师、管理员三种角色
 * 3. 提供完整的getter/setter方法便于数据访问
 * 
 * 对应数据库表：user
 * 

 */
package com.achievement.model;

import java.util.Date;

public class User {
    // 用户唯一标识ID（主键，自增）
    private Integer id;
    
    // 学号（学生专用，20位数字，唯一）
    private String studentNumber;
    
    // 用户名（登录账号，唯一，长度3-20字符）
    private String username;
    
    // 密码（加密存储，长度6-20字符）
    private String password;
    
    // 真实姓名（用于显示和识别用户身份）
    private String realName;
    
    // 用户角色（学生/教师/管理员，控制系统权限）
    private String role;
    
    // 所属院系（如：计算机学院、电子信息工程学院）
    private String department;
    
    // 所属专业（如：计算机科学与技术、软件工程）
    private String major;
    
    // 年级信息（如：2022级、2023级）
    private String grade;
    
    // 电子邮箱（用于接收通知和找回密码）
    private String email;
    
    // 手机号码（中国大陆11位手机号）
    private String phone;
    
    // 记录创建时间（自动生成）
    private Date createdAt;
    
    // 记录最后更新时间（自动更新）
    private Date updatedAt;

    /**
     * 默认构造方法
     * 用于创建空的User对象，通常配合setter方法使用
     */
    public User() {
    }

    /**
     * 带参数的构造方法
     * 用于快速创建User对象，主要用于用户注册场景
     * 
     * @param username 用户名
     * @param password 密码
     * @param realName 真实姓名
     * @param role 角色（学生/教师/管理员）
     * @param email 电子邮箱
     */
    public User(String username, String password, String realName, String role, String email) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.role = role;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取用户姓名（getName方法）
     * 为了兼容性，提供与getRealName()相同的功能
     * 在某些UI组件中可能需要getName()方法
     * 
     * @return 用户真实姓名
     */
    public String getName() {
        return realName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", major='" + major + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
