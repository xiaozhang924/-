/**
 * IntellectualProperty.java
 * 该类用于定义知识产权数据模型
 */
package com.achievement.model;

import java.util.Date;

public class IntellectualProperty {
    private Integer id;
    private Integer studentId;
    private String name;
    private String type;
    private String applicationNumber;
    private String authorizationNumber;
    private Date applicationDate;
    private Date authorizationDate;
    private String status;
    private String remarks;
    private Date createdAt;
    private Date updatedAt;

    public IntellectualProperty() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAuthorizationNumber() {
        return authorizationNumber;
    }

    public void setAuthorizationNumber(String authorizationNumber) {
        this.authorizationNumber = authorizationNumber;
    }

    public Date getAuthorizationDate() {
        return authorizationDate;
    }

    public void setAuthorizationDate(Date authorizationDate) {
        this.authorizationDate = authorizationDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // 兼容getPropertyName方法的别名
    public String getPropertyName() {
        return name;
    }

    // 兼容setPropertyName方法的别名
    public void setPropertyName(String propertyName) {
        this.name = propertyName;
    }

    // 兼容getPropertyType方法的别名
    public String getPropertyType() {
        return type;
    }

    // 兼容setPropertyType方法的别名
    public void setPropertyType(String propertyType) {
        this.type = propertyType;
    }

    @Override
    public String toString() {
        return "IntellectualProperty{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", applicationNumber='" + applicationNumber + '\'' +
                ", authorizationNumber='" + authorizationNumber + '\'' +
                ", applicationDate=" + applicationDate +
                ", authorizationDate=" + authorizationDate +
                ", status='" + status + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
