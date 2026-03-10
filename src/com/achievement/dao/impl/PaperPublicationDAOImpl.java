/**
 * PaperPublicationDAOImpl.java
 * 该类用于实现论文发表数据的数据库访问操作
 */
package com.achievement.dao.impl;

import com.achievement.dao.PaperPublicationDAO;
import com.achievement.model.PaperPublication;
import com.achievement.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaperPublicationDAOImpl implements PaperPublicationDAO {
    @Override
    public void addPaperPublication(PaperPublication paperPublication) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO paper_publication (student_id, paper_title, journal_conference_name, publication_time, author_rank, indexing_status, publication_status, authors, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, paperPublication.getStudentId());
            ps.setString(2, paperPublication.getPaperTitle());
            ps.setString(3, paperPublication.getJournalConferenceName());
            ps.setTimestamp(4, paperPublication.getPublicationTime() != null ? new java.sql.Timestamp(paperPublication.getPublicationTime().getTime()) : null);
            ps.setInt(5, paperPublication.getAuthorRank());
            ps.setString(6, paperPublication.getIndexingStatus());
            ps.setString(7, paperPublication.getPublicationStatus());
            ps.setString(8, paperPublication.getAuthors());
            ps.setString(9, paperPublication.getRemarks());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加论文发表失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    @Override
    public PaperPublication getPaperPublicationById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM paper_publication WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPaperPublication(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByStudentId(Integer studentId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM paper_publication WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByDepartment(String department) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT pp.* FROM paper_publication pp JOIN user u ON pp.student_id = u.id WHERE u.department = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, department);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByMajor(String major) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT pp.* FROM paper_publication pp JOIN user u ON pp.student_id = u.id WHERE u.major = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, major);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByGrade(String grade) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT pp.* FROM paper_publication pp JOIN user u ON pp.student_id = u.id WHERE u.grade = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, grade);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByTimeRange(Date startTime, Date endTime) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM paper_publication WHERE publication_time BETWEEN ? AND ?";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByIndexingStatus(String indexingStatus) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM paper_publication WHERE indexing_status = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, indexingStatus);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取论文发表列表失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public void updatePaperPublication(PaperPublication paperPublication) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE paper_publication SET student_id = ?, paper_title = ?, journal_conference_name = ?, publication_time = ?, author_rank = ?, indexing_status = ?, publication_status = ?, authors = ?, remarks = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, paperPublication.getStudentId());
            ps.setString(2, paperPublication.getPaperTitle());
            ps.setString(3, paperPublication.getJournalConferenceName());
            ps.setTimestamp(4, paperPublication.getPublicationTime() != null ? new java.sql.Timestamp(paperPublication.getPublicationTime().getTime()) : null);
            ps.setInt(5, paperPublication.getAuthorRank());
            ps.setString(6, paperPublication.getIndexingStatus());
            ps.setString(7, paperPublication.getPublicationStatus());
            ps.setString(8, paperPublication.getAuthors());
            ps.setString(9, paperPublication.getRemarks());
            ps.setInt(10, paperPublication.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新论文发表失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    @Override
    public void deletePaperPublication(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM paper_publication WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除论文发表失败: " + e.getMessage());
        } finally {
            DBUtil.close(ps, conn);
        }
    }
    
    @Override
    public boolean checkDuplicatePaperPublication(PaperPublication paperPublication) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM paper_publication WHERE student_id = ? AND paper_title = ? AND publication_time = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, paperPublication.getStudentId());
            ps.setString(2, paperPublication.getPaperTitle());
            ps.setTimestamp(3, paperPublication.getPublicationTime() != null ? new java.sql.Timestamp(paperPublication.getPublicationTime().getTime()) : null);
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
    
    @Override
    public List<PaperPublication> getAllPaperPublications() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM paper_publication";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取所有论文发表数据失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    public List<PaperPublication> getPaperPublicationsByConditions(String department, String major, String grade, Date startTime, Date endTime, String indexingStatus) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PaperPublication> papers = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            StringBuilder sqlBuilder = new StringBuilder("SELECT pp.* FROM paper_publication pp JOIN user u ON pp.student_id = u.id WHERE 1=1");
            
            // 动态添加WHERE子句
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
                sqlBuilder.append(" AND pp.publication_time >= ?");
            }
            if (endTime != null) {
                sqlBuilder.append(" AND pp.publication_time <= ?");
            }
            if (indexingStatus != null && !indexingStatus.isEmpty()) {
                sqlBuilder.append(" AND pp.indexing_status = ?");
            }
            
            ps = conn.prepareStatement(sqlBuilder.toString());
            
            // 动态绑定参数
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
            if (indexingStatus != null && !indexingStatus.isEmpty()) {
                ps.setString(parameterIndex++, indexingStatus);
            }
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                papers.add(mapResultSetToPaperPublication(rs));
            }
            return papers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据条件查询论文发表数据失败: " + e.getMessage());
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    // 辅助方法：将ResultSet映射为PaperPublication对象
    private PaperPublication mapResultSetToPaperPublication(ResultSet rs) throws SQLException {
        PaperPublication paperPublication = new PaperPublication();
        paperPublication.setId(rs.getInt("id"));
        paperPublication.setStudentId(rs.getInt("student_id"));
        paperPublication.setPaperTitle(rs.getString("paper_title"));
        paperPublication.setJournalConferenceName(rs.getString("journal_conference_name"));
        paperPublication.setPublicationStatus(rs.getString("publication_status"));
        paperPublication.setPublicationTime(rs.getTimestamp("publication_time"));
        paperPublication.setAuthorRank(rs.getInt("author_rank"));
        paperPublication.setIndexingStatus(rs.getString("indexing_status"));
        paperPublication.setAuthors(rs.getString("authors"));
        paperPublication.setRemarks(rs.getString("remarks"));
        paperPublication.setCreatedAt(rs.getTimestamp("created_at"));
        paperPublication.setUpdatedAt(rs.getTimestamp("updated_at"));
        return paperPublication;
    }
}