/**
 * ContestAward.java
 * 竞赛获奖实体类 - 用于定义竞赛获奖数据模型
 * 
 * 功能说明：
 * 1. 记录学生参加各类学科竞赛的获奖信息
 * 2. 支持个人项目和团队项目的区分管理
 * 3. 记录竞赛级别（国家级、省级、校级）和获奖等级
 * 4. 存储证书路径、指导教师等详细信息
 * 
 * 对应数据库表：contest_award

 */
package com.achievement.model;
/*竞赛获奖实体类 - 用于定义竞赛获奖数据模型*/
import java.util.Date;

public class ContestAward {
    // 竞赛获奖记录唯一标识ID（主键，自增）
    private Integer id;
    
    // 学生ID（外键，关联user表的id字段）
    private Integer studentId;
    
    // 竞赛名称（如：全国大学生数学建模竞赛）
    private String contestName;
    
    // 竞赛级别（国家级/省级/校级，用于统计分析）
    private String contestLevel;
    
    // 证书文件存储路径（用于保存电子版证书）
    private String certificatePath;
    
    // 备注信息（记录特殊说明或补充信息）
    private String remarks;
    
    // 获奖等级（一等奖/二等奖/三等奖/优秀奖等）
    private String awardLevel;
    
    // 获奖时间（用于年度统计）
    private Date awardTime;
    
    // 主办单位（如：教育部、XX省教育厅）
    private String organizer;
    
    // 是否为团队项目（true-团队项目，false-个人项目）
    private boolean isTeam;
    
    // 团队成员列表（多个成员用逗号分隔，如："张三,李四,王五"）
    private String teamMembers;
    
    // 指导教师姓名（记录指导老师信息）
    private String advisor;
    
    // 记录创建时间（自动生成）
    private Date createdAt;
    
    // 记录最后更新时间（自动更新）
    private Date updatedAt;

    /**
     * 默认构造方法
     * 用于创建空的ContestAward对象，通常配合setter方法使用
     */
    public ContestAward() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestLevel() {
        return contestLevel;
    }

    public void setContestLevel(String contestLevel) {
        this.contestLevel = contestLevel;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAwardLevel() {
        return awardLevel;
    }

    public void setAwardLevel(String awardLevel) {
        this.awardLevel = awardLevel;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public boolean isTeam() {
        return isTeam;
    }

    public void setTeam(boolean team) {
        isTeam = team;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
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
        return "ContestAward{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", contestName='" + contestName + '\'' +
                ", contestLevel='" + contestLevel + '\'' +
                ", awardLevel='" + awardLevel + '\'' +
                ", awardTime=" + awardTime +
                ", organizer='" + organizer + '\'' +
                ", isTeam=" + isTeam +
                ", teamMembers='" + teamMembers + '\'' +
                ", advisor='" + advisor + '\'' +
                ", certificatePath='" + certificatePath + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
