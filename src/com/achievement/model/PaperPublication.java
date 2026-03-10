/**
 * PaperPublication.java
 * 该类用于定义论文发表数据模型
 */
package com.achievement.model;

import java.util.Date;

public class PaperPublication {
    private Integer id;
    private Integer studentId;
    private String paperTitle;
    private String journalConferenceName;
    private String publicationStatus;
    private Date publicationTime;
    private Integer authorRank;
    private String indexingStatus;
    private String authors;
    private String remarks;
    private Date createdAt;
    private Date updatedAt;

    public PaperPublication() {
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

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle;
    }

    public String getJournalConferenceName() {
        return journalConferenceName;
    }

    public void setJournalConferenceName(String journalConferenceName) {
        this.journalConferenceName = journalConferenceName;
    }

    public String getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(String publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public Date getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(Date publicationTime) {
        this.publicationTime = publicationTime;
    }

    public Integer getAuthorRank() {
        return authorRank;
    }

    public void setAuthorRank(Integer authorRank) {
        this.authorRank = authorRank;
    }

    public String getIndexingStatus() {
        return indexingStatus;
    }

    public void setIndexingStatus(String indexingStatus) {
        this.indexingStatus = indexingStatus;
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

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    // 兼容getPublishDate方法的别名
    public Date getPublishDate() {
        return publicationTime;
    }

    // 兼容setPublishDate方法的别名
    public void setPublishDate(Date publishDate) {
        this.publicationTime = publishDate;
    }

    @Override
    public String toString() {
        return "PaperPublication{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", paperTitle='" + paperTitle + '\'' +
                ", journalConferenceName='" + journalConferenceName + '\'' +
                ", publicationStatus='" + publicationStatus + '\'' +
                ", publicationTime=" + publicationTime +
                ", authorRank=" + authorRank +
                ", indexingStatus='" + indexingStatus + '\'' +
                ", authors='" + authors + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
