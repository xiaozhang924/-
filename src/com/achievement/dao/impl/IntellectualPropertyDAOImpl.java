/**
 * IntellectualPropertyDAOImpl.java
 * 该类用于实现知识产权数据的数据库访问操作
 */
package com.achievement.dao.impl;

import com.achievement.dao.IntellectualPropertyDAO;
import com.achievement.model.IntellectualProperty;
import com.achievement.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IntellectualPropertyDAOImpl implements IntellectualPropertyDAO {
    /**
     * 添加知识产权记录
     * <p>将知识产权信息插入到数据库中</p>
     * 
     * @param intellectualProperty 知识产权对象，包含知识产权的所有信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void addIntellectualProperty(IntellectualProperty intellectualProperty) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO intellectual_property (student_id, name, type, application_number, authorization_number, application_date, authorization_date, status, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, intellectualProperty.getStudentId());
            ps.setString(2, intellectualProperty.getName());
            ps.setString(3, intellectualProperty.getType());
            ps.setString(4, intellectualProperty.getApplicationNumber());
            ps.setString(5, intellectualProperty.getAuthorizationNumber());
            ps.setTimestamp(6, intellectualProperty.getApplicationDate() != null ? new java.sql.Timestamp(intellectualProperty.getApplicationDate().getTime()) : null);
            ps.setTimestamp(7, intellectualProperty.getAuthorizationDate() != null ? new java.sql.Timestamp(intellectualProperty.getAuthorizationDate().getTime()) : null);
            ps.setString(8, intellectualProperty.getStatus());
            ps.setString(9, intellectualProperty.getRemarks());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加知识产权失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 根据ID获取知识产权信息
     * <p>通过知识产权记录ID从数据库中查询并返回知识产权对象</p>
     * 
     * @param id 知识产权记录的唯一标识符
     * @return 知识产权对象，如果找不到则返回null
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public IntellectualProperty getIntellectualPropertyById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM intellectual_property WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToIntellectualProperty(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据学生ID获取知识产权列表
     * <p>查询指定学生的所有知识产权记录</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByStudentId(Integer studentId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM intellectual_property WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据院系获取知识产权列表
     * <p>查询指定院系所有学生的知识产权记录</p>
     * 
     * @param department 院系名称
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByDepartment(String department) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT ip.* FROM intellectual_property ip JOIN user u ON ip.student_id = u.id WHERE u.department = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据专业获取知识产权列表
     * <p>查询指定专业所有学生的知识产权记录</p>
     * 
     * @param major 专业名称
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByMajor(String major) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT ip.* FROM intellectual_property ip JOIN user u ON ip.student_id = u.id WHERE u.major = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, major);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据年级获取知识产权列表
     * <p>查询指定年级所有学生的知识产权记录</p>
     * 
     * @param grade 年级信息（如：2020级）
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByGrade(String grade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT ip.* FROM intellectual_property ip JOIN user u ON ip.student_id = u.id WHERE u.grade = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, grade);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据时间范围获取知识产权列表
     * <p>查询指定时间段内的所有知识产权记录</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByTimeRange(Date startTime, Date endTime) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM intellectual_property WHERE application_date BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据知识产权类型获取列表
     * <p>查询指定类型的所有知识产权记录</p>
     * 
     * @param type 知识产权类型（如：专利、软件著作权）
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByType(String type) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM intellectual_property WHERE type = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据知识产权状态获取列表
     * <p>查询指定状态的所有知识产权记录</p>
     * 
     * @param status 知识产权状态（如：申请中、已授权、已驳回）
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByStatus(String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM intellectual_property WHERE status = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取知识产权列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 更新知识产权信息
     * <p>根据知识产权记录ID更新数据库中的知识产权信息</p>
     * 
     * @param intellectualProperty 知识产权对象，包含更新后的知识产权信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void updateIntellectualProperty(IntellectualProperty intellectualProperty) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE intellectual_property SET student_id = ?, name = ?, type = ?, application_number = ?, authorization_number = ?, application_date = ?, authorization_date = ?, status = ?, remarks = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, intellectualProperty.getStudentId());
            ps.setString(2, intellectualProperty.getName());
            ps.setString(3, intellectualProperty.getType());
            ps.setString(4, intellectualProperty.getApplicationNumber());
            ps.setString(5, intellectualProperty.getAuthorizationNumber());
            ps.setTimestamp(6, intellectualProperty.getApplicationDate() != null ? new java.sql.Timestamp(intellectualProperty.getApplicationDate().getTime()) : null);
            ps.setTimestamp(7, intellectualProperty.getAuthorizationDate() != null ? new java.sql.Timestamp(intellectualProperty.getAuthorizationDate().getTime()) : null);
            ps.setString(8, intellectualProperty.getStatus());
            ps.setString(9, intellectualProperty.getRemarks());
            ps.setInt(10, intellectualProperty.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新知识产权失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 删除知识产权记录
     * <p>根据知识产权记录ID从数据库中删除知识产权记录</p>
     * 
     * @param id 知识产权记录的唯一标识符
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void deleteIntellectualProperty(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM intellectual_property WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除知识产权失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 检查知识产权记录是否重复
     * <p>根据学生ID、知识产权名称和申请日期检查是否存在重复记录</p>
     * 
     * @param intellectualProperty 知识产权对象，包含要检查的知识产权信息
     * @return 如果存在重复记录则返回true，否则返回false
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public boolean checkDuplicateIntellectualProperty(IntellectualProperty intellectualProperty) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM intellectual_property WHERE student_id = ? AND name = ? AND application_date = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, intellectualProperty.getStudentId());
            ps.setString(2, intellectualProperty.getName());
            ps.setTimestamp(3, intellectualProperty.getApplicationDate() != null ? new java.sql.Timestamp(intellectualProperty.getApplicationDate().getTime()) : null);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("检查重复记录失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 获取所有知识产权记录
     * <p>查询数据库中所有的知识产权记录</p>
     * 
     * @return 知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getAllIntellectualProperties() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM intellectual_property";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取所有知识产权失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据多条件查询知识产权列表
     * <p>支持按院系、专业、年级、时间范围、知识产权类型和状态组合查询知识产权记录</p>
     * 
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param type 知识产权类型
     * @param status 知识产权状态
     * @return 符合条件的知识产权列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<IntellectualProperty> getIntellectualPropertiesByConditions(String department, String major, String grade, Date startTime, Date endTime, String type, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<IntellectualProperty> properties = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            StringBuilder sqlBuilder = new StringBuilder("SELECT ip.* FROM intellectual_property ip JOIN user u ON ip.student_id = u.id WHERE 1=1");
            
            // 根据条件动态添加WHERE子句
            if (department != null && !department.isEmpty()) {
                sqlBuilder.append(" AND u.department = ?");
            }
            if (major != null && !major.isEmpty()) {
                sqlBuilder.append(" AND u.major = ?");
            }
            if (grade != null && !grade.isEmpty()) {
                sqlBuilder.append(" AND u.grade = ?");
            }
            if (startTime != null) {
                sqlBuilder.append(" AND ip.application_date >= ?");
            }
            if (endTime != null) {
                sqlBuilder.append(" AND ip.application_date <= ?");
            }
            if (type != null && !type.isEmpty()) {
                sqlBuilder.append(" AND ip.type = ?");
            }
            if (status != null && !status.isEmpty()) {
                sqlBuilder.append(" AND ip.status = ?");
            }
            
            ps = conn.prepareStatement(sqlBuilder.toString());
            
            // 设置参数
            int parameterIndex = 1;
            if (department != null && !department.isEmpty()) {
                ps.setString(parameterIndex++, department);
            }
            if (major != null && !major.isEmpty()) {
                ps.setString(parameterIndex++, major);
            }
            if (grade != null && !grade.isEmpty()) {
                ps.setString(parameterIndex++, grade);
            }
            if (startTime != null) {
                ps.setTimestamp(parameterIndex++, new java.sql.Timestamp(startTime.getTime()));
            }
            if (endTime != null) {
                ps.setTimestamp(parameterIndex++, new java.sql.Timestamp(endTime.getTime()));
            }
            if (type != null && !type.isEmpty()) {
                ps.setString(parameterIndex++, type);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(parameterIndex++, status);
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                properties.add(mapResultSetToIntellectualProperty(rs));
            }
            return properties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据条件查询知识产权数据失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 将ResultSet结果集映射为IntellectualProperty对象
     * <p>从数据库查询结果中提取数据并构建知识产权对象</p>
     * 
     * @param rs 数据库查询结果集，包含知识产权的所有字段信息
     * @return 构建完成的知识产权对象
     * @throws SQLException 当从结果集中获取数据时发生数据库错误
     */
    private IntellectualProperty mapResultSetToIntellectualProperty(ResultSet rs) throws SQLException {
        IntellectualProperty property = new IntellectualProperty();
        property.setId(rs.getInt("id"));
        property.setStudentId(rs.getInt("student_id"));
        property.setName(rs.getString("name"));
        property.setType(rs.getString("type"));
        property.setApplicationNumber(rs.getString("application_number"));
        property.setAuthorizationNumber(rs.getString("authorization_number"));
        property.setApplicationDate(rs.getTimestamp("application_date"));
        property.setAuthorizationDate(rs.getTimestamp("authorization_date"));
        property.setStatus(rs.getString("status"));
        property.setRemarks(rs.getString("remarks"));
        property.setCreatedAt(rs.getTimestamp("created_at"));
        property.setUpdatedAt(rs.getTimestamp("updated_at"));
        return property;
    }
}