/**
 * InnovationProject.java
 * 该类用于定义创新项目数据模型
 */
package com.achievement.model;

import java.util.Date;

public class InnovationProject {
    private Integer id;
    private Integer studentId;
    private String projectName;
    private String projectType;
    private String projectLevel;
    private String projectStatus;
    private Integer startYear;
    private Date startTime;
    private Date endTime;
    private String conclusionStatus;
    private String projectLeader;
    private String teamMembers;
    private String role;
    private String advisor;
    private String remarks;
    private Date createdAt;
    private Date updatedAt;

    public InnovationProject() {
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLevel() {
        return projectLevel;
    }

    public void setProjectLevel(String projectLevel) {
        this.projectLevel = projectLevel;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getConclusionStatus() {
        return conclusionStatus;
    }

    public void setConclusionStatus(String conclusionStatus) {
        this.conclusionStatus = conclusionStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "InnovationProject{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", projectName='" + projectName + '\'' +
                ", projectType='" + projectType + '\'' +
                ", projectLevel='" + projectLevel + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                ", startYear=" + startYear +
                ", conclusionStatus='" + conclusionStatus + '\'' +
                ", projectLeader='" + projectLeader + '\'' +
                ", teamMembers='" + teamMembers + '\'' +
                ", role='" + role + '\'' +
                ", advisor='" + advisor + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
