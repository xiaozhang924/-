/**
 * UserDAOImpl.java
 * 该类用于实现用户数据的数据库访问操作
 */
package com.achievement.dao.impl;

import com.achievement.constant.RoleConstant;
import com.achievement.dao.UserDAO;
import com.achievement.model.User;
import com.achievement.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {
     /**
     * 添加用户记录
     * <p>将用户信息插入到数据库中</p>
     * 
     * @param user 用户对象，包含用户的所有信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO user (username, password, real_name, student_number, role, department, major, grade, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getRealName(),
                user.getStudentNumber(),
                user.getRole(),
                user.getDepartment(),
                user.getMajor(),
                user.getGrade(),
                user.getEmail(),
                user.getPhone());
    }
    
    /**
     * 根据ID获取用户信息
     * <p>通过用户ID从数据库中查询并返回用户对象</p>
     * 
     * @param id 用户的唯一标识符
     * @return 用户对象，如果找不到则返回null
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public User getUserById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取用户失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据用户名获取用户信息
     * <p>通过用户名从数据库中查询并返回用户对象</p>
     * 
     * @param username 用户名，用户登录账号
     * @return 用户对象，如果找不到则返回null
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public User getUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取用户失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据角色获取用户列表
     * <p>查询指定角色的所有用户记录</p>
     * 
     * @param role 用户角色（如：学生、教师、管理员）
     * @return 用户列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<User> getUsersByRole(String role) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE role = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, role);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取用户列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据院系获取学生列表
     * <p>查询指定院系的所有学生记录</p>
     * 
     * @param department 院系名称
     * @return 学生列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<User> getStudentsByDepartment(String department) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> students = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE role = ? AND department = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, RoleConstant.STUDENT);
            ps.setString(2, department);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                students.add(mapResultSetToUser(rs));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取学生列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据专业获取学生列表
     * <p>查询指定专业的所有学生记录</p>
     * 
     * @param major 专业名称
     * @return 学生列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<User> getStudentsByMajor(String major) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> students = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE role = ? AND major = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, RoleConstant.STUDENT);
            ps.setString(2, major);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                students.add(mapResultSetToUser(rs));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取学生列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据年级获取学生列表
     * <p>查询指定年级的所有学生记录</p>
     * 
     * @param grade 年级信息（如：2020级）
     * @return 学生列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<User> getStudentsByGrade(String grade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> students = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM user WHERE role = ? AND grade = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, RoleConstant.STUDENT);
            ps.setString(2, grade);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                students.add(mapResultSetToUser(rs));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取学生列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据多条件查询学生列表
     * <p>支持按姓名、院系、专业、年级组合查询学生信息</p>
     * 
     * @param name 学生姓名，支持模糊查询
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @return 符合条件的学生列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<User> getStudentsByConditions(String name, String department, String major, String grade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> students = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("SELECT * FROM user WHERE role = ?");
            List<Object> params = new ArrayList<>();
            params.add(RoleConstant.STUDENT);
            
            // 构建动态查询条件
            if (name != null && !name.isEmpty()) {
                sql.append(" AND real_name LIKE ?");
                params.add("%" + name + "%");
            }
            
            if (department != null && !department.isEmpty()) {
                sql.append(" AND department = ?");
                params.add(department);
            }
            
            if (major != null && !major.isEmpty()) {
                sql.append(" AND major = ?");
                params.add(major);
            }
            
            if (grade != null && !grade.isEmpty()) {
                sql.append(" AND grade = ?");
                params.add(grade);
            }
            
            ps = conn.prepareStatement(sql.toString());
            
            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                students.add(mapResultSetToUser(rs));
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取学生列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 更新用户信息
     * <p>根据用户ID更新数据库中的用户信息</p>
     * 
     * @param user 用户对象，包含更新后的用户信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void updateUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE user SET username = ?, password = ?, real_name = ?, role = ?, department = ?, major = ?, grade = ?, email = ?, phone = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRealName());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getDepartment());
            ps.setString(6, user.getMajor());
            ps.setString(7, user.getGrade());
            ps.setString(8, user.getEmail());
            ps.setString(9, user.getPhone());
            ps.setInt(10, user.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新用户失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 删除用户
     * <p>根据用户ID从数据库中删除用户记录</p>
     * 
     * @param id 用户的唯一标识符
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void deleteUser(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM user WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除用户失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 用户登录验证
     * <p>根据用户名查询用户信息，密码验证已在Service层处理</p>
     * 
     * @param username 用户名
     * @param password 用户密码
     * @return 用户对象，如果找不到则返回null
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public User login(String username, String password) {
        // 注意：密码验证已在Service层处理，这里仅根据用户名查询
        return getUserByUsername(username);
    }
    
    /**
     * 将ResultSet结果集映射为User对象
     * <p>从数据库查询结果中提取数据并构建用户对象</p>
     * 
     * @param rs 数据库查询结果集，包含用户的所有字段信息
     * @return 构建完成的用户对象
     * @throws SQLException 当从结果集中获取数据时发生数据库错误
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRealName(rs.getString("real_name"));
        user.setStudentNumber(rs.getString("student_number"));
        user.setRole(rs.getString("role"));
        user.setDepartment(rs.getString("department"));
        user.setMajor(rs.getString("major"));
        user.setGrade(rs.getString("grade"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        return user;
    }
}