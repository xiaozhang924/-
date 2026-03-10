/**
 * ContestAwardDAOImpl.java
 * 该类用于实现竞赛获奖数据的数据库访问操作
 */
package com.achievement.dao.impl;

import com.achievement.dao.ContestAwardDAO;
import com.achievement.model.ContestAward;
import com.achievement.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContestAwardDAOImpl implements ContestAwardDAO {
    /**
     * 添加竞赛获奖记录
     * <p>将竞赛获奖信息插入到数据库中</p>
     * 
     * @param contestAward 竞赛获奖对象，包含获奖的所有信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void addContestAward(ContestAward contestAward) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO contest_award (student_id, contest_name, contest_level, award_level, award_time, organizer, is_team, team_members, advisor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, contestAward.getStudentId());
            ps.setString(2, contestAward.getContestName());
            ps.setString(3, contestAward.getContestLevel());
            ps.setString(4, contestAward.getAwardLevel());
            ps.setDate(5, new java.sql.Date(contestAward.getAwardTime().getTime()));
            ps.setString(6, contestAward.getOrganizer());
            ps.setBoolean(7, contestAward.isTeam());
            ps.setString(8, contestAward.getTeamMembers());
            ps.setString(9, contestAward.getAdvisor());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加竞赛获奖失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 根据ID获取竞赛获奖信息
     * <p>通过竞赛获奖记录ID从数据库中查询并返回获奖对象</p>
     * 
     * @param id 竞赛获奖记录的唯一标识符
     * @return 竞赛获奖对象，如果找不到则返回null
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public ContestAward getContestAwardById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM contest_award WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToContestAward(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据学生ID获取竞赛获奖列表
     * <p>查询指定学生的所有竞赛获奖记录</p>
     * 
     * @param studentId 学生的唯一标识符
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByStudentId(Integer studentId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM contest_award WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据院系获取竞赛获奖列表
     * <p>查询指定院系所有学生的竞赛获奖记录</p>
     * 
     * @param department 院系名称
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByDepartment(String department) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT ca.* FROM contest_award ca JOIN user u ON ca.student_id = u.id WHERE u.department = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据专业获取竞赛获奖列表
     * <p>查询指定专业所有学生的竞赛获奖记录</p>
     * 
     * @param major 专业名称
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByMajor(String major) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT ca.* FROM contest_award ca JOIN user u ON ca.student_id = u.id WHERE u.major = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, major);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据年级获取竞赛获奖列表
     * <p>查询指定年级所有学生的竞赛获奖记录</p>
     * 
     * @param grade 年级信息（如：2020级）
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByGrade(String grade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT ca.* FROM contest_award ca JOIN user u ON ca.student_id = u.id WHERE u.grade = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, grade);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据时间范围获取竞赛获奖列表
     * <p>查询指定时间段内的所有竞赛获奖记录</p>
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByTimeRange(Date startTime, Date endTime) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM contest_award WHERE award_time BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(startTime.getTime()));
            ps.setDate(2, new java.sql.Date(endTime.getTime()));
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据竞赛级别获取竞赛获奖列表
     * <p>查询指定级别竞赛的所有获奖记录</p>
     * 
     * @param level 竞赛级别（如：国家级、省级、校级）
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByLevel(String level) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM contest_award WHERE contest_level = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, level);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取竞赛获奖列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 更新竞赛获奖信息
     * <p>根据竞赛获奖记录ID更新数据库中的获奖信息</p>
     * 
     * @param contestAward 竞赛获奖对象，包含更新后的获奖信息
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void updateContestAward(ContestAward contestAward) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE contest_award SET student_id = ?, contest_name = ?, contest_level = ?, award_level = ?, award_time = ?, organizer = ?, is_team = ?, team_members = ?, advisor = ?, certificate_path = ?, remarks = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, contestAward.getStudentId());
            ps.setString(2, contestAward.getContestName());
            ps.setString(3, contestAward.getContestLevel());
            ps.setString(4, contestAward.getAwardLevel());
            ps.setDate(5, new java.sql.Date(contestAward.getAwardTime().getTime()));
            ps.setString(6, contestAward.getOrganizer());
            ps.setBoolean(7, contestAward.isTeam());
            ps.setString(8, contestAward.getTeamMembers());
            ps.setString(9, contestAward.getAdvisor());
            ps.setString(10, contestAward.getCertificatePath());
            ps.setString(11, contestAward.getRemarks());
            ps.setInt(12, contestAward.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新竞赛获奖失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 删除竞赛获奖记录
     * <p>根据竞赛获奖记录ID从数据库中删除获奖记录</p>
     * 
     * @param id 竞赛获奖记录的唯一标识符
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public void deleteContestAward(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM contest_award WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除竞赛获奖失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    /**
     * 检查竞赛获奖记录是否重复
     * <p>根据学生ID、竞赛名称和获奖时间检查是否存在重复记录</p>
     * 
     * @param contestAward 竞赛获奖对象，包含要检查的获奖信息
     * @return 如果存在重复记录则返回true，否则返回false
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public boolean checkDuplicateContestAward(ContestAward contestAward) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM contest_award WHERE student_id = ? AND contest_name = ? AND award_time = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, contestAward.getStudentId());
            ps.setString(2, contestAward.getContestName());
            ps.setDate(3, new java.sql.Date(contestAward.getAwardTime().getTime()));
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
     * 获取所有竞赛获奖记录
     * <p>查询数据库中所有的竞赛获奖记录</p>
     * 
     * @return 竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getAllContestAwards() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM contest_award";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取所有竞赛获奖数据失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 根据多条件查询竞赛获奖列表
     * <p>支持按院系、专业、年级、时间范围、竞赛级别组合查询竞赛获奖记录</p>
     * 
     * @param department 院系名称
     * @param major 专业名称
     * @param grade 年级信息
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param level 竞赛级别
     * @return 符合条件的竞赛获奖列表，如果没有记录则返回空列表
     * @throws RuntimeException 当数据库操作失败时抛出
     */
    @Override
    public List<ContestAward> getContestAwardsByConditions(String department, String major, String grade, Date startTime, Date endTime, String level) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ContestAward> contestAwards = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            StringBuilder sqlBuilder = new StringBuilder("SELECT ca.* FROM contest_award ca JOIN user u ON ca.student_id = u.id WHERE 1=1");
            
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
                sqlBuilder.append(" AND ca.award_time >= ?");
            }
            if (endTime != null) {
                sqlBuilder.append(" AND ca.award_time <= ?");
            }
            if (level != null && !level.isEmpty()) {
                sqlBuilder.append(" AND ca.contest_level = ?");
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
                ps.setDate(parameterIndex++, new java.sql.Date(startTime.getTime()));
            }
            if (endTime != null) {
                ps.setDate(parameterIndex++, new java.sql.Date(endTime.getTime()));
            }
            if (level != null && !level.isEmpty()) {
                ps.setString(parameterIndex++, level);
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                contestAwards.add(mapResultSetToContestAward(rs));
            }
            return contestAwards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据条件查询竞赛获奖数据失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    /**
     * 将ResultSet结果集映射为ContestAward对象
     * <p>从数据库查询结果中提取数据并构建竞赛获奖对象</p>
     * 
     * @param rs 数据库查询结果集，包含竞赛获奖的所有字段信息
     * @return 构建完成的竞赛获奖对象
     * @throws SQLException 当从结果集中获取数据时发生数据库错误
     */
    private ContestAward mapResultSetToContestAward(ResultSet rs) throws SQLException {        ContestAward contestAward = new ContestAward();        contestAward.setId(rs.getInt("id"));        contestAward.setStudentId(rs.getInt("student_id"));        contestAward.setContestName(rs.getString("contest_name"));        contestAward.setContestLevel(rs.getString("contest_level"));        contestAward.setAwardLevel(rs.getString("award_level"));        contestAward.setAwardTime(rs.getDate("award_time"));        contestAward.setOrganizer(rs.getString("organizer"));        contestAward.setTeam(rs.getBoolean("is_team"));        contestAward.setTeamMembers(rs.getString("team_members"));        contestAward.setAdvisor(rs.getString("advisor"));        contestAward.setCertificatePath(rs.getString("certificate_path"));        contestAward.setRemarks(rs.getString("remarks"));        contestAward.setCreatedAt(rs.getTimestamp("created_at"));        contestAward.setUpdatedAt(rs.getTimestamp("updated_at"));        return contestAward;    }
}