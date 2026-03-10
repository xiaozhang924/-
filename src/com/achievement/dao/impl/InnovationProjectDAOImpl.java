/**
 * InnovationProjectDAOImpl.java
 * 该类用于实现创新项目数据的数据库访问操作
 */
package com.achievement.dao.impl;

import com.achievement.dao.InnovationProjectDAO;
import com.achievement.model.InnovationProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InnovationProjectDAOImpl extends BaseDAO implements InnovationProjectDAO {
    /**
     * 添加创新项目记录
     * <p>将创新项目信息插入到数据库中</p>
     * 
     * @param innovationProject 创新项目对象，包含项目的所有信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void addInnovationProject(InnovationProject innovationProject) {
        String sql = "INSERT INTO innovation_project (student_id, project_name, project_type, project_level, project_status, start_year, start_time, end_time, conclusion_status, project_leader, team_members, role, advisor, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        executeUpdate(sql, 
            innovationProject.getStudentId(),
            innovationProject.getProjectName(),
            innovationProject.getProjectType(),
            innovationProject.getProjectLevel(),
            innovationProject.getProjectStatus(),
            innovationProject.getStartYear(),
            innovationProject.getStartTime() != null ? new java.sql.Timestamp(innovationProject.getStartTime().getTime()) : null,
            innovationProject.getEndTime() != null ? new java.sql.Timestamp(innovationProject.getEndTime().getTime()) : null,
            innovationProject.getConclusionStatus(),
            innovationProject.getProjectLeader(),
            innovationProject.getTeamMembers(),
            innovationProject.getRole(),
            innovationProject.getAdvisor(),
            innovationProject.getRemarks()
        );
    }
    
    /**
     * 根据ID获取创新项目信息
     * <p>通过项目ID从数据库中查询并返回创新项目对象</p>
     * 
     * @param id 创新项目的唯一标识符
     * @return 创新项目对象，如果找不到则返回null
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public InnovationProject getInnovationProjectById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM innovation_project WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToInnovationProject(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据学生ID获取创新项目列表
     * <p>查询指定学生的所有创新项目记录</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByStudentId(Integer studentId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM innovation_project WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据院系获取创新项目列表
     * <p>查询指定院系所有学生的创新项目记录</p>
     * 
     * @param department 院系名称
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByDepartment(String department) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT ip.* FROM innovation_project ip JOIN user u ON ip.student_id = u.id WHERE u.department = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据专业获取创新项目列表
     * <p>查询指定专业所有学生的创新项目记录</p>
     * 
     * @param major 专业名称
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByMajor(String major) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT ip.* FROM innovation_project ip JOIN user u ON ip.student_id = u.id WHERE u.major = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, major);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据年级获取创新项目列表
     * <p>查询指定年级所有学生的创新项目记录</p>
     * 
     * @param grade 年级信息（如：2022级、2023级）
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByGrade(String grade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT ip.* FROM innovation_project ip JOIN user u ON ip.student_id = u.id WHERE u.grade = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, grade);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据时间范围获取创新项目列表
     * <p>查询指定时间段内开始的创新项目记录</p>
     * 
     * @param startTime 时间范围的开始时间
     * @param endTime 时间范围的结束时间
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByTimeRange(Date startTime, Date endTime) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM innovation_project WHERE start_year BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, startTime.getYear() + 1900);
            ps.setInt(2, endTime.getYear() + 1900);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据项目级别获取创新项目列表
     * <p>查询指定级别的创新项目记录</p>
     * 
     * @param level 项目级别（如：国家级、省级、校级）
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByLevel(String level) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM innovation_project WHERE project_level = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, level);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据项目状态获取创新项目列表
     * <p>查询指定结项状态的创新项目记录</p>
     * 
     * @param status 结项状态（如：已结项、在研、中止）
     * @return 创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByStatus(String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM innovation_project WHERE conclusion_status = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取大创项目列表失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 更新创新项目记录
     * <p>修改数据库中的创新项目信息</p>
     * 
     * @param innovationProject 包含更新信息的创新项目对象，其中ID字段必须非空
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void updateInnovationProject(InnovationProject innovationProject) {
        String sql = "UPDATE innovation_project SET student_id = ?, project_name = ?, project_type = ?, project_level = ?, project_status = ?, start_year = ?, start_time = ?, end_time = ?, conclusion_status = ?, project_leader = ?, team_members = ?, role = ?, advisor = ?, remarks = ? WHERE id = ?";
        executeUpdate(sql, 
            innovationProject.getStudentId(),
            innovationProject.getProjectName(),
            innovationProject.getProjectType(),
            innovationProject.getProjectLevel(),
            innovationProject.getProjectStatus(),
            innovationProject.getStartYear(),
            innovationProject.getStartTime() != null ? new java.sql.Timestamp(innovationProject.getStartTime().getTime()) : null,
            innovationProject.getEndTime() != null ? new java.sql.Timestamp(innovationProject.getEndTime().getTime()) : null,
            innovationProject.getConclusionStatus(),
            innovationProject.getProjectLeader(),
            innovationProject.getTeamMembers(),
            innovationProject.getRole(),
            innovationProject.getAdvisor(),
            innovationProject.getRemarks(),
            innovationProject.getId()
        );
    }
    
    /**
     * 删除创新项目记录
     * <p>根据ID从数据库中删除创新项目信息</p>
     * 
     * @param id 创新项目的唯一标识符
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void deleteInnovationProject(Integer id) {
        String sql = "DELETE FROM innovation_project WHERE id = ?";
        executeUpdate(sql, id);
    }
    
    /**
     * 检查创新项目记录是否重复
     * <p>根据学生ID、项目名称和开始年份检查数据库中是否已存在相同的项目记录</p>
     * 
     * @param innovationProject 待检查的创新项目对象
     * @return 如果存在重复记录返回true，否则返回false
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public boolean checkDuplicateInnovationProject(InnovationProject innovationProject) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            String sql = "SELECT COUNT(*) FROM innovation_project WHERE student_id = ? AND project_name = ? AND start_year = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, innovationProject.getStudentId());
            ps.setString(2, innovationProject.getProjectName());
            ps.setInt(3, innovationProject.getStartYear());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("检查重复记录失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 获取所有创新项目记录
     * <p>查询数据库中的所有创新项目信息</p>
     * 
     * @return 所有创新项目的列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getAllInnovationProjects() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            String sql = "SELECT * FROM innovation_project";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取所有创新项目失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 根据多条件组合查询创新项目列表
     * <p>支持按院系、专业、年级、时间范围、项目级别和结项状态进行组合查询</p>
     * 
     * @param department 院系名称，为空时不进行过滤
     * @param major 专业名称，为空时不进行过滤
     * @param grade 年级信息，为空时不进行过滤
     * @param startTime 开始时间，为空时不进行过滤
     * @param endTime 结束时间，为空时不进行过滤
     * @param level 项目级别，为空时不进行过滤
     * @param status 结项状态，为空时不进行过滤
     * @return 符合条件的创新项目列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<InnovationProject> getInnovationProjectsByConditions(String department, String major, String grade, Date startTime, Date endTime, String level, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<InnovationProject> projects = new ArrayList<>();
        
        try {
            conn = getConnection();
            StringBuilder sqlBuilder = new StringBuilder("SELECT ip.* FROM innovation_project ip JOIN user u ON ip.student_id = u.id WHERE 1=1");
            
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
                sqlBuilder.append(" AND ip.start_year >= ?");
            }
            if (endTime != null) {
                sqlBuilder.append(" AND ip.start_year <= ?");
            }
            if (level != null && !level.isEmpty()) {
                sqlBuilder.append(" AND ip.project_level = ?");
            }
            if (status != null && !status.isEmpty()) {
                sqlBuilder.append(" AND ip.conclusion_status = ?");
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
                ps.setInt(parameterIndex++, startTime.getYear() + 1900);
            }
            if (endTime != null) {
                ps.setInt(parameterIndex++, endTime.getYear() + 1900);
            }
            if (level != null && !level.isEmpty()) {
                ps.setString(parameterIndex++, level);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(parameterIndex++, status);
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                projects.add(mapResultSetToInnovationProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据条件查询创新项目数据失败: " + e.getMessage());
        } finally {
            closeResources(rs, ps, conn);
        }
    }
    
    /**
     * 将ResultSet结果集映射为InnovationProject对象
     * <p>从数据库查询结果中提取数据并构建创新项目对象</p>
     * 
     * @param rs 数据库查询结果集，包含创新项目的所有字段信息
     * @return 构建完成的创新项目对象
     * @throws SQLException 当从结果集中获取数据时发生数据库错误
     */
    private InnovationProject mapResultSetToInnovationProject(ResultSet rs) throws SQLException {
        InnovationProject project = new InnovationProject();
        project.setId(rs.getInt("id"));
        project.setStudentId(rs.getInt("student_id"));
        project.setProjectName(rs.getString("project_name"));
        project.setProjectType(rs.getString("project_type"));
        project.setProjectLevel(rs.getString("project_level"));
        project.setProjectStatus(rs.getString("project_status"));
        project.setStartYear(rs.getInt("start_year"));
        project.setStartTime(rs.getTimestamp("start_time"));
        project.setEndTime(rs.getTimestamp("end_time"));
        project.setConclusionStatus(rs.getString("conclusion_status"));
        project.setProjectLeader(rs.getString("project_leader"));
        project.setTeamMembers(rs.getString("team_members"));
        project.setRole(rs.getString("role"));
        project.setAdvisor(rs.getString("advisor"));
        project.setRemarks(rs.getString("remarks"));
        project.setCreatedAt(rs.getTimestamp("created_at"));
        project.setUpdatedAt(rs.getTimestamp("updated_at"));
        return project;
    }
}